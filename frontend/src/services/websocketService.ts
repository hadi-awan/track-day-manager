// src/services/websocket.service.ts
class WebsocketService {
    private socket: WebSocket | null = null;
    private stompClient: any = null;

    connect(sessionId: string, onLocationUpdate: (data: any) => void) {
        const SockJS = require('sockjs-client');
        const Stomp = require('stompjs');

        this.socket = new SockJS('http://localhost:8080/ws');
        this.stompClient = Stomp.over(this.socket);

        this.stompClient.connect({}, () => {
            this.stompClient.subscribe('/topic/location', (response: any) => {
                const data = JSON.parse(response.body);
                onLocationUpdate(data);
            });
        });
    }

    sendLocation(locationData: any) {
        if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/app/track", {}, JSON.stringify(locationData));
        }
    }

    disconnect() {
        if (this.stompClient) {
            this.stompClient.disconnect();
        }
    }
}

export const webSocketService = new WebsocketService();