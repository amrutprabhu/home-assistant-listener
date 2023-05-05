package com.amrut.prabhu.HomeAssistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@SpringBootApplication
public class HomeAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeAssistantApplication.class, args);
    }

    @Bean
    public WebSocketClient webSocketClient(final SocketHandler socketHandler) {
        WebSocketClient client = new StandardWebSocketClient();
        String url = "ws://192.168.0.158:8123/api/websocket";
        client.execute(socketHandler, url);
        return client;
    }
}
