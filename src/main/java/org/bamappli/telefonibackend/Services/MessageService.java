package org.bamappli.telefonibackend.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.MessageRequestDTO;
import org.bamappli.telefonibackend.Entity.Message;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Enum.AnnonceStatut;
import org.bamappli.telefonibackend.Enum.MessageType;
import org.bamappli.telefonibackend.Repository.MessageRepo;
import org.bamappli.telefonibackend.Utils.FileOperation;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MessageService implements CrudService<Long, Message>{

    private final MessageRepo messageRepo;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public Message creer(Message message) {
        Utilisateur utilisateur = userService.getCurrentUser();
        message.setUtilisateur(utilisateur);

        Message savedMessage = messageRepo.save(message);

        // Get the receiver's FCM token
        Utilisateur receiver = messageRepo.findReceiver(savedMessage.getId());

        if (receiver != null && receiver.getFcmToken() != null) {
            // Send the notification
            notificationService.sendPushNotification(
                    receiver.getFcmToken(),
                    "Nouveau Message",
                    "Vous avez reçu un message de " + message.getUtilisateur().getNom()
            );
        }

        return savedMessage;

    }

    @Override
    public Message modifer(Long id, Message message) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Message> message1 = messageRepo.findById(id);

        if (message1.isPresent() && Objects.equals(user.getId(), message1.get().getUtilisateur().getId()) && message1.get().getDiscussion().getAnnonce().getStatut() == AnnonceStatut.EN_VENTE && message1.get().getMessageType()==MessageType.TEXT){
            Message messageExist = message1.get();
            if (message.getMessage() != null) messageExist.setMessage(message.getMessage());
            messageExist.setTimestamp(new Date());
            return messageRepo.save(messageExist);
        }else{
            throw new IllegalArgumentException("Le message n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Message> trouver(Long id) {
        return messageRepo.findById(id);
    }

    @Override
    public List<Message> recuperer() {
        return messageRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Optional<Message> message = messageRepo.findById(id);
        if (message.isPresent()){
            if (message.get().getDiscussion().getAnnonce().getStatut() == AnnonceStatut.EN_VENTE){
                messageRepo.deleteById(id);
            }
        }
    }

    public Object recupererMessage(Long id) throws MalformedURLException {
        // Récupérer le message à partir de l'id
        Optional<Message> optionalMessage = messageRepo.findById(id);

        // Si le message n'existe pas, lever une exception spécifique
        if (optionalMessage.isEmpty()) {
            throw new EntityNotFoundException("Le message avec l'id " + id + " n'existe pas.");
        }

        Message message = optionalMessage.get();

        // Si le message est de type PHOTOS ou SONG, télécharger le fichier correspondant
        if (message.getMessageType() == MessageType.PHOTOS || message.getMessageType() == MessageType.SONG) {
            return FileOperation.downloadFile(message.getMessage());
        } else {
            // Sinon, retourner l'objet Message directement (pour les messages TEXT)
            return message;
        }
    }


    public Message creerMessage(MessageRequestDTO dto) throws IOException {
        Message message = new Message();
        message.setDiscussion(dto.getDiscussion());

        // Si un fichier est présent dans la requête
        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            String contentType = dto.getFile().getContentType();
            System.out.println(contentType);

            // Vérification du type de fichier pour déterminer le MessageType
            if (contentType != null) {
                if (contentType.startsWith("image/")) {
                    // Si le fichier est une image, définir MessageType comme PHOTOS
                    message.setMessageType(MessageType.PHOTOS);
                    message.setMessage(FileOperation.uploadFile(dto.getFile(), "uploads/images/telephones/"));
                } else if (contentType.startsWith("audio/")) {
                    // Si le fichier est un audio, définir MessageType comme SONG
                    message.setMessageType(MessageType.SONG);
                    message.setMessage(FileOperation.uploadFile(dto.getFile(), "uploads/audio/"));
                } else {
                    throw new IllegalArgumentException("Type de fichier non supporté : " + contentType);
                }
            }
        } else if (dto.getText() != null && !dto.getText().isEmpty()) {
            // Si le fichier n'est pas présent mais qu'il y a du texte, créer un message texte
            message.setMessageType(MessageType.TEXT);
            message.setMessage(dto.getText());
        } else {
            throw new IllegalArgumentException("Aucun fichier ou texte fourni.");
        }

        // Récupérer l'utilisateur actuel
        Utilisateur utilisateur = userService.getCurrentUser();
        message.setUtilisateur(utilisateur);

        // Sauvegarder et retourner le message
        return messageRepo.save(message);
    }

    public void supprimerMessage(Long id) throws IOException {
        // Récupérer le message à partir de l'id
        Optional<Message> optionalMessage = messageRepo.findById(id);

        // Vérifier si le message existe
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            if (message.getDiscussion().getAnnonce().getStatut() == AnnonceStatut.EN_VENTE){
                // Si le message est de type PHOTOS ou SONG, supprimer le fichier associé
                if (message.getMessageType() == MessageType.PHOTOS || message.getMessageType() == MessageType.SONG) {
                    try {
                        FileOperation.deleteFile(message.getMessage()); // Supposer que cette méthode existe
                    } catch (IOException e) {
                        throw new RuntimeException("Erreur lors de la suppression du fichier : " + e.getMessage(), e);
                    }
                }

                // Supprimer l'enregistrement du message de la base de données
                messageRepo.deleteById(id);
            }

        } else {
            throw new EntityNotFoundException("Le message avec l'id " + id + " n'existe pas.");
        }
    }
}
