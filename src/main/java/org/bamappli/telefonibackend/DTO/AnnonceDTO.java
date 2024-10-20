package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.bamappli.telefonibackend.Enum.TelephoneType;

@Data
@AllArgsConstructor
public class AnnonceDTO {
    private Long id;
    private String nom;
    private String prix;
    private TelephoneType etat;
    private String vendeur;
    private AnnonceStatut statut;
}
