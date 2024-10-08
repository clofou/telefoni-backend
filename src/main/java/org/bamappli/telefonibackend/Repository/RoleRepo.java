package org.bamappli.telefonibackend.Repository;


import org.bamappli.telefonibackend.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByNom(String nom);
}
