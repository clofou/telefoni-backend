package org.bamappli.telefonidashboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonidashboard.Enum.Grade;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private String telephone;
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne
    private Role role;

    @OneToOne
    private Wallet compte;
}
