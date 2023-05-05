package com.amrut.prabhu.HomeAssistant;

import org.springframework.stereotype.Component;

@Component
public class HAMessageConstants {


    public static String AUTH_MESSSAGE = """
            {
              "type": "auth",
              "access_token": "%s"
            }
            """;

    public static String SUBSCRIBE_MESSAGE = """
            {
              "id": 1,
              "type": "subscribe_events"
            }
            """;


}
