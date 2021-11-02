package com.universaldevices.udwebsocketexample.ui;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainWebSocketListener extends WebSocketListener {

    private final String LOG_TAG = MainWebSocketListener.class.getSimpleName();
    private final MainActivityViewModel viewModel;

    private List<String> messages = new ArrayList<>();

    public MainWebSocketListener(MainActivityViewModel viewModel){
        this.viewModel = viewModel;
    }



    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        Log.v(LOG_TAG, "OnOpen");
        super.onOpen(webSocket, response);
        messages = new ArrayList<>();
        viewModel.setConnected(true);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        Log.v(LOG_TAG, "Receiving String: " + text);
        super.onMessage(webSocket, text);
        messages.add(text);
        viewModel.setMessage(messages);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        Log.v(LOG_TAG, "OnClose");
        super.onClosed(webSocket, code, reason);
        viewModel.setConnected(false);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        Log.v(LOG_TAG, "OnFailure: " + response + ", " + t);
        super.onFailure(webSocket, t, response);
        viewModel.setConnected(false);
        viewModel.setError(new Error("" + response + ", " + t));
    }



}
