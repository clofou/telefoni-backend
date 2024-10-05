package org.bamappli.telefonidashboard.Utils;

import lombok.AllArgsConstructor;
import org.bamappli.telefonidashboard.Entity.Utilisateur;
import org.bamappli.telefonidashboard.Repository.UtilisateurRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UtilisateurRepo utilisateurRepo;

    public Long getCurrentUsernameId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur user = utilisateurRepo.findByEmail(authentication.getName());
        Long id = 0L;
        if (user != null) {
            id = user.getId();
        }

        return id;
    }
}
