package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Boutique extends Utilisateur {

    private boolean accountLocked;

    @ManyToOne
    private Admin admin;
}
