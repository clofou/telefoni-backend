package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.VenteTypeDTO;
import org.bamappli.telefonibackend.Services.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "statistiques")
@AllArgsConstructor
public class StatistiquesController {

    private GraphService statistiquesService;

    @GetMapping("/ventes-occasion-vs-neuf")
    public ResponseEntity<List<VenteTypeDTO>> getVentesOccasionVsNeuf() {
        List<VenteTypeDTO> ventes = statistiquesService.getVentesOccasionVsNeuf();
        return ResponseEntity.ok(ventes);
    }
}
