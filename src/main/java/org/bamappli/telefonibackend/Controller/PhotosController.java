package org.bamappli.telefonibackend.Controller;


import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Photos;
import org.bamappli.telefonibackend.Services.PhotosService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "photos")
@AllArgsConstructor
public class PhotosController {

    private final PhotosService photosService;


    @PostMapping("/upload")
    public Photos uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        return photosService.creerPhotos(file);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable Long id) {
        try {
            Resource resource = photosService.recupererPhotos(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Nouvelle méthode pour supprimer une photo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable Long id) {
        try {
            photosService.supprimer(id);
            return ResponseEntity.ok("Photo supprimée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression de la photo : " + e.getMessage());
        }
    }
}
