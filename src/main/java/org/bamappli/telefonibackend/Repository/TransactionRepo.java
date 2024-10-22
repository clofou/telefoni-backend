package org.bamappli.telefonibackend.Repository;


import org.bamappli.telefonibackend.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}