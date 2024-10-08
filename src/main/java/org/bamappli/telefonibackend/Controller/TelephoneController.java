package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Brand;
import org.bamappli.telefonibackend.Entity.Telephone;
import org.bamappli.telefonibackend.Services.BrandService;
import org.bamappli.telefonibackend.Services.TelephoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "telephone")
@AllArgsConstructor
public class TelephoneController {

    private final TelephoneService telephoneService;

    @PostMapping
    public Telephone creer(@RequestBody Telephone telephone){
        return telephoneService.creer(telephone);
    }

    @GetMapping
    public List<Telephone> liste(){
        return telephoneService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Telephone> un(@PathVariable Long id){
        return telephoneService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Telephone modifier(@PathVariable Long id, @RequestBody Telephone telephone){
        return telephoneService.modifer(id,telephone);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        telephoneService.supprimer(id);
    }
}
