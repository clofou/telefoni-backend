package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.CommandeDTO;
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

    @GetMapping(path = "/{id}")
    public Optional<Commande> un(@PathVariable Long id){
        return commandeService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Commande modifier(@PathVariable Long id, @RequestBody Commande commande){
        return commandeService.modifer(id,commande);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        commandeService.supprimer(id);
    }
}
