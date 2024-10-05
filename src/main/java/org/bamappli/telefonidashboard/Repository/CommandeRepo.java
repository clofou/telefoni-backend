package org.bamappli.telefonidashboard.Repository;

import org.bamappli.telefonidashboard.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepo extends JpaRepository<Commande, Long> {

}
