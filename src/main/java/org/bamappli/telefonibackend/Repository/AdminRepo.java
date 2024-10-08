package org.bamappli.telefonibackend.Repository;


import org.bamappli.telefonibackend.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}
