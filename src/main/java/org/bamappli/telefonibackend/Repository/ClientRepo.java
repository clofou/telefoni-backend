package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepo extends JpaRepository<Client, Long> {

}