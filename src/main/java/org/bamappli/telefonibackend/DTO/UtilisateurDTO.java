package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtilisateurDTO {
    private String nom;
    private String email;
    private String adresse;
    private String role;
    private String photoUrl;
    private String telephone;

}
