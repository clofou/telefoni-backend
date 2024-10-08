package org.bamappli.telefonibackend.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageRequestDTO {
    private MultipartFile file;
    private String text;
}
