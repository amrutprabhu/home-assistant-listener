import SockJS from "sockjs-client";
import Stomp from "stompjs";

export function connectToStompClient(subscriptionHandler, path, url) {
  const socket = new SockJS(url);
  const stompClient = Stomp.over(socket);

  stompClient.connect(
    {},
    () => {
      stompClient.subscribe(path, (message) => {
        console.log("->>>>" + message.body);
        const messageBody = JSON.parse(message.body);
        subscriptionHandler(messageBody);
      });
    },
    function (error) {
      console.error("WebSocket error:", error);
    }
  );
  return stompClient;
}
