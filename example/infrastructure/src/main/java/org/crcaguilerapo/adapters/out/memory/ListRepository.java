package org.crcaguilerapo.adapters.out.memory;

import org.crcaguilerapo.dtos.Message;
import org.crcaguilerapo.port.in.IMessagePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListRepository implements IMessagePort {

    List<Message> database = new ArrayList<>();

    public ListRepository() {
        database.add(new Message(1, "test1@test.com", "test2@test.com", "Hello world"));
        database.add(new Message(2, "test2@test.com", "test1@test.com", "Hello world"));
    }

    @Override
    public Optional<Message> getMessage(long id) {
        return database
                .stream()
                .filter(v -> v.id() == id)
                .findFirst();
    }

    @Override
    public void saveMessage(Message message) {
        database.add(message);
    }
}
