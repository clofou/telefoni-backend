package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoutiqueDTO {
    private int id;
    private String nom;
    private String popularite;
    private String isLocked;
    private double ventes;
}
