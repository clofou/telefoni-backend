package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoutiqueRepo extends JpaRepository<Boutique, Long> {
    @Query("SELECT b, COUNT(c) as nombreVentes FROM Boutique b JOIN Commande c ON b.id = c.transaction.phone.utilisateur.id GROUP BY b ORDER BY nombreVentes DESC")
    List<Object[]> findBoutiquesOrderedBySales();
}
