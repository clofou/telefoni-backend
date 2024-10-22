package org.bamappli.telefonibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private String message;
    private String messageType;
    private String utilisateur;
    // getters and setters
}
