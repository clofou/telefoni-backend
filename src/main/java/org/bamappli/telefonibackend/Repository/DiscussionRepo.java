package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Discussion;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepo extends JpaRepository<Discussion, Long> {
    List<Discussion> findByAcheteurOrVendeur(Utilisateur acheteur, Utilisateur vendeur);
}
