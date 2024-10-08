package org.bamappli.telefonibackend.Utils;

import org.bamappli.telefonibackend.Entity.Photos;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileOperation {

    // Méthode pour supprimer physiquement un fichier
    public static void deleteFile(String lienMedia) throws IOException {
        Path filePath = Paths.get(lienMedia).normalize();
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new RuntimeException("Le fichier n'existe pas : " + lienMedia);
        }
    }

    // Méthode pour uploader une photo
    public static String uploadFile(MultipartFile file, String uploadDir) throws IOException {
        // Créer le répertoire de stockage si nécessaire
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Enregistrer le fichier avec son nom original
        String fileName = file.getOriginalFilename();
        assert fileName != null;

        Path filePath = uploadPath.resolve(fileName);

        // Copier le fichier au bon emplacement
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner le chemin du fichier
        return filePath.toString();
    }

    // Méthode pour télécharger une image à partir de l'entité Photos via lienMedias
    public static Resource downloadFile(String lienMedia) throws MalformedURLException {

        // Vérifier que le lienMedia n'est pas vide et localiser le fichier
        Path filePath = Paths.get(lienMedia).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Le fichier n'a pas pu être trouvé ou lu : " + lienMedia);
        }
    }


}
