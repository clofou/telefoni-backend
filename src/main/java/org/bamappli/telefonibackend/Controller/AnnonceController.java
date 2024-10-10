package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Annonce;
import org.bamappli.telefonibackend.Entity.Controller;
import org.bamappli.telefonibackend.Services.AnnonceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "annonce")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AnnonceController {

    private AnnonceService annonceService;

    @PostMapping
    public Annonce creer(@RequestBody Annonce annonce){
        return annonceService.creer(annonce);
    }

    @GetMapping
    public List<Annonce> liste(){
        return annonceService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Annonce> un(@PathVariable Long id){
        return annonceService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Annonce modifier(@PathVariable Long id, @RequestBody Annonce annonce){
        return annonceService.modifer(id,annonce);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        annonceService.supprimer(id);
    }
}
