package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.TransactionStatut;

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
    private Date dateDeTransaction = new Date();
    private Double montant;
    @Enumerated(EnumType.STRING)
    private TransactionStatut statut = TransactionStatut.NON_PAYER;


    @OneToOne
    private Annonce phone;

    @OneToOne
    @JoinColumn(name = "acheteur")
    private Client acheteur;
}

