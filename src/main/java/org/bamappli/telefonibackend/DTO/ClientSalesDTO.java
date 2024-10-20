package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSalesDTO {
    private String nom;
    private String email;
    private Long nombreDeVentes;
}
