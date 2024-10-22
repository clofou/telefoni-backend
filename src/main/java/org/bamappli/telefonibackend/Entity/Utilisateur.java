package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.Grade;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private String numeroDeTelephone;
    private String adresse;
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.BASIC;

    @ManyToOne
    private Role role;

    @OneToOne
    private Wallet compte;


}
