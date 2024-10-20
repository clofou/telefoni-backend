package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.AvisRequestDTO;
import org.bamappli.telefonibackend.Entity.Avis;
import org.bamappli.telefonibackend.Services.AvisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "avis")
@AllArgsConstructor
public class AvisController {

    private AvisService avisService;

    @PostMapping
    public ResponseEntity<Avis> creerAvis(@RequestBody AvisRequestDTO avisRequestDTO) {
        Avis avis = avisService.creerAvis(
                avisRequestDTO.getEvalueId(),
                avisRequestDTO.getAnnonceId(),
                avisRequestDTO.getReparationId(),
                avisRequestDTO.getNote(),
                avisRequestDTO.getCommentaire()
        );
        return ResponseEntity.ok(avis);
    }
}

