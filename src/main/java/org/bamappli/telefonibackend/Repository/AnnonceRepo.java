package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Annonce;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AnnonceRepo extends JpaRepository<Annonce, Long> {
    @Query("SELECT a FROM Annonce a WHERE a.statut = 'VENDU' AND a.valideeParController = true")
    List<Annonce> findAnnoncesVendues();

    List<Annonce> findByStatut(AnnonceStatut statut);
}
