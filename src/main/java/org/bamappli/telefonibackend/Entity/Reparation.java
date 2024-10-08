package org.bamappli.telefonibackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonibackend.Enum.ReparationStatut;

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
    private ReparationStatut statut;
    private Integer nombreDeTelephoneReparer;
    private Double nombreTotalDeTelephone;

    @OneToMany
    private List<Telephone> telephoneList;

    @ManyToOne
    private Reparateur reparateur;
}
