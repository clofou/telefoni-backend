package org.bamappli.telefonidashboard.Repository;


import org.bamappli.telefonidashboard.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}