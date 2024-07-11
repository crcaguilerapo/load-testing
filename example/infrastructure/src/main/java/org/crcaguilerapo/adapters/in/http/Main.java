package org.crcaguilerapo.adapters.in.http;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;

public class Main {
    static Server newServer(int port) {
        ServerBuilder sb = Server.builder();
        return sb
                .http(port)
                .annotatedService(new MessageController())
                .build();
    }
    public static void main(String[] args) {
        Server server = newServer(8080);
        server.closeOnJvmShutdown();
        server.start().join();
    }
}