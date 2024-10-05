package org.bamappli.telefonidashboard.Repository;

import org.bamappli.telefonidashboard.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepo extends JpaRepository<Client, Long> {

}