package org.bamappli.telefonidashboard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telefonidashboard.Enum.LitigeStatut;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Litige {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2048)
    private String description;
    @Enumerated(EnumType.STRING)
    private LitigeStatut statut;

    @ManyToOne
    private Transaction transaction;
}
