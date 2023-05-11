import React, { useEffect, useState } from "react";
import Head from "next/head";
import _ from "lodash";
import Layout from "@/components/Layout";
import { connectToStompClient } from "@/utils/client";

let stompClient;

export async function getServerSideProps(context) {
  const WEBSOCKET_URL = process.env.WEBSOCKET_URL;
  return {
    props: {
      WEBSOCKET_URL,
    },
  };
}
export default function Home({ WEBSOCKET_URL }) {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    if (!stompClient) {
      const subscriptionHandler = (json) => {
        console.info("received ->" + JSON.stringify(json));
        setEvents((prevEvents) => [...prevEvents, json]);
      };

      stompClient = connectToStompClient(
        subscriptionHandler,
        "/all",
        WEBSOCKET_URL
      );
    }

    return () => {
      if (stompClient && stompClient.connected) {
        console.log("Disconnecting Client");
        stompClient.disconnect();
        stompClient = undefined;
      }
    };
  }, [WEBSOCKET_URL]);
  return (
    <Layout>
      <div className="flex flex-col justify-center h-full mx-auto bg-gray-700">
        <Head>
          <title>Amr</title>
          <link rel="icon" href="/favicon.ico" />
        </Head>

        <div className="text-center w-auto">
          <h2 className="text-3xl font-extrabold sm:text-4xl">WebSocket 1</h2>
        </div>
        {/* <main className="mt-2"> */}
        <div className="px-6 lg:px-8 w-full">
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
        {/* </main> */}
      </div>
    </Layout>
  );
}
