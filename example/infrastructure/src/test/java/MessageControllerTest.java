import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import org.crcaguilerapo.adapters.in.http.MessageController;
import org.crcaguilerapo.dtos.Message;
import org.junit.jupiter.api.*;
import com.linecorp.armeria.server.Server;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.utility.MountableFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageControllerTest {
    static int port = 8080;
    static int proxyPort = 4010;
    static Server server;
    static GenericContainer<?> proxyContainer;

    static MessageController getMockController() {
        MessageController msgController = mock(MessageController.class);

        HttpResponse response1 = HttpResponse.ofJson(
                HttpStatus.OK,
                List.of(new Message(1, "a", "b", "hi"))
        );
        when(msgController.getMessages()).thenReturn(response1);

        HttpResponse response2 = HttpResponse.of(
                HttpStatus.OK
        );
        when(msgController.saveMessage(any())).thenReturn(response2);

        return msgController;
    }

    static void initAPI() {
        Server sb = Server.builder()
                .http(port)
                .annotatedService(getMockController())
                .build();
        sb.closeOnJvmShutdown();
        sb.start().join();
    }

    static void initContainer() {
        final String url = String.format("http://host.testcontainers.internal:%d/", port);

        proxyContainer = new GenericContainer<>("stoplight/prism:5.8.2")
                .withAccessToHost(true)
                .withCopyFileToContainer(
                        MountableFile.forClasspathResource("spec.yaml"),
                        "/"
                )
                .withExposedPorts(proxyPort)
                .withCommand("proxy /spec.yaml $URL".replace("$URL", url))
                .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Starting Prism.*"));;
        proxyContainer.start();
        Testcontainers.exposeHostPorts(port);
    }

    @BeforeAll
    public static void setup() {
        initAPI();
        initContainer();
    }

    @AfterAll
    public static void tearDown() {
        server.stop().join();
        proxyContainer.stop();
    }

    @Test
    public void verify() {
        System.out.println("");

    }

}
