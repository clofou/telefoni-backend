package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.ClientSalesDTO;
import org.bamappli.telefonibackend.Entity.*;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.bamappli.telefonibackend.Repository.*;
import org.bamappli.telefonibackend.Utils.UserService;
import org.bamappli.telefonibackend.Utils.UtilService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService implements CrudService<Long, Client> {

    private final UtilisateurRepo utilisateurRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final WalletService walletService;
    private final ClientRepo clientRepo;
    private final PanierRepo panierRepo;

    @Override
    public Client creer(Client client) {

        // Verifier si le mail et le mot de passe sont valides
        UtilService.checkEmailAndPassword(client.getEmail(), client.getMotDePasse());

        Utilisateur utilisateur = utilisateurRepo.findByEmail(client.getEmail());
        if (utilisateur != null){
            throw new RuntimeException("Le Compte du Client Existe deja");
        }


        // Attribution d'un role
        Role role = roleRepo.findByNom("CLIENT");
        if (role == null){
            throw new RuntimeException("Le role n'existe pas");
        }
        client.setRole(role);
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));

        // Creer Un Compte par defaut
        Wallet wallet = new Wallet();
        wallet.setCodeSecret("000000");
        Wallet compte = walletService.creer(wallet);
        client.setCompte(compte);

        // Creer Un Panier par defaut
        Panier panier = panierRepo.save(new Panier());
        client.setCompte(compte);
        client.setPanier(panier);

        return clientRepo.save(client);
    }

    @Override
    public Client modifer(Long id, Client client) {
        Utilisateur user = userService.getCurrentUser();
        if (client.getMotDePasse() != null && UtilService.isValidPassword(client.getMotDePasse())){
            throw new IllegalArgumentException("Le Mot de passe doit comporter plus de 6 caracteres");
        }
        Optional<Client> client1 = clientRepo.findById(id);

        if (client1.isPresent() && Objects.equals(client1.get().getId(), user.getId())){
            Client clienExist = client1.get();
            if (client.getNom() != null) clienExist.setNom(client.getNom());
            if (client.getAdresse() != null) clienExist.setAdresse(client.getAdresse());
            if (client.getNumeroDeTelephone() != null) clienExist.setNumeroDeTelephone(client.getNumeroDeTelephone());
            return clientRepo.save(clienExist);
        }else{
            throw new IllegalArgumentException("Le Client n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Client> trouver(Long id) {
        return clientRepo.findById(id);
    }

    @Override
    public List<Client> recuperer() {
        return clientRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Optional<Client> client = clientRepo.findById(id);
        client.ifPresent(clientRepo::delete);
    }

    public List<ClientSalesDTO> getClientsOrderedBySales() {
        List<Object[]> results = clientRepo.findClientsOrderedBySales(AnnonceStatut.VENDU);
        List<ClientSalesDTO> clients = new ArrayList<>();

        for (Object[] result : results) {
            Client client = (Client) result[0];
            Long nombreVentes = (Long) result[1];

            ClientSalesDTO dto = new ClientSalesDTO();
            dto.setNom(client.getNom());
            dto.setEmail(client.getEmail());
            dto.setNombreDeVentes(nombreVentes);

            clients.add(dto);
        }

        return clients;
    }
}
