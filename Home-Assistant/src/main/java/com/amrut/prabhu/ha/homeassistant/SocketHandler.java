package com.amrut.prabhu.ha.homeassistant;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import static com.amrut.prabhu.ha.homeassistant.HAMessageConstants.AUTH_MESSSAGE;
import static com.amrut.prabhu.ha.homeassistant.HAMessageConstants.SUBSCRIBE_MESSAGE;
import static java.lang.String.format;

@Component
public class SocketHandler implements WebSocketHandler {

    static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    private ObjectMapper objectMapper;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Value("${homeassistant.token}")
    private String token;

    public SocketHandler() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Connection established, do something
        logger.info("Connection Established");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Handle incoming message
        DocumentContext json = JsonPath.parse(message.getPayload().toString());
        MessageType type = MessageType.valueOf(json.read("$.type", String.class));

        switch (type) {
            case auth_required: {
                session.sendMessage(new TextMessage(format(AUTH_MESSSAGE, token).getBytes()));
                break;
            }
            case auth_ok: {
                session.sendMessage(new TextMessage(SUBSCRIBE_MESSAGE.getBytes()));
                break;
            }
            case result: {
                if (json.read("$.success", boolean.class)) {
                    logger.info("Websocket connection setup successfully");
                }
                break;
            }
            case event: {
                try {
                    logger.info(json.read("$.event.data.entity_id").toString());

                    simpMessagingTemplate.convertAndSend("/all", message.getPayload());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.error("Event Body: " + json.jsonString());
                }
                break;
            }
            default:
                logger.warn(json.jsonString());

        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Handle transport error
        logger.info("Transport Error" + exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // Connection closed, do something
        logger.info("Connection Status: " + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}