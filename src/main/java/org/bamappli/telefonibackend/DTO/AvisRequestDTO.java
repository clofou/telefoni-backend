package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvisRequestDTO {
    private Long evalueId;
    private Long annonceId;
    private Long reparationId;
    private int note;
    private String commentaire;
}


