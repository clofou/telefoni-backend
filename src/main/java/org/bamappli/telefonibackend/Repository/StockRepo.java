package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepo extends JpaRepository<Stock, Long> {

}