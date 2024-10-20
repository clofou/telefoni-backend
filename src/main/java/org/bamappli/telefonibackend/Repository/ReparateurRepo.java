package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Reparateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReparateurRepo extends JpaRepository<Reparateur, Long> {
    @Query("SELECT r, COUNT(rep) as nombreReparations FROM Reparateur r JOIN Reparation rep ON r.id = rep.reparateur.id GROUP BY r ORDER BY nombreReparations DESC")
    List<Object[]> findReparateursOrderedByReparations();
}
