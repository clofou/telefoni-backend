package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.CommandeDTO;
import org.bamappli.telefonibackend.DTO.CommandeStatutDTO;
import org.bamappli.telefonibackend.Entity.Commande;
import org.bamappli.telefonibackend.Services.CommandeService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "commande")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CommandeController {

    private CommandeService commandeService;

    @PostMapping
    public Commande creer(@RequestBody Commande commande){
        return commandeService.creer(commande);
    }

    @GetMapping
    public Stream<CommandeDTO> liste(){
        return commandeService.recuperer1();
    }

    @GetMapping(path = "total")
    public double nombreCommande(){
        return commandeService.getPourcentageAugmentationCommandes();
    }

    @GetMapping(path = "/{id}")
    public Optional<Commande> un(@PathVariable Long id){
        return commandeService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Commande modifier(@PathVariable Long id, @RequestBody CommandeStatutDTO commande){
        return commandeService.updateStatut(id,commande.getAcheteur(), commande.getVendeur(), commande.getController());
    }

    @PatchMapping(path = "montant/debloquer")
    public Commande modifier(){
        return commandeService.debloquerMontantsBloques();
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        commandeService.supprimer(id);
    }
}
