package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "client")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ClientController {

    private ClientService clientService;

    @GetMapping
    public List<Client> tout(){
        return clientService.recuperer();
    }

    @PostMapping
    public Client creer(@RequestBody Client client){
        return clientService.creer(client);
    }
}
