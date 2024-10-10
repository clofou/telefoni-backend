package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Brand;
import org.bamappli.telefonibackend.Services.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "brand")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class BrandController {

    private BrandService brandService;

    @PostMapping
    public Brand creer(@RequestBody Brand brand){
        return brandService.creer(brand);
    }

    @GetMapping
    public List<Brand> liste(){
        return brandService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Brand> un(@PathVariable Long id){
        return brandService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Brand modifier(@PathVariable Long id, @RequestBody Brand brand){
        return brandService.modifer(id,brand);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        brandService.supprimer(id);
    }
}
