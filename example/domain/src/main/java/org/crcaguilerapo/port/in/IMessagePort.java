package org.crcaguilerapo.port.in;

import org.crcaguilerapo.dtos.Message;

import java.util.Optional;

public interface IMessagePort {
    Optional<Message> getMessage(long id);
    void saveMessage(Message message);
}
