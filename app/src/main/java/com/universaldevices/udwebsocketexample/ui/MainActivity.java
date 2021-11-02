package com.universaldevices.udwebsocketexample.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.universaldevices.udwebsocketexample.R;
import com.universaldevices.udwebsocketexample.objects.Credential;
import com.universaldevices.udwebsocketexample.objects.IsyWebSocket;
import com.universaldevices.udwebsocketexample.objects.ShowMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    //Companion Classes
    private MainActivityViewModel viewModel;
    private MainActivityAdapter adapter;

    //Created in the class
    public boolean isConnected = false;
    public Credential credential;
    public List<String> messages = new ArrayList<>();
    public IsyWebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        credential = new Credential();
        adapter = new MainActivityAdapter(this);
        setObservers(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //this will call onPrepareOptionsMenu
        invalidateOptionsMenu();
    }

    ///////////////////// Observers /////////////////////////////
    ////////////////////////////////////////////////////////////

    private void setObservers(boolean add){
        if (add){
            addObservers();
        }else {
            removeObservers();
        }
    }

    private void addObservers(){
        if (viewModel != null){
            return;
        }

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.observeErrors().observe(this, errorObserver);
        viewModel.observeWebSocket().observe(this, webSocketObserver);
        viewModel.observeConnected().observe(this, webSocketConnectedObserver);
        viewModel.observeMessages().observe(this, messageObserver);

    }

    private void removeObservers(){
        if (viewModel == null){
            return;
        }

        if (webSocket != null) {
            webSocket.close();
        }

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.observeErrors().removeObserver(errorObserver);
        viewModel.observeWebSocket().removeObserver(webSocketObserver);
        viewModel.observeConnected().removeObserver(webSocketConnectedObserver);
        viewModel.observeMessages().removeObserver(messageObserver);

        viewModel = null;

    }

    ///////////////////// Top MENU /////////////////////////////
    ////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem start = menu.findItem(R.id.action_start);
        MenuItem stop = menu.findItem(R.id.action_stop);
        start.setVisible(!isConnected);
        stop.setVisible(isConnected);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_start:
                messages = new ArrayList<>();
                adapter.notifyDataSetChanged();
                viewModel.connectWebSocket(credential);
                break;
            case R.id.action_stop:
                Log.v(LOG_TAG, "Stop pressed");
                if (webSocket != null){
                    Log.v(LOG_TAG, "Closing socket");
                    webSocket.close();
                }
                break;
            default:
                Log.v(LOG_TAG, "menu item not matched");
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////// Observers  ///////////////////////////
    ////////////////////////////////////////////////////////////

    public Observer<Error> errorObserver = error -> {
        if (error != null){
            ShowMessage.singleButtonMessage("ERROR\n" + error, this, null);
        }
    };

    public Observer<Boolean> webSocketConnectedObserver = object -> {
        if (object != null){
            isConnected = object;
            adapter.notifyDataSetChanged();
            invalidateOptionsMenu();
        }
    };


    public Observer<List<String>> messageObserver = object -> {
        if (object != null){
            messages = object;
            adapter.notifyDataSetChanged();
        }
    };


    public Observer<IsyWebSocket> webSocketObserver = object -> {
        if (object != null){
            webSocket = object;
        }
    };


    ///////////////////// Life Cycle ///////////////////////////
    ////////////////////////////////////////////////////////////

    @Override
    public void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "-- ON STOP --");
    }

    @Override
    protected void onPause(){
        Log.v(LOG_TAG, "--ON PAUSE--");
        setObservers(false);
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        setObservers(true);
        Log.v(LOG_TAG, "--ON RESUME__");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "- ON DESTROY -");
    }


}