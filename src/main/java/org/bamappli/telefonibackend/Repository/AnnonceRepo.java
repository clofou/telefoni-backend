package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnnonceRepo extends JpaRepository<Annonce, Long> {


}
