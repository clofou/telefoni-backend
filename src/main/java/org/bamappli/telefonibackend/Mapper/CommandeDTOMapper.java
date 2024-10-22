package org.bamappli.telefonibackend.Mapper;

import org.bamappli.telefonibackend.DTO.CommandeDTO;
import org.bamappli.telefonibackend.Entity.Commande;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommandeDTOMapper implements Function<Commande, CommandeDTO> {
    @Override
    public CommandeDTO apply(Commande commande) {
        return new CommandeDTO(commande.getId(), commande.getTransaction().getPhone().getPhone().getTitre(), commande.getTransaction().getMontant(), commande.getTransaction().getAcheteur().getNom(), commande.getStatut());
    }
}
