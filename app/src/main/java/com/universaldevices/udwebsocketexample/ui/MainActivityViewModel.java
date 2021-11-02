package com.universaldevices.udwebsocketexample.ui;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.universaldevices.udwebsocketexample.objects.Credential;
import com.universaldevices.udwebsocketexample.objects.IsyWebSocket;
import com.universaldevices.udwebsocketexample.repositories.Repository;

import java.util.List;

import okhttp3.WebSocketListener;

public class MainActivityViewModel extends AndroidViewModel {

    private final String LOG_TAG = MainActivityViewModel.class.getSimpleName();

    //Context
    private final Application application;

    //Repositories
    private final Repository repository;

    //WebSocketListener
    private final MainWebSocketListener socketListener;

    //Database Observations
    //none

    //Observed Objects
    private final MutableLiveData<Error> errorLiveData;
    private final MutableLiveData<Boolean> connectedLiveData;
    private final MutableLiveData<List<String>> messageLiveData;
    private final MutableLiveData<IsyWebSocket> webSocketLiveData;



    //Init
    public MainActivityViewModel(@NonNull Application application){
        super(application);
        //Context
        this.application = application;
        //Repositories
        this.repository = new Repository(application);
        //Web Socket Listener
        this.socketListener = new MainWebSocketListener(this);
        //Database Observations
        //none
        //observed objects
        this.errorLiveData = new MutableLiveData<>();
        this.connectedLiveData = new MutableLiveData<>();
        this.messageLiveData = new MutableLiveData<>();
        this.webSocketLiveData = new MutableLiveData<>();
    }



    ////////////////////////// Observers ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    public LiveData<Error> observeErrors() {return errorLiveData; }
    public LiveData<Boolean> observeConnected() {return connectedLiveData; }
    public LiveData<List<String>> observeMessages() {return messageLiveData; }
    public LiveData<IsyWebSocket> observeWebSocket() {return webSocketLiveData; }

    ///////////////////////// Setters //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    public void setError(Error error){
        if (application != null){
            Handler mainHandler = new Handler(application.getMainLooper());
            Runnable myRunnable = () -> {
                errorLiveData.setValue(error);
                //clear the error so it is not given to a new observer
                errorLiveData.setValue(null);
            };
            mainHandler.post(myRunnable);
        }
    }

    public void setConnected(Boolean object){
        if (application != null){
            Handler mainHandler = new Handler(application.getMainLooper());
            Runnable myRunnable = () -> {
                connectedLiveData.setValue(object);
                //Do not clear
            };
            mainHandler.post(myRunnable);
        }
    }

    public void setMessage(List<String> object){
        if (application != null){
            Handler mainHandler = new Handler(application.getMainLooper());
            Runnable myRunnable = () -> {
                messageLiveData.setValue(object);
                //Do not clear
            };
            mainHandler.post(myRunnable);
        }
    }

    public void setWebSocket(IsyWebSocket object){
        if (application != null){
            Handler mainHandler = new Handler(application.getMainLooper());
            Runnable myRunnable = () -> {
                webSocketLiveData.setValue(object);
                //Do not clear
            };
            mainHandler.post(myRunnable);
        }
    }

    ///////////////////////// Getters //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    public void connectWebSocket(Credential credential){
        repository.startWebSocket(credential, socketListener, object ->{
            if (object.error != null){
                setError(object.error);
            }else {
                setWebSocket(object);
            }
        });
    }



}
