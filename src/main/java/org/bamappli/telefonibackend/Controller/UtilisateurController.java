package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.AuthentificationDTO;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Services.ClientService;
import org.bamappli.telefonibackend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class UtilisateurController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientService clientService;

    @PostMapping(path = "connexion")
    public Map<String, String> seConnecter(@RequestBody AuthentificationDTO auth) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getMotDePasse())
        );
        if (authentication.isAuthenticated()){
            return jwtService.generate(auth.getEmail());
        }
        return null;
    }

    @PostMapping(path = "inscription")
    public Client inscription(@RequestBody AuthentificationDTO dto){
        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setMotDePasse(dto.getMotDePasse());
        return clientService.creer(client);
    }

}
