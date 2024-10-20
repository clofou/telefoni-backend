package org.bamappli.telefonibackend.Mapper;

import org.bamappli.telefonibackend.DTO.AnnonceDTO;
import org.bamappli.telefonibackend.Entity.Annonce;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnnonceDTOMapper implements Function<Annonce, AnnonceDTO> {
    @Override
    public AnnonceDTO apply(Annonce annonce) {
        return new AnnonceDTO(annonce.getId(), annonce.getPhone().getTitre(), annonce.getPhone().getPrix().toString(), annonce.getPhone().getType(), annonce.getUtilisateur().getEmail(), annonce.getStatut());
    }
}
