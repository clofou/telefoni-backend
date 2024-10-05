package org.bamappli.telefonidashboard.Repository;

import org.bamappli.telefonidashboard.Entity.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepo extends JpaRepository<Telephone, Long> {

}