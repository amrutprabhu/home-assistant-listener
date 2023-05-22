package com.amrut.prabhu.ha;

import com.amrut.prabhu.ha.homeassistant.SocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class HAConfig {

    @Bean
    public WebSocketClient webSocketClient(final SocketHandler socketHandler, HAConfigProperties haConfigProperties) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(socketHandler, haConfigProperties.getUrl());
        return client;
    }
}
