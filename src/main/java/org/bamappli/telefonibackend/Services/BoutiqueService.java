package org.bamappli.telefonibackend.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.BoutiqueResponseDTO;
import org.bamappli.telefonibackend.DTO.BoutiqueSalesDTO;
import org.bamappli.telefonibackend.Entity.*;
import org.bamappli.telefonibackend.Mapper.BoutiqueDTOMapper;
import org.bamappli.telefonibackend.Repository.BoutiqueRepo;
import org.bamappli.telefonibackend.Repository.RoleRepo;
import org.bamappli.telefonibackend.Repository.UtilisateurRepo;
import org.bamappli.telefonibackend.Repository.WalletRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.bamappli.telefonibackend.Utils.UtilService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BoutiqueService implements CrudService<Long, Boutique> {

    private final BoutiqueRepo boutiqueRepo;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepo utilisateurRepo;
    private final RoleRepo roleRepo;
    private final UserService userService;
    private final WalletService walletService;
    private final BoutiqueDTOMapper boutiqueDTOMapper;

    @Override
    @Transactional
    public Boutique creer(Boutique boutique) {
        Utilisateur admin = userService.getCurrentUser();

        // Verifier si le mail et le mot de passe sont valides
        UtilService.checkEmailAndPassword(boutique.getEmail(), boutique.getMotDePasse());

        Utilisateur utilisateur = utilisateurRepo.findByEmail(boutique.getEmail());
        if (utilisateur != null){
            throw new RuntimeException("La Boutique Existe deja existe deja");
        }

        // Attribution d'un role
        Role role = roleRepo.findByNom("BOUTIQUE");
        if (role == null){
            throw new RuntimeException("Le role n'existe pas");
        }
        boutique.setRole(role);


        boutique.setAdmin((Admin) admin);
        boutique.setMotDePasse(passwordEncoder.encode(boutique.getMotDePasse()));

        // Creer Un Compte par defaut
        Wallet wallet = new Wallet();
        wallet.setCodeSecret("000000");
        Wallet compte = walletService.creer(wallet);
        boutique.setCompte(compte);

        return boutiqueRepo.save(boutique);
    }

    @Override
    public Boutique modifer(Long id, Boutique boutique) {
        Utilisateur user = userService.getCurrentUser();
        if (UtilService.isValidPassword(boutique.getMotDePasse())){
            throw new IllegalArgumentException("Le Mot de passe doit comporter plus de 6 caracteres");
        }
        Optional<Boutique> boutique1 = boutiqueRepo.findById(id);

        if (boutique1.isPresent() && (Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getId(), boutique1.get().getId()))){
            Boutique boutiqueExist = boutique1.get();
            if (boutique.getNom() != null) boutiqueExist.setNom(boutique.getNom());
            if (boutique.getNumeroDeTelephone() != null) boutiqueExist.setNumeroDeTelephone(boutique.getNumeroDeTelephone());
            return boutiqueRepo.save(boutiqueExist);
        }else{
            throw new IllegalArgumentException("La boutique n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Boutique> trouver(Long id) {
        return boutiqueRepo.findById(id);
    }

    @Override
    public List<Boutique> recuperer() {
        return List.of();
    }

    public Stream<BoutiqueResponseDTO> recupererDTO() {
        return boutiqueRepo.findAll()
                .stream().map(boutiqueDTOMapper);
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Boutique> boutique = boutiqueRepo.findById(id);
        if (boutique.isPresent()) {
            if ((Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getId(), id))) {
                boutiqueRepo.deleteById(id);
            }
        }else{
            throw new IllegalArgumentException("La Boutique mentionne n'existe pas");
        }
    }

    public List<BoutiqueSalesDTO> getBoutiquesOrderedBySales() {
        List<Object[]> results = boutiqueRepo.findBoutiquesOrderedBySales();
        List<BoutiqueSalesDTO> boutiques = new ArrayList<>();

        for (Object[] result : results) {
            Boutique boutique = (Boutique) result[0];
            Long nombreVentes = (Long) result[1];

            BoutiqueSalesDTO dto = new BoutiqueSalesDTO();
            dto.setNom(boutique.getNom());
            dto.setEmail(boutique.getEmail());
            dto.setNombreDeVentes(nombreVentes);

            boutiques.add(dto);
        }

        return boutiques;
    }
}
