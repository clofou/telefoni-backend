package org.bamappli.telefonidashboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    Date timestamp;

    @ManyToOne
    Utilisateur utilisateur;

    @ManyToOne
    private Discussion discussion;
}
