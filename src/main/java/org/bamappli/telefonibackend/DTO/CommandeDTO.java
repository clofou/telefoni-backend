package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Enum.CommandeStatut;

@AllArgsConstructor
@Getter
@Setter
public class CommandeDTO {
    private Long id;
    private String nom;
    private Double prix;
    private String acheteur;
    private CommandeStatut statut;

}