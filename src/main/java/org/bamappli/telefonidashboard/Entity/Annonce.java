package org.bamappli.telefonidashboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonidashboard.Enum.AnnonceStatut;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AnnonceStatut statut;

    @ManyToOne
    private Telephone phone;

    @ManyToOne
    private Utilisateur utilisateur;

}
