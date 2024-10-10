package org.bamappli.telefonibackend.DTO;

import lombok.Data;
import org.bamappli.telefonibackend.Entity.Discussion;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageRequestDTO {
    private MultipartFile file;
    private String text;
    private Discussion discussion;
}
