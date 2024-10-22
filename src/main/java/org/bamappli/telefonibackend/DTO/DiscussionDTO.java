package org.bamappli.telefonibackend.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionDTO {
    private Long id;
    private String vendeur;
    private String acheteur;
    private String annonce;
    // getters and setters
}

