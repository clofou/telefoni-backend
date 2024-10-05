package org.bamappli.telefonidashboard.Repository;

import org.bamappli.telefonidashboard.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepo extends JpaRepository<Stock, Long> {

}