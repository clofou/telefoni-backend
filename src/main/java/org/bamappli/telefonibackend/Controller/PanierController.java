package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Brand;
import org.bamappli.telefonibackend.Entity.Panier;
import org.bamappli.telefonibackend.Services.BrandService;
import org.bamappli.telefonibackend.Services.PanierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "panier")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class PanierController {

    private PanierService panierService;

    @GetMapping
    public List<Panier> liste(){
        return panierService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Panier> un(@PathVariable Long id){
        return panierService.trouver(id);
    }

}
