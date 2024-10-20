package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.AnnonceDTO;
import org.bamappli.telefonibackend.DTO.ModeleVenduDTO;
import org.bamappli.telefonibackend.DTO.PhoneResponseDto;
import org.bamappli.telefonibackend.Entity.Annonce;
import org.bamappli.telefonibackend.Entity.Boutique;
import org.bamappli.telefonibackend.Entity.Telephone;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.bamappli.telefonibackend.Mapper.AnnonceDTOMapper;
import org.bamappli.telefonibackend.Repository.AnnonceRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AnnonceService implements CrudService<Long, Annonce>{

    private final AnnonceRepo annonceRepo;
    private final UserService userService;
    private final AnnonceDTOMapper annonceDTOMapper;


    @Override
    public Annonce creer(Annonce annonce) {
        Utilisateur user = userService.getCurrentUser();
        annonce.setUtilisateur(user);
        return annonceRepo.save(annonce);
    }

    @Override
    public Annonce modifer(Long id, Annonce annonce) {
        Optional<Annonce> annonce1 = annonceRepo.findById(id);

        if (annonce1.isPresent()){
            Annonce annonceExist = annonce1.get();
            if (annonce.getStatut() != null) annonceExist.setStatut(annonce.getStatut());
            if (annonce.getValideeParController() != null) annonceExist.setValideeParController(annonce.getValideeParController());
            return annonceRepo.save(annonceExist);
        }else{
            throw new IllegalArgumentException("L'annonce n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Annonce> trouver(Long id) {
        return annonceRepo.findById(id);
    }

    @Override
    public List<Annonce> recuperer() {
        return annonceRepo.findAll();
    }

    public Stream<AnnonceDTO> recuperer1() {
        return annonceRepo.findAll().stream().map(annonceDTOMapper);
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Annonce> annonce = annonceRepo.findById(id);

        if (annonce.isPresent()){
            Annonce annonce1 = annonce.get();

            if ((Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getRole().getNom(), "CONTROLLER") || Objects.equals(user.getId(), annonce1.getUtilisateur().getId()))){
                annonceRepo.deleteById(id);
            }

        }else{
            throw new IllegalArgumentException("L'annonce mentionne n'existe pas");
        }

    }

    // Méthode pour récupérer la liste des modèles de téléphones les plus vendus avec leur pourcentage
    public List<ModeleVenduDTO> getModelesLesPlusVendus() {
        List<Annonce> annoncesVendues = annonceRepo.findAnnoncesVendues(); // On récupère toutes les annonces vendues
        long totalAnnoncesVendues = annoncesVendues.size();

        // Créer un map pour compter les ventes par modèle et marque
        Map<String, Long> modeleVentesMap = new HashMap<>();

        for (Annonce annonce : annoncesVendues) {
            Telephone telephone = annonce.getPhone();
            if (telephone != null && telephone.getModele() != null) {
                String key = telephone.getBrand().getNom() + " " + telephone.getModele().getNom();
                modeleVentesMap.put(key, modeleVentesMap.getOrDefault(key, 0L) + 1);
            }
        }

        // Calculer les pourcentages et créer la liste des DTO
        List<ModeleVenduDTO> result = new ArrayList<>();
        for (Map.Entry<String, Long> entry : modeleVentesMap.entrySet()) {
            String brandModele = entry.getKey();
            long nombreVentes = entry.getValue();
            double pourcentageVente = (double) nombreVentes / totalAnnoncesVendues * 100;

            // Séparer la marque et le modèle à partir de la clé
            String[] brandModeleSplit = brandModele.split(" ");
            String brandNom = brandModeleSplit[0];
            String modeleNom = brandModeleSplit.length > 1 ? brandModeleSplit[1] : "";

            result.add(new ModeleVenduDTO(brandNom, modeleNom, pourcentageVente));
        }

        return result;
    }

    /*public List<PhoneResponseDto> getAllPhones() {
        // Récupérer toutes les annonces valides (EN_VENTE par exemple)
        List<Annonce> annonces = annonceRepo.findByStatut(AnnonceStatut.EN_VENTE);

        // Transformer les annonces en PhoneResponseDto
        return annonces.stream().map(this::convertToDto).collect(Collectors.toList());
    }*/

    /*private PhoneResponseDto convertToDto(Annonce annonce) {
        Telephone phone = annonce.getPhone();
        return new PhoneResponseDto(
                phone.getTitre(),
                phone.getDescription(),
                phone.getPrix(),
                annonce.getStatut().name(),
                annonce.getUtilisateur().getNom(),
                annonce.getUtilisateur() instanceof Boutique ? "Boutique" : "Personne Lamda",
                phone.getPhotosList().stream().map(P::getNom).collect(Collectors.toList()),
                false, // Placeholder pour le statut "isLiked"
                0 // Placeholder pour "totalLikes", peut être calculé selon des besoins spécifiques
        );
    }*/
}

