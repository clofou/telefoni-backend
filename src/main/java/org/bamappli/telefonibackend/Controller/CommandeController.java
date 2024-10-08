package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Brand;
import org.bamappli.telefonibackend.Entity.Commande;
import org.bamappli.telefonibackend.Services.BrandService;
import org.bamappli.telefonibackend.Services.CommandeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "commande")
@AllArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public Commande creer(@RequestBody Commande commande){
        return commandeService.creer(commande);
    }

    @GetMapping
    public List<Commande> liste(){
        return commandeService.recuperer();
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
