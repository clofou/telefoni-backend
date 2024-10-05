package org.bamappli.telefonidashboard.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Controller extends Utilisateur{
    private String adresse;
    private String docOff1;
    private String docOff2;
    private String docOff3;
}
