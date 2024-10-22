package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.AuthentificationDTO;
import org.bamappli.telefonibackend.DTO.InscriptionDTO;
import org.bamappli.telefonibackend.DTO.UtilisateurDTO;
import org.bamappli.telefonibackend.DTO.VerifyCodeDTO;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Mapper.UserDTOMapper;
import org.bamappli.telefonibackend.Repository.UtilisateurRepo;
import org.bamappli.telefonibackend.Services.ClientService;
import org.bamappli.telefonibackend.Services.EmailService;
import org.bamappli.telefonibackend.Utils.UserService;
import org.bamappli.telefonibackend.Utils.UtilService;
import org.bamappli.telefonibackend.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UtilisateurController {

    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private ClientService clientService;
    private UserService userService;
    private UserDTOMapper userDTOMapper;
    private UtilisateurRepo utilisateurRepo;
    private EmailService emailService;

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
    public Client inscription(@RequestBody InscriptionDTO dto){
        // Génération du code de vérification
        String verificationCode = UtilService.generateVerificationCode();

        Client client = new Client();
        client.setNom(dto.getNom());
        client.setFcmToken(dto.getFcmToken());
        client.setVerificationCode(verificationCode);
        client.setActive(false); // Le compte est inactif jusqu'à validation
        client.setEmail(dto.getEmail());
        client.setMotDePasse(dto.getMotDePasse());
        // Envoi de l'email avec le code de vérification
        emailService.sendVerificationEmail(dto.getEmail(), verificationCode);
        return clientService.creer(client);
    }

    @GetMapping(path = "user/current")
    public UtilisateurDTO getCurrentUserInfo(){
        return userDTOMapper.apply(userService.getCurrentUser());
    }

    @PostMapping("/update-token")
    public ResponseEntity<String> updateFcmToken(@RequestParam Long userId,
                                                 @RequestParam String newFcmToken) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepo.findById(userId);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            utilisateur.setFcmToken(newFcmToken);
            utilisateurRepo.save(utilisateur);
            return ResponseEntity.ok("FCM token updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping(path = "verify")
    public ResponseEntity<String> verifyAccount(@RequestBody VerifyCodeDTO verifyCodeDTO) {
        Utilisateur utilisateurOpt = utilisateurRepo.findByEmail(verifyCodeDTO.getEmail());

        if (utilisateurOpt != null) {

            // Vérification du code
            if (utilisateurOpt.getVerificationCode().equals(verifyCodeDTO.getCode())) {
                utilisateurOpt.setActive(true); // Activer le compte
                utilisateurOpt.setVerificationCode(null); // Supprimer le code de vérification après succès
                utilisateurRepo.save(utilisateurOpt);
                return ResponseEntity.ok("Votre compte a été activé avec succès.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code de vérification incorrect.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }
    }

}
