package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Reparation;
import org.bamappli.telefonibackend.Services.ReparationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "reparation")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ReparationController {
    private ReparationService reparationService;

    @PostMapping
    public Reparation creer(@RequestBody Reparation reparation){
        return reparationService.creer(reparation);
    }

    @GetMapping
    public List<Reparation> liste(){
        return reparationService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Reparation> un(@PathVariable Long id){
        return reparationService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Reparation modifier(@PathVariable Long id, @RequestBody Reparation reparation){
        return reparationService.modifer(id,reparation);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        reparationService.supprimer(id);
    }
}
