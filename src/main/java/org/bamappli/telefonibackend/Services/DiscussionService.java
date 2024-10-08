package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Admin;
import org.bamappli.telefonibackend.Entity.Annonce;
import org.bamappli.telefonibackend.Entity.Discussion;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Repository.AnnonceRepo;
import org.bamappli.telefonibackend.Repository.DiscussionRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscussionService implements CrudService<Long, Discussion> {

    private final DiscussionRepo discussionRepo;
    private final UserService userService;
    private final AnnonceRepo annonceRepo;


    @Override
    public Discussion creer(Discussion discussion) {
        Utilisateur utilisateur = userService.getCurrentUser();
        discussion.setAcheteur(utilisateur);
        Optional<Annonce> optionalAnnonce = annonceRepo.findById(discussion.getAnnonce().getId());
        if (optionalAnnonce.isPresent()){
            discussion.setVendeur(optionalAnnonce.get().getUtilisateur());
            return discussionRepo.save(discussion);
        }
        throw new IllegalArgumentException("L'annonce n'existe plus");
    }

    @Override
    public Discussion modifer(Long aLong, Discussion discussion) {
        return null;
    }

    @Override
    public Optional<Discussion> trouver(Long id) {
        return discussionRepo.findById(id);
    }

    @Override
    public List<Discussion> recuperer() {
        return discussionRepo.findAll();
    }

    @Override
    public void supprimer(Long aLong) {

    }
}
