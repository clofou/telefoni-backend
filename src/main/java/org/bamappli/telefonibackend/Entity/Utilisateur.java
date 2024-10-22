package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.Grade;

import java.util.Date;

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
    private String numeroDeTelephone;
    private String adresse;
    private int points;
    private String photoUrl;
    private Date dateCreation = new Date();
    private String fcmToken;
    private String verificationCode; // Code de vérification
    private boolean isActive = false;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.BASIC;

    @ManyToOne
    private Role role;

    @OneToOne
    private Wallet compte;

    // Méthode pour mettre à jour le grade en fonction des points
    public void reevaluerGrade() {
        if (points >= 1500) {
            this.grade = Grade.VIP;
        } else if (points >= 800) {
            this.grade = Grade.PRO_PLUS;
        } else if (points >= 300) {
            this.grade = Grade.PRO;
        } else {
            this.grade = Grade.BASIC; // Assuming BASIC is a lower rank
        }
    }


}
