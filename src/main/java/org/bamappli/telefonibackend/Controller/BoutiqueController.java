package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.BoutiqueResponseDTO;
import org.bamappli.telefonibackend.DTO.BoutiqueSalesDTO;
import org.bamappli.telefonibackend.Entity.Boutique;
import org.bamappli.telefonibackend.Services.BoutiqueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "boutique")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class BoutiqueController {

    private BoutiqueService boutiqueService;

    @PostMapping
    public Boutique creer(@RequestBody Boutique boutique){
        return boutiqueService.creer(boutique);
    }

    @PatchMapping(path = "/{id}")
    public Boutique modifier(@RequestBody Boutique boutique, @PathVariable Long id){
        return boutiqueService.modifer(id, boutique);
    }

    @GetMapping
    public Stream<BoutiqueResponseDTO> recuperer(){
        return boutiqueService.recupererDTO();
    }

    @GetMapping(path = "order")
    public List<BoutiqueSalesDTO> recupererOrder(){
        return boutiqueService.getBoutiquesOrderedBySales();
    }
}
