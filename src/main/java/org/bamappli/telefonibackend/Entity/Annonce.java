package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;

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
    private AnnonceStatut statut = AnnonceStatut.EN_ATTENTE_VALIDATION;
    // Ajout d'un attribut pour spécifier la garantie (nombre de jours)
    private Integer dateDeGarantit = 2;
    // Nouvel attribut pour indiquer si l'annonce a été validée par un contrôleur
    private Boolean valideeParController = false;

    @ManyToOne
    private Telephone phone;

    @ManyToOne
    private Utilisateur utilisateur;

}
