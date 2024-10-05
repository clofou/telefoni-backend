package org.bamappli.telefonidashboard.Repository;


import org.bamappli.telefonidashboard.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {

}