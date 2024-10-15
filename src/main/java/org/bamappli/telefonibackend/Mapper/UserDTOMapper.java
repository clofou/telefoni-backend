package org.bamappli.telefonibackend.Mapper;

import org.bamappli.telefonibackend.DTO.UtilisateurDTO;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOMapper implements Function<Utilisateur, UtilisateurDTO> {
    @Override
    public UtilisateurDTO apply(Utilisateur utilisateur) {
        return new UtilisateurDTO(utilisateur.getNom(), utilisateur.getEmail(), utilisateur.getAdresse(), utilisateur.getRole().getNom(), utilisateur.getPhotoUrl(), utilisateur.getNumeroDeTelephone());
    }
}
