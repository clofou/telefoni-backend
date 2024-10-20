package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoutiqueSalesDTO {
    private String nom;
    private String email;
    private Long nombreDeVentes;
}

