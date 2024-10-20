package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bamappli.telefonibackend.Enum.CommandeStatut;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeStatutDTO {
    private CommandeStatut acheteur;
    private CommandeStatut vendeur;
    private CommandeStatut controller;
}
