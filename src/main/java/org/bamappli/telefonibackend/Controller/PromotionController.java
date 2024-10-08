package org.bamappli.telefonibackend.Controller;


import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Promotion;
import org.bamappli.telefonibackend.Services.PromotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "promotion")
@AllArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public Promotion creer(@RequestBody Promotion promotion){
        return promotionService.creer(promotion);
    }

    @GetMapping
    public List<Promotion> liste(){
        return promotionService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Promotion> un(@PathVariable Long id){
        return promotionService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Promotion modifier(@PathVariable Long id, @RequestBody Promotion promotion){
        return promotionService.modifer(id,promotion);
    }
}
