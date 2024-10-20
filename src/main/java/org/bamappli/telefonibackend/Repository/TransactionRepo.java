package org.bamappli.telefonibackend.Repository;


import org.bamappli.telefonibackend.Entity.Transaction;
import org.bamappli.telefonibackend.Enum.TransactionStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.statut = :statut")
    String sumTotalByStatut(@Param("statut") TransactionStatut statut);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.statut = :statut AND t.dateDeTransaction BETWEEN :startDate AND :endDate")
    Long countTransactionsByStatusBetweenDates(TransactionStatut statut, Date startDate, Date endDate);

    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.statut = :statut AND DATE(t.dateDeTransaction) = :date")
    String sumTotalByStatutAndDate(@Param("statut") TransactionStatut statut, @Param("date") LocalDate date);

}