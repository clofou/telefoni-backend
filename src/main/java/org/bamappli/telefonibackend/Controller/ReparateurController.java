package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Controller;
import org.bamappli.telefonibackend.Entity.Reparateur;
import org.bamappli.telefonibackend.Services.ReparateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "reparateur")
@AllArgsConstructor
public class ReparateurController {

    private final ReparateurService reparateurService;

    @PostMapping
    public Reparateur creer(@RequestBody Reparateur reparateur){
        return reparateurService.creer(reparateur);
    }

    @GetMapping
    public List<Reparateur> liste(){
        return reparateurService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Reparateur> un(@PathVariable Long id){
        return reparateurService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Reparateur modifier(@PathVariable Long id, @RequestBody Reparateur reparateur){
        return reparateurService.modifer(id,reparateur);
    }
}
