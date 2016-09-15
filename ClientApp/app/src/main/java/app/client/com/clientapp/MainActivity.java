package app.client.com.clientapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import app.service.com.serviceapp.ExampleAIDLService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXPLICIT_INTENT_PACKAGE = "app.service.com.serviceapp";
    public static final String EXPLICIT_INTENT_ACTION = ".service.ExampleService";

    ExampleAIDLService proxyService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            proxyService = ExampleAIDLService.Stub.asInterface(service);
            try {
                Toast.makeText(MainActivity.this, proxyService.joinString("LEFT", "RIGHT"), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.use_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.use_service) {
            Intent explicitIntent = new Intent(EXPLICIT_INTENT_PACKAGE+EXPLICIT_INTENT_ACTION);
            explicitIntent.setPackage(EXPLICIT_INTENT_PACKAGE);
            boolean ret = bindService(explicitIntent, serviceConnection, BIND_AUTO_CREATE);
            Toast.makeText(MainActivity.this, "bind result -> " + ret, Toast.LENGTH_SHORT).show();
        }
    }
}
