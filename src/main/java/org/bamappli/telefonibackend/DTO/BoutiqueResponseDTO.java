package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.Grade;

@AllArgsConstructor
@Getter
@Setter
public class BoutiqueResponseDTO {
    private Long id;
    private String nom;
    private String email;
    private String numeroDeTelephone;
    private Grade grade;
    private double soldeDuCompte;

}
