package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Reparation;
import org.bamappli.telefonibackend.Enum.ReparationStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReparationRepo extends JpaRepository<Reparation, Long> {
    @Query("SELECT COUNT(r) FROM Reparation r WHERE r.statut = :statut AND r.dateReparation BETWEEN :startDate AND :endDate")
    Long countReparationsByStatusBetweenDates(ReparationStatut statut, Date startDate, Date endDate);

    @Query("SELECT r, COUNT(rep) as nombreReparations FROM Reparateur r JOIN Reparation rep ON r.id = rep.reparateur.id GROUP BY r ORDER BY nombreReparations DESC")
    List<Object[]> findReparateursOrderedByReparations();

}