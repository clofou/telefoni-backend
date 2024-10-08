package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Brand;
import org.bamappli.telefonibackend.Entity.Stock;
import org.bamappli.telefonibackend.Services.BrandService;
import org.bamappli.telefonibackend.Services.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "stock")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public Stock creer(@RequestBody Stock stock){
        return stockService.creer(stock);
    }

    @GetMapping
    public List<Stock> liste(){
        return stockService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Stock> un(@PathVariable Long id){
        return stockService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Stock modifier(@PathVariable Long id, @RequestBody Stock stock){
        return stockService.modifer(id,stock);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        stockService.supprimer(id);
    }
}
