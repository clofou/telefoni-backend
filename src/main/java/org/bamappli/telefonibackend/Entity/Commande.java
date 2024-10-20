package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.CommandeStatut;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CommandeStatut statutAcheteur = CommandeStatut.EN_ATTENTE; // Statut côté acheteur

    @Enumerated(EnumType.STRING)
    private CommandeStatut statutVendeur = CommandeStatut.EN_ATTENTE;  // Statut côté vendeur

    @Enumerated(EnumType.STRING)
    private CommandeStatut statutController = CommandeStatut.EN_ATTENTE; // Statut côté controller

    private Date dateLivraison;

    @ManyToOne
    private Transaction transaction;

    @ManyToOne
    private Controller controller;
}
