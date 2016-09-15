package app.service.com.serviceapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import app.service.com.serviceapp.ExampleAIDLService;

/**
 * Created by gordon on 16/9/15.
 */
public class ExampleService extends Service {

    public static final String TAG = ExampleService.class.getCanonicalName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return proxy;
    }

    private ExampleAIDLService.Stub proxy = new ExampleAIDLService.Stub() {
        @Override
        public String joinString(String oneStr, String anotherString) throws RemoteException {
            return oneStr +"_"+ anotherString;
        }
    };
}
