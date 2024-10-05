package org.bamappli.telefonidashboard.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonidashboard.Entity.Role;
import org.bamappli.telefonidashboard.Repository.RoleRepo;
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
