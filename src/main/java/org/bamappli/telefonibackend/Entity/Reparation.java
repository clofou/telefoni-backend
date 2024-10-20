package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.ReparationStatut;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reparation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descriptionProbleme;
    @Enumerated(EnumType.STRING)
    private ReparationStatut statut;
    private Integer nombreDeTelephoneReparer;
    private Double nombreTotalDeTelephone;
    private Date dateReparation = new Date();

    @OneToMany
    private List<Telephone> telephoneList;

    @ManyToOne
    private Reparateur reparateur;
}
