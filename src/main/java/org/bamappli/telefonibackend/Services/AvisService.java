package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Annonce;
import org.bamappli.telefonibackend.Entity.Avis;
import org.bamappli.telefonibackend.Entity.Reparation;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Repository.AnnonceRepo;
import org.bamappli.telefonibackend.Repository.AvisRepository;
import org.bamappli.telefonibackend.Repository.ReparationRepo;
import org.bamappli.telefonibackend.Repository.UtilisateurRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AvisService {

    private AvisRepository avisRepository;
    private UtilisateurRepo utilisateurRepository;
    private AnnonceRepo annonceRepository;
    private ReparationRepo reparationRepository;
    private UserService userService;

    public Avis creerAvis(Long evalueId, Long annonceId, Long reparationId, int note, String commentaire) {
        Utilisateur evaluateur = userService.getCurrentUser();

        Utilisateur evalue = utilisateurRepository.findById(evalueId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur évalué non trouvé"));

        Optional<Annonce> optionalAnnonce = annonceRepository.findById(annonceId);
        Avis avis = new Avis();

        Optional<Reparation> optionalReparation = reparationRepository.findById(reparationId);
        if (optionalAnnonce.isPresent()){
            Annonce annonce = optionalAnnonce.get();
            if (avisRepository.findByEvaluateurAndEvalueAndAnnonce(evaluateur, evalue, annonce).isPresent()) {
                throw new IllegalArgumentException("Un avis a déjà été donné pour cette annonce.");
            }
            avis.setAnnonce(annonce);
        } else if (optionalReparation.isPresent()) {
            Reparation reparation = optionalReparation.get();
            if (avisRepository.findByEvaluateurAndEvalueAndReparation(evaluateur, evalue, reparation).isPresent()) {
                throw new IllegalArgumentException("Un avis a déjà été donné pour cette réparation.");
            }
            avis.setReparation(reparation);
        }


        // Créer un nouvel avis

            avis.setEvaluateur(evaluateur);
            avis.setEvalue(evalue);
            avis.setNote(note);
            avis.setCommentaire(commentaire);
            // Enregistrer l'avis
            avis = avisRepository.save(avis);

            // Mettre à jour les points de l'utilisateur évalué
            evalue.setPoints(evalue.getPoints() + note);
            // Réévaluer le grade de l'utilisateur évalué
            evalue.reevaluerGrade();
            // Sauvegarder les modifications de l'utilisateur évalué
            utilisateurRepository.save(evalue);

            return avis;
    }
}

