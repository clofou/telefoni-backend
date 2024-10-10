package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Litige;
import org.bamappli.telefonibackend.Services.LitigeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "litige")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class LitigeController {

    private LitigeService litigeService;

    @PostMapping
    public Litige creer(@RequestBody Litige litige){
        return litigeService.creer(litige);
    }

    @GetMapping
    public List<Litige> liste(){
        return litigeService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Litige> un(@PathVariable Long id){
        return litigeService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Litige modifier(@PathVariable Long id, @RequestBody Litige litige){
        return litigeService.modifer(id,litige);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        litigeService.supprimer(id);
    }
}
