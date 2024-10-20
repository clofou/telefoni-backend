package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCreation = new Date();

    private int note; // Valeur de la note (1 à 5 par exemple)

    @ManyToOne
    private Utilisateur evaluateur; // L'utilisateur qui évalue

    @ManyToOne
    private Utilisateur evalue; // L'utilisateur qui est évalué

    @OneToOne
    private Annonce annonce; // L'annonce liée à l'avis (optionnel)


    @OneToOne
    private Reparation reparation; // La réparation liée à l'avis (optionnel)

    @Column(length = 1024)
    private String commentaire; // Commentaire facultatif
}
