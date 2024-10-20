package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Commande;
import org.bamappli.telefonibackend.Enum.CommandeStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CommandeRepo extends JpaRepository<Commande, Long> {
    @Query("SELECT c FROM Commande c WHERE c.transaction.phone.utilisateur.id = :vendeurId")
    List<Commande> findByVendeurId(@Param("vendeurId") Long vendeurId);

    @Query("SELECT COUNT(c) FROM Commande c WHERE c.statutAcheteur = :statutAcheteur AND c.statutController = :statutController AND c.dateLivraison BETWEEN :startDate AND :endDate")
    Long countCommandesLivreesBetweenDates(CommandeStatut statutAcheteur, CommandeStatut statutController, Date startDate, Date endDate);

    @Query("SELECT FUNCTION('MONTH', c.transaction.dateDeTransaction) as mois, c.transaction.phone.phone.type, COUNT(c) as nombreVentes " +
            "FROM Commande c " +
            "WHERE c.statutAcheteur = 'LIVRER' " +
            "GROUP BY mois, c.transaction.phone.phone.type ")
    List<Object[]> findMonthlySalesByPhoneType();

    @Query("SELECT COUNT(c) FROM Commande c WHERE DATE(c.dateLivraison) = :date")
    Long countCommandesByDate(@Param("date") LocalDate date);
}
