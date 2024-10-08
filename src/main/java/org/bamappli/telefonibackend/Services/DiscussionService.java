package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Admin;
import org.bamappli.telefonibackend.Entity.Discussion;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Repository.DiscussionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscussionService implements CrudService<Long, Discussion> {

    private final DiscussionRepo discussionRepo;


    @Override
    public Discussion creer(Discussion discussion) {
        return discussionRepo.save(discussion);
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
