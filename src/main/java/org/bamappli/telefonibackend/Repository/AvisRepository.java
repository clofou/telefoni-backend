package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Annonce;
import org.bamappli.telefonibackend.Entity.Avis;
import org.bamappli.telefonibackend.Entity.Reparation;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {
    Optional<Avis> findByEvaluateurAndEvalueAndAnnonce(Utilisateur evaluateur, Utilisateur evalue, Annonce annonce);

    Optional<Avis> findByEvaluateurAndEvalueAndReparation(Utilisateur evaluateur, Utilisateur evalue, Reparation reparation);

    @Query("SELECT COUNT(a) FROM Avis a WHERE a.dateCreation BETWEEN :startDate AND :endDate")
    Long countAvisBetweenDates(Date startDate, Date endDate);
}

