package org.bamappli.telefonidashboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonidashboard.Enum.TelephoneType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Telephone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TelephoneType type;
    private String titre;
    private int memoire;
    @Column(length = 2048)
    private String description;
    private double prix;

    @OneToMany
    private List<Tags> tagsList;

    @OneToMany
    private List<Photos> photosList;

    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Modele modele;
    @ManyToOne
    Utilisateur utilisateur;
}
