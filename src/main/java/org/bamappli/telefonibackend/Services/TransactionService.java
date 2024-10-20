package org.bamappli.telefonibackend.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.PayerTransactionDTO;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Entity.Transaction;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Entity.Wallet;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.bamappli.telefonibackend.Enum.TransactionStatut;
import org.bamappli.telefonibackend.Repository.TransactionRepo;
import org.bamappli.telefonibackend.Repository.UtilisateurRepo;
import org.bamappli.telefonibackend.Repository.WalletRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class TransactionService implements CrudService<Long, Transaction>{

    private final UserService userService;
    private final TransactionRepo transactionRepo;
    private final UtilisateurRepo utilisateurRepo;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepo walletRepo;
    private final CommandeService commandeService;

    @Override
    public Transaction creer(Transaction transaction) {
        Optional<Utilisateur> client = utilisateurRepo.findById(transaction.getAcheteur().getId());
        if (client.isPresent()){
            return transactionRepo.save(transaction);
        }
        throw new EntityNotFoundException("Utilisateur non trouve");
    }

    @Override
    public Transaction modifer(Long id, Transaction transaction) {
        Optional<Transaction> transaction1 = transactionRepo.findById(id);

        if (transaction1.isPresent()){
            Transaction transactionExist = transaction1.get();
            if (transaction.getStatut() != null) transactionExist.setStatut(transaction.getStatut());
            return transactionRepo.save(transactionExist);
        }else{
            throw new IllegalArgumentException("La Transaction n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Transaction> trouver(Long id) {
        return transactionRepo.findById(id);
    }

    @Override
    public List<Transaction> recuperer() {
        return transactionRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        transactionRepo.deleteById(id);
    }

    public Transaction payer(Long id, String codeSecret) {
        Utilisateur utilisateur = userService.getCurrentUser();
        Optional<Transaction> transaction1 = transactionRepo.findById(id);

        if (transaction1.isPresent()) {
            Transaction transactionExist = transaction1.get();

            if (passwordEncoder.matches(codeSecret, utilisateur.getCompte().getCodeSecret())) {
                if (transactionExist.getStatut() != TransactionStatut.PAYER) {
                    Double montantTransaction = transactionExist.getMontant();
                    Double montantTotalAvecFrais = montantTransaction * 1.02;

                    if (montantTotalAvecFrais <= utilisateur.getCompte().getSolde()) {
                        // Déduire le montant total (montant + 2%) de l'acheteur
                        Wallet acheteurWallet = utilisateur.getCompte();
                        acheteurWallet.setSolde(acheteurWallet.getSolde() - montantTotalAvecFrais);

                        // Ajouter 2% au compte de l'admin
                        Utilisateur admin = utilisateurRepo.findByEmail("fakoro88@gmail.com");
                        Wallet walletAdmin = admin.getCompte();
                        walletAdmin.setSolde(walletAdmin.getSolde() + (montantTransaction * 0.02));

                        // Statut de l'annonce à VENDU et montant bloqué du vendeur
                        transactionExist.getPhone().setStatut(AnnonceStatut.VENDU);
                        Utilisateur vendeur = transactionExist.getPhone().getUtilisateur();
                        Wallet walletVendeur = vendeur.getCompte();
                        walletVendeur.setMontantBloque(walletVendeur.getMontantBloque() + (montantTransaction * 0.98));

                        // Sauvegarde des modifications
                        walletRepo.save(acheteurWallet);
                        walletRepo.save(walletAdmin);
                        walletRepo.save(walletVendeur);

                        // Mettre à jour la date de la transaction et le statut
                        transactionExist.setDateDeTransaction(new Date());
                        transactionExist.setStatut(TransactionStatut.PAYER);

                        // Créer une commande associée à cette transaction
                        commandeService.creerCommande(transactionExist);

                        return transactionRepo.save(transactionExist);
                    } else {
                        throw new IllegalArgumentException("Solde insuffisant");
                    }
                } else {
                    throw new IllegalArgumentException("La transaction a déjà été payée");
                }
            } else {
                throw new IllegalArgumentException("Code secret erroné");
            }
        } else {
            throw new IllegalArgumentException("Transaction non trouvée");
        }
    }


    public Map<String, Object> getTotalMontantVentes() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // Montant total des ventes aujourd'hui
        String totalMontantVentesAujourd = transactionRepo.sumTotalByStatutAndDate(TransactionStatut.PAYER, today);
        BigDecimal totalMontantAujourd = new BigDecimal(totalMontantVentesAujourd != null ? totalMontantVentesAujourd : "0");

        // Montant total des ventes hier
        String totalMontantVentesHier = transactionRepo.sumTotalByStatutAndDate(TransactionStatut.PAYER, yesterday);

        return getStringObjectMap(totalMontantVentesHier, totalMontantAujourd);
    }

    private static Map<String, Object> getStringObjectMap(String totalMontantVentesHier, BigDecimal totalMontantAujourd) {
        BigDecimal totalMontantHier = new BigDecimal(totalMontantVentesHier != null ? totalMontantVentesHier : "0");

        // Calculer le pourcentage de variation
        BigDecimal pourcentageVariation = BigDecimal.ZERO;
        if (totalMontantHier.compareTo(BigDecimal.ZERO) > 0) {
            pourcentageVariation = (totalMontantAujourd.subtract(totalMontantHier))
                    .divide(totalMontantHier, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        // Créer une réponse contenant le total des ventes et le pourcentage de variation
        Map<String, Object> response = new HashMap<>();
        response.put("totalMontantVentes", totalMontantAujourd);
        response.put("pourcentageVariation", pourcentageVariation);
        return response;
    }


}
