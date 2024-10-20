package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.CommandeDTO;
import org.bamappli.telefonibackend.Entity.*;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.bamappli.telefonibackend.Enum.CommandeStatut;
import org.bamappli.telefonibackend.Enum.TransactionStatut;
import org.bamappli.telefonibackend.Mapper.CommandeDTOMapper;
import org.bamappli.telefonibackend.Repository.CommandeRepo;
import org.bamappli.telefonibackend.Repository.TransactionRepo;
import org.bamappli.telefonibackend.Repository.WalletRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CommandeService implements CrudService<Long, Commande> {

    private final UserService userService;
    private final CommandeRepo commandeRepo;
    private final CommandeDTOMapper commandeDTOMapper;
    private final WalletRepo walletRepo;
    private final PasswordEncoder passwordEncoder;
    private final TransactionRepo transactionRepo;


    @Override
    public Commande creer(Commande commande) {
        Utilisateur user = userService.getCurrentUser();
        commande.setController((Controller) user);
        return commandeRepo.save(commande);
    }

    @Override
    public Commande modifer(Long id, Commande commande) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Commande> commande1 = commandeRepo.findById(id);

        if (commande1.isPresent() && Objects.equals(user.getId(), commande1.get().getController().getId())){
            Commande brandExist = commande1.get();
            if (commande.getStatutAcheteur() != null) brandExist.setStatutAcheteur(commande.getStatutAcheteur());
            return commandeRepo.save(brandExist);
        }else{
            throw new IllegalArgumentException("La Commande n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Commande> trouver(Long id) {
        return commandeRepo.findById(id);
    }

    @Override
    public List<Commande> recuperer() {
        return commandeRepo.findAll();
    }

    public Stream<CommandeDTO> recuperer1() {
        return commandeRepo.findAll().stream().map(commandeDTOMapper);
    }
    public double getPourcentageAugmentationCommandes() {
        // Récupérer la date d'aujourd'hui et d'hier
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // Récupérer le nombre de commandes pour aujourd'hui et hier
        Long commandesToday = commandeRepo.countCommandesByDate(today);
        Long commandesYesterday = commandeRepo.countCommandesByDate(yesterday);

        // Si pas de commandes hier, éviter la division par zéro
        if (commandesYesterday == 0) {
            return (commandesToday > 0) ? 100 : 0; // 100% d'augmentation si commandesToday > 0
        }

        // Calcul du pourcentage d'augmentation

        return ((double) (commandesToday - commandesYesterday) / commandesYesterday) * 100;
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Commande> commande = commandeRepo.findById(id);
        if(commande.isPresent()){
            if ((Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getRole().getNom(), "CONTROLLER"))){
                commandeRepo.deleteById(id);
            }
        }else{
            throw new IllegalArgumentException("La Commande mentionne n'existe pas");
        }

    }

    public Commande creerCommande(Transaction transaction) {
        Commande commande = new Commande();
        commande.setTransaction(transaction);
        commande.setStatutAcheteur(CommandeStatut.EN_ATTENTE);
        commande.setStatutVendeur(CommandeStatut.EN_ATTENTE);
        commande.setStatutController(CommandeStatut.EN_ATTENTE);

        return commandeRepo.save(commande);
    }

    // Méthode pour mettre à jour le statut
    public Commande updateStatut(Long commandeId, CommandeStatut statutAcheteur, CommandeStatut statutVendeur, CommandeStatut statutController) {
        Commande commande = commandeRepo.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));

        if (statutAcheteur != null) {
            commande.setStatutAcheteur(statutAcheteur);
            if (statutAcheteur == CommandeStatut.LIVRER){
                commande.setDateLivraison(new Date());
            }

        }
        if (statutVendeur != null) commande.setStatutVendeur(statutVendeur);
        if (statutController != null) {
            Controller controller = (Controller) userService.getCurrentUser();
            commande.setController(controller);

            if (statutController == CommandeStatut.LIVRER){
                Wallet vendeurWallet = commande.getTransaction().getPhone().getUtilisateur().getCompte();
                double soldeadeduire = 2*vendeurWallet.getMontantBloque()/100;
                vendeurWallet.setMontantBloque(vendeurWallet.getMontantBloque() - soldeadeduire);
                Wallet controllerWallet = commande.getController().getCompte();
                controllerWallet.setSolde(soldeadeduire);
            }
            commande.setStatutController(statutController);

        }

        commandeRepo.save(commande);
        return commande;
    }

    // Méthode pour débloquer tous les montants bloqués qui remplissent les conditions
    public Commande debloquerMontantsBloques() {
        Utilisateur utilisateur = userService.getCurrentUser();
        // Récupérer toutes les commandes via les annonces créées par ce vendeur
        List<Commande> commandes = commandeRepo.findByVendeurId(utilisateur.getId()); // Méthode à ajouter dans le repo

        for (Commande commande : commandes) {
            // Vérifier si la commande a été marquée livrée par l'acheteur et le contrôleur
            if (commande.getStatutAcheteur() == CommandeStatut.LIVRER
                    && commande.getStatutController() == CommandeStatut.LIVRER) {

                // Récupérer l'annonce associée via la transaction
                Annonce annonce = commande.getTransaction().getPhone();
                Utilisateur vendeur = annonce.getUtilisateur(); // Accéder au vendeur via l'annonce

                // Vérifier si le vendeur est bien celui pour qui nous débloquons le montant
                if (vendeur.getId().equals(utilisateur.getId())) {
                    // Calculer le nombre de jours écoulés depuis la date de transaction
                    Date dateLivraison = commande.getTransaction().getDateDeTransaction();
                    long joursEcoules = (new Date().getTime() - dateLivraison.getTime()) / (1000 * 60 * 60 * 24);

                    // Si la garantie est dépassée, débloquer le montant
                    if (joursEcoules >= annonce.getDateDeGarantit()) {
                        Wallet walletVendeur = vendeur.getCompte();

                        // Ajouter le montant bloqué pour cette transaction au solde principal du vendeur
                        walletVendeur.setSolde(walletVendeur.getSolde() + commande.getTransaction().getMontant());

                        // Mettre le montant bloqué à 0 pour cette transaction
                        walletVendeur.setMontantBloque(walletVendeur.getMontantBloque() - commande.getTransaction().getMontant());

                        // Sauvegarder les changements
                        walletRepo.save(walletVendeur);
                    }
                }
            }
        }
        return null;
    }

    public void annulerCommande(Long commandeId, String codeSecret) {
        Commande commande = commandeRepo.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));

        Transaction transaction = commande.getTransaction();
        Utilisateur acheteur = userService.getCurrentUser();

        if (passwordEncoder.matches(codeSecret, acheteur.getCompte().getCodeSecret())) {
            if (commande.getStatutAcheteur() != CommandeStatut.LIVRER && commande.getStatutController() != CommandeStatut.LIVRER) {
                // Restituer le montant à l'acheteur - 2% pour l'admin et -2% si livré par le controller
                Wallet walletAcheteur = acheteur.getCompte();
                walletAcheteur.setSolde(walletAcheteur.getSolde() + (transaction.getMontant() * 0.96)); // -4% total
                walletRepo.save(walletAcheteur);

                // Mettre à jour les statuts
                transaction.setStatut(TransactionStatut.ANNULER);
                commande.setStatutAcheteur(CommandeStatut.ANNULE);
                transaction.getPhone().setStatut(AnnonceStatut.EN_VENTE);

                transactionRepo.save(transaction);
                commandeRepo.save(commande);
            } else {
                throw new IllegalArgumentException("Commande déjà livrée, impossible d'annuler");
            }
        } else {
            throw new IllegalArgumentException("Code secret erroné");
        }
    }

}
