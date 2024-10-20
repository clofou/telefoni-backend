package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.UtilisateurNouveauDTO;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Repository.ClientRepo;
import org.bamappli.telefonibackend.Repository.UtilisateurRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UtilisateurService {

    private final ClientRepo utilisateurRepo;


    // Méthode pour récupérer les nouveaux utilisateurs du jour et calculer le pourcentage de variation
    public Map<String, Object> getNouveauxUtilisateursAvecPourcentage() {
        // Calculer la date d'hier
        LocalDateTime dateDebut = LocalDate.now().minusDays(1).atStartOfDay();

        // Récupérer les utilisateurs créés depuis hier
        List<Client> utilisateursDepuisHier = utilisateurRepo.findNouveauxUtilisateursDepuis(dateDebut);

        // Filtrer les utilisateurs créés aujourd'hui et hier
        List<Client> utilisateursAujourdHui = utilisateursDepuisHier.stream()
                .filter(u -> toLocalDate(u.getDateCreation()).isEqual(LocalDate.now()))
                .collect(Collectors.toList());

        List<Client> utilisateursHier = utilisateursDepuisHier.stream()
                .filter(u -> toLocalDate(u.getDateCreation()).isEqual(LocalDate.now().minusDays(1)))
                .collect(Collectors.toList());

        // Transformer la liste des utilisateurs d'aujourd'hui en DTO
        List<UtilisateurNouveauDTO> utilisateursDTO = utilisateursAujourdHui.stream()
                .map(u -> new UtilisateurNouveauDTO(
                        u.getNom(),
                        u.getEmail(),
                        u.getAdresse() != null ? u.getAdresse() : "Adresse non disponible",
                        u.getNumeroDeTelephone() != null ? u.getNumeroDeTelephone() : "Numéro non disponible"))
                .collect(Collectors.toList());

        // Calculer le pourcentage de variation
        double pourcentageVariation = getPourcentageVariation(utilisateursAujourdHui, utilisateursHier);

        // Créer la réponse
        Map<String, Object> result = new HashMap<>();
        result.put("utilisateurs", utilisateursDTO);
        result.put("pourcentageVariation", pourcentageVariation);

        return result;
    }

    // Méthode pour calculer le pourcentage de variation
    private static double getPourcentageVariation(List<Client> utilisateursAujourdHui, List<Client> utilisateursHier) {
        long nombreUtilisateursAujourdHui = utilisateursAujourdHui.size();
        long nombreUtilisateursHier = utilisateursHier.size();

        double pourcentageVariation;
        if (nombreUtilisateursHier == 0) {
            pourcentageVariation = nombreUtilisateursAujourdHui > 0 ? 100 : 0; // Si aucun utilisateur hier, tout est croissance
        } else {
            pourcentageVariation = ((double) (nombreUtilisateursAujourdHui - nombreUtilisateursHier) / nombreUtilisateursHier) * 100;
        }
        return pourcentageVariation;
    }

    // Méthode pour convertir java.util.Date en java.time.LocalDate
    private LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
