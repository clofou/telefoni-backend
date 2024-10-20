package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModeleVenduDTO {
    private String brandNom;
    private String modeleNom;
    private double pourcentageVente;

}

