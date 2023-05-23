package com.amrut.prabhu.ha;

import com.amrut.prabhu.ha.homeassistant.SocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.ExecutionException;

@Configuration
public class HAConfig {

    @Bean
    public WebSocketSession webSocketSession(final SocketHandler socketHandler, HAConfigProperties haConfigProperties) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        return client.execute(socketHandler, haConfigProperties.getUrl())
                .get();
    }
}
