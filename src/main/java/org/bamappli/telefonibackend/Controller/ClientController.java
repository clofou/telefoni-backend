package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Services.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;

    @PostMapping
    public Client creer(@RequestBody Client client){
        return clientService.creer(client);
    }
}
