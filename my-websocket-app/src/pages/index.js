import React, { useEffect, useState } from "react";
import Head from "next/head";
import _ from "lodash";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

var socket;
export async function getServerSideProps(context) {
  const WEBSOCKET_URL_1 = process.env.WEBSOCKET_URL;
  return {
    props: {
      WEBSOCKET_URL_1,
    },
  };
}
export default function Home({ WEBSOCKET_URL_1 }) {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    if (socket === undefined) {
      console.log("here" + WEBSOCKET_URL_1);
      socket = new SockJS(WEBSOCKET_URL_1);
      const stompClient = Stomp.over(socket);
      stompClient.connect(
        {},
        function (frame) {
          console.log("-----------------------------------");
          console.log("STOMP client connected");
          console.log("-----------------------------------");
          stompClient.subscribe("/all", function (message) {
            const data = JSON.parse(message.body);
            console.info("received ->" + JSON.stringify(data));
            setEvents((prevEvents) => [...prevEvents, data]);
          });
        },
        function (error) {
          console.error("WebSocket error:", error);
        }
      );

      return () => {
        if (stompClient.connected) {
          console.info("-----" + stompClient);
          stompClient.disconnect();
        }
      };
    }
  }, [WEBSOCKET_URL_1]);
  return (
    <div className="min-h-screen py-6 flex flex-col justify-center sm:py-12 bg-gray-700">
      <Head>
        <title>WebSocket</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className="mt-2">
        <div className="px-6 lg:px-8 ">
          <div className="text-center">
            <h2 className="text-3xl font-extrabold text-gray-400 sm:text-4xl">
              WebSocket
            </h2>
          </div>
          <div className="mt-12">
            <div className="grid grid-cols-1 gap-5 md:grid-cols-2 ">
              {_.map(events, (event, index) => (
                <div
                  key={index}
                  className="overflow-x-scroll shadow-md shadow-gray-900 bg-gray-800 rounded-lg divide-y divide-gray-200"
                >
                  <div className="px-4 py-5 sm:px-6">
                    <h3 className="text-lg leading-6 font-medium">
                      Event #{index + 1}
                    </h3>
                  </div>
                  <div className="px-4 py-5 sm:p-6 font-bold text-gray-">
                    <pre>{JSON.stringify(event, null, 2)}</pre>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </main>

      <footer className="mt-auto bg-gray-200">
        <div className="max-w-3xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
          <p className="text-center text-base font-medium text-gray-600">
            &copy; 2023 RefactorFirst. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  );
}
