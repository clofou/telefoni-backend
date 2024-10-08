package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepo extends JpaRepository<Telephone, Long> {

}