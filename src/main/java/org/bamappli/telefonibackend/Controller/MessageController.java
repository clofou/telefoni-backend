package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.MessageRequestDTO;
import org.bamappli.telefonibackend.Entity.Commande;
import org.bamappli.telefonibackend.Entity.Message;
import org.bamappli.telefonibackend.Services.CommandeService;
import org.bamappli.telefonibackend.Services.MessageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public Message creer(@RequestBody MessageRequestDTO message) throws IOException {
        return messageService.creerMessage(message);
    }

    @GetMapping
    public List<Message> liste(){
        return messageService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Object un(@PathVariable Long id) throws MalformedURLException {
        return messageService.recupererMessage(id);
    }

    @PatchMapping(path = "/{id}")
    public Message modifier(@PathVariable Long id, @RequestBody Message message){
        return messageService.modifer(id,message);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id) throws IOException {
        messageService.supprimerMessage(id);
    }
}
