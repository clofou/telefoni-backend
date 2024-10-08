package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Modele;
import org.bamappli.telefonibackend.Services.ModeleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "modele")
@AllArgsConstructor
public class ModeleController {

    private final ModeleService modeleService;

    @PostMapping
    public Modele creer(@RequestBody Modele modele){
        return modeleService.creer(modele);
    }

    @GetMapping
    public List<Modele> liste(){
        return modeleService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Modele> un(@PathVariable Long id){
        return modeleService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Modele modifier(@PathVariable Long id, @RequestBody Modele modele){
        return modeleService.modifer(id,modele);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        modeleService.supprimer(id);
    }
}
