package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int note; // Note sur 5
    private String commentaire;

    @ManyToOne
    private Client client; // L'auteur de l'avis

    @ManyToOne
    private Transaction produit; // Si l'avis concerne un produit

    @ManyToOne
    private Reparation service; // Si l'avis concerne un service (ex: réparateur)

    private LocalDateTime dateAvis;
}