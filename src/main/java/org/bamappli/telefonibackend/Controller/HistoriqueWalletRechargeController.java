package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Discussion;
import org.bamappli.telefonibackend.Entity.HistoriqueWalletRecharge;
import org.bamappli.telefonibackend.Services.DiscussionService;
import org.bamappli.telefonibackend.Services.HistoriqueWalletRechargeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "historiquewallet")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class HistoriqueWalletRechargeController {

    private HistoriqueWalletRechargeService historiqueWalletRechargeService;

    @GetMapping
    public List<HistoriqueWalletRecharge> liste(){
        return historiqueWalletRechargeService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<HistoriqueWalletRecharge> un(@PathVariable Long id){
        return historiqueWalletRechargeService.trouver(id);
    }
}
