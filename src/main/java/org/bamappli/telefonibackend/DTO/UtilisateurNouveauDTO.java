package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtilisateurNouveauDTO {
    private String nom;
    private String email;
    private String adresse;
    private String numeroDeTelephone;

}

