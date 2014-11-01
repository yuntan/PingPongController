package com.example.yuntan.pingpongcontroller;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class GameController extends WebSocketClient {

    private GameControllerListenerInterface _listener = null;

    public GameController(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        _listener.Message("onOpen");
    }

    @Override
    public void onMessage(String s) {
        _listener.Message("onMessage\nMessage: " + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        _listener.Message(String.format("onClose\nCode: %d, Reason: %s", i, s));
    }

    @Override
    public void onError(Exception e) {
        _listener.Message("onError\nError: " + e.getMessage());
    }

    public void setListener(GameControllerListenerInterface _listener) {
        this._listener = _listener;
    }
}
