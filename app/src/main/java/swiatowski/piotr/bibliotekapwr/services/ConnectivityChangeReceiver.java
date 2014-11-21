package swiatowski.piotr.bibliotekapwr.services;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.inject.Inject;

import roboguice.receiver.RoboBroadcastReceiver;

/**
 * Created by Piotrek on 2014-11-11.
 */
public class ConnectivityChangeReceiver extends RoboBroadcastReceiver{
    @Inject
    private ConnectivityManager mConnectivityManager;

    @Override
    protected void handleReceive(Context context, Intent intent) {
        NetworkInfo wifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi != null) {
            if (wifi.isAvailable() ) {
                Intent serviceIntent = new Intent(context, SynchronizationService.class);
                context.startService(serviceIntent);
            }
        }
    }
}
