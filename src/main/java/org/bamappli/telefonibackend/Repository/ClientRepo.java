package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ClientRepo extends JpaRepository<Client, Long> {
    @Query("SELECT c, COUNT(a) as nombreVentes FROM Client c JOIN Annonce a ON c.id = a.utilisateur.id WHERE a.statut = :statut GROUP BY c ORDER BY nombreVentes DESC")
    List<Object[]> findClientsOrderedBySales(@Param("statut") AnnonceStatut statut);

    // Récupérer les utilisateurs créés à partir d'hier jusqu'à aujourd'hui
    @Query("SELECT u FROM Client u WHERE u.dateCreation >= :dateDebut")
    List<Client> findNouveauxUtilisateursDepuis(@Param("dateDebut") LocalDateTime dateDebut);

}