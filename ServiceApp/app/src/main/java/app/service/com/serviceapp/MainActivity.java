package app.service.com.serviceapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import app.service.com.serviceapp.service.ExampleService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ExampleAIDLService proxyService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            proxyService = ExampleAIDLService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(this, ExampleService.class), serviceConnection, BIND_AUTO_CREATE);
        setContentView(R.layout.activity_main);
        findViewById(R.id.use_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.use_service) {
            try {
                Toast.makeText(this,proxyService.joinString("LEFT","RIGHT"),Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
