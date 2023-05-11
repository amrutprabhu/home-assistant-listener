import WebSocket from 'ws';

export default function handler(req, res) {
  const ws = new WebSocket('ws://localhost:3000');

  ws.on('open', function open() {
    console.log('WebSocket Client Connected');
  });

  ws.on('message', function incoming(data) {
    console.log(data);
    res.json(JSON.parse(data));
  });

  ws.on('close', function close() {
    console.log('WebSocket Client Disconnected');
  });
}
