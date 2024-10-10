package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.CommandeDTO;
import org.bamappli.telefonibackend.Entity.*;
import org.bamappli.telefonibackend.Mapper.CommandeDTOMapper;
import org.bamappli.telefonibackend.Repository.CommandeRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CommandeService implements CrudService<Long, Commande> {

    private final UserService userService;
    private final CommandeRepo commandeRepo;
    private final CommandeDTOMapper commandeDTOMapper;


    @Override
    public Commande creer(Commande commande) {
        Utilisateur user = userService.getCurrentUser();
        commande.setController((Controller) user);
        return commandeRepo.save(commande);
    }

    @Override
    public Commande modifer(Long id, Commande commande) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Commande> commande1 = commandeRepo.findById(id);

        if (commande1.isPresent() && Objects.equals(user.getId(), commande1.get().getController().getId())){
            Commande brandExist = commande1.get();
            if (commande.getStatut() != null) brandExist.setStatut(commande.getStatut());
            return commandeRepo.save(brandExist);
        }else{
            throw new IllegalArgumentException("La Commande n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Commande> trouver(Long id) {
        return commandeRepo.findById(id);
    }

    @Override
    public List<Commande> recuperer() {
        return commandeRepo.findAll();
    }

    public Stream<CommandeDTO> recuperer1() {
        return commandeRepo.findAll().stream().map(commandeDTOMapper);
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Commande> commande = commandeRepo.findById(id);
        if(commande.isPresent()){
            if ((Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getRole().getNom(), "CONTROLLER"))){
                commandeRepo.deleteById(id);
            }
        }else{
            throw new IllegalArgumentException("La Commande mentionne n'existe pas");
        }

    }
}
