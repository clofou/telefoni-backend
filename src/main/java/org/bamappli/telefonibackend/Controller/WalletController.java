package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Wallet;
import org.bamappli.telefonibackend.Services.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "wallet")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class WalletController{

    private WalletService walletService;

    @PostMapping
    public Wallet creer(@RequestBody Wallet wallet){
        return walletService.creer(wallet);
    }

    @GetMapping
    public List<Wallet> liste(){
        return walletService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Wallet> un(@PathVariable Long id){
        return walletService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Wallet modifier(@PathVariable Long id, @RequestBody Wallet wallet){
        return walletService.modifer(id,wallet);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        walletService.supprimer(id);
    }
}
