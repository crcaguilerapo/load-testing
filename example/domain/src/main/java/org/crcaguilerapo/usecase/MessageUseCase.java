package org.crcaguilerapo.usecase;

import org.crcaguilerapo.dtos.Message;
import org.crcaguilerapo.port.in.IMessagePort;

import java.util.ArrayList;
import java.util.List;

public class MessageUseCase {

    private final IMessagePort messageRepository;

    public MessageUseCase(IMessagePort messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages(List<Long> ids) {
        var response = new ArrayList<Message>();
        for (Long id : ids) {
            response.add(messageRepository.getMessage(id).get());
        }
        return response;
    }

    public void saveMessage(Message message) {
        messageRepository.saveMessage(message);
    }
}
