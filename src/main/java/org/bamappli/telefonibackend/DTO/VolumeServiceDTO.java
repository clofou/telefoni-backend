package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolumeServiceDTO {
    private Date date;          // Date du jour
    private Long volume;        // Nombre total d'actions (avis, transactions, annonces validées)
    private Long services;      // Nombre total de services (réparations, commandes livrées)
}

