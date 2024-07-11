package org.crcaguilerapo.adapters.in.http;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Post;
import org.crcaguilerapo.adapters.out.memory.ListRepository;
import org.crcaguilerapo.dtos.Message;
import org.crcaguilerapo.port.in.IMessagePort;
import org.crcaguilerapo.usecase.MessageUseCase;

import java.util.List;


public final class MessageController {

  private final IMessagePort messagePort = new ListRepository();
  private final MessageUseCase messageUseCase = new MessageUseCase(messagePort);

  @Get("/messages")
  public HttpResponse getMessages() {
    var response = messageUseCase.getMessages(List.of(1L, 2L));
    return HttpResponse.ofJson(
            HttpStatus.OK,
            response
    );
  }

  @Post("/message")
  public HttpResponse saveMessage(Message message) {
    messageUseCase.saveMessage(message);
    return HttpResponse.of(
            HttpStatus.OK
    );
  }
}