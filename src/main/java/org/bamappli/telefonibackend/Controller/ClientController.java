package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.ClientSalesDTO;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Services.ClientService;
import org.bamappli.telefonibackend.Services.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "client")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ClientController {

    private ClientService clientService;
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Client> tout(){
        return clientService.recuperer();
    }

    @GetMapping(path = "new")
    public Map<String, Object> toutNombre(){
        return utilisateurService.getNouveauxUtilisateursAvecPourcentage();
    }

    @GetMapping(path = "order")
    public List<ClientSalesDTO> toutOrdre(){
        return clientService.getClientsOrderedBySales();
    }

    @PatchMapping(path = "{id}")
    public Client modifier(@PathVariable Long id, @RequestBody Client client){
        return clientService.modifer(id, client);
    }

    @PostMapping
    public Client creer(@RequestBody Client client){
        return clientService.creer(client);
    }
}
