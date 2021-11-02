package com.universaldevices.udwebsocketexample.repositories;

import android.app.Application;

import com.universaldevices.udwebsocketexample.objects.Credential;
import com.universaldevices.udwebsocketexample.objects.IsyWebSocket;

import okhttp3.OkHttpClient;
import okhttp3.WebSocketListener;

public class Repository {

    private final Application application;

    public Repository(Application application){
        this.application = application;
    }


    public void startWebSocket(Credential credential, WebSocketListener listener, IsyWebSocket.Completion completion){
        Runnable runnable = () -> {
            IsyWebSocket object = IsyWebSocket.getInstance(listener, credential);
            completion.onCompletion(object);
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


}
