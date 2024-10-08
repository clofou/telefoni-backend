package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Role;
import org.bamappli.telefonibackend.Repository.RoleRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    public Role creerRole(Role role) {
        Role existRole = roleRepo.findByNom(role.getNom());
        if (existRole != null){
            return null;
        }
        return roleRepo.save(role);
    }
}
