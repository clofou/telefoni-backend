package org.bamappli.telefonibackend.Mapper;

import org.bamappli.telefonibackend.DTO.BoutiqueResponseDTO;
import org.bamappli.telefonibackend.Entity.Boutique;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BoutiqueDTOMapper implements Function<Boutique, BoutiqueResponseDTO> {
    @Override
    public BoutiqueResponseDTO apply(Boutique boutique) {
        return new BoutiqueResponseDTO(boutique.getId(), boutique.getNom(), boutique.getEmail(), boutique.getNumeroDeTelephone(), boutique.getGrade(), boutique.getCompte().getSolde());
    }
}
