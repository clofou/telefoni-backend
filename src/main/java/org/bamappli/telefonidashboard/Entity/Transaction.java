package org.bamappli.telefonidashboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonidashboard.Enum.TransactionStatut;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDeTransaction;
    private double montant;
    private TransactionStatut statut;


    @OneToOne
    private Annonce phone;

    @OneToOne
    @JoinColumn(name = "acheteur")
    private Client acheteur;
}

