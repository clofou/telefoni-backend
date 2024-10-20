package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenteTypeDTO {
    private int mois; // 1 = Janvier, 2 = Février, etc.
    private long ventesOccasion;
    private long ventesNeuf;

}
