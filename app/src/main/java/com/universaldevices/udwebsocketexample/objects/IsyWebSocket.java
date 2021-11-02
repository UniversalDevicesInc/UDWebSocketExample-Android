package com.universaldevices.udwebsocketexample.objects;


import android.util.Log;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class IsyWebSocket {

    private final String LOG_TAG = IsyWebSocket.class.getSimpleName();

    public Error error;
    public WebSocket webSocket;
    public Credential credential;
    private OkHttpClient client;


    public interface Completion {
        void onCompletion(IsyWebSocket object);
    }

    private IsyWebSocket(Credential credential){
        client = new OkHttpClient();
        this.credential = credential;
    }


    public static IsyWebSocket getInstance(WebSocketListener listener, Credential credential){
        IsyWebSocket object = new IsyWebSocket(credential);
        object.setValues(listener);
        return object;
    }


    private void setValues(WebSocketListener listener){
        if (credential.userName == null || credential.userName.isEmpty()){
            error = new Error("Username is empty");
            return ;
        }

        if (credential.password == null || credential.password.isEmpty()){
            error = new Error("Password is empty");
            return ;
        }

        if (credential.ipAddress == null || credential.ipAddress.isEmpty()){
            error = new Error("Ip Address is empty");
            return ;
        }


        String credentials = Credentials.basic(credential.userName, credential.password);


        String url = "ws://";
        url = url + credential.ipAddress;
        if (credential.port != null){
            url = url + ":" + credential.port;
        }
        url = url + "/rest/subscribe";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credentials)
                .addHeader("Sec-WebSocket-Protocol", "ISYSUB")
                .addHeader("Sec-WebSocket-Version", "13")
                .addHeader("Origin", "com.universal-devices.websockets.isy")
                .build();
       webSocket = client.newWebSocket(request, listener);
    }


    public void close(){

        if (webSocket != null){
            boolean closeStatus = webSocket.close(1000, "Closed by client");
            Log.v(LOG_TAG, "Closed Gracefully: " + closeStatus);
        }

    }

}
