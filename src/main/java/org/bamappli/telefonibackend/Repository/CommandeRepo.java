package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepo extends JpaRepository<Commande, Long> {

}
