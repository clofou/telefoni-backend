package org.bamappli.telefonibackend.Utils;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Repository.UtilisateurRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UtilisateurRepo utilisateurRepo;

    public Utilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return utilisateurRepo.findByEmail(authentication.getName());
    }
}