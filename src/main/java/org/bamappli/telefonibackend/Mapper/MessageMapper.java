package org.bamappli.telefonibackend.Mapper;


import org.bamappli.telefonibackend.DTO.MessageDTO;
import org.bamappli.telefonibackend.Entity.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MessageMapper implements Function<Message, MessageDTO> {


    @Override
    public MessageDTO apply(Message message) {
        return new MessageDTO(message.getId(), message.getMessage(), message.getMessageType().toString(), message.getUtilisateur().getNom());
    }
}
