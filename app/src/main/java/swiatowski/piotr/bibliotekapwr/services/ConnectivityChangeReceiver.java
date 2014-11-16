package swiatowski.piotr.bibliotekapwr.services;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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
        NetworkInfo mobile = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi != null && mobile != null) {
            if (wifi.isAvailable() || mobile.isAvailable()) {
                Log.d("doszlo", "network is available, starting synchronization service");

                Intent serviceIntent = new Intent(context, SynchronizationService.class);
                context.startService(serviceIntent);
            }
        } else {
            Log.d("doszlo", "nulls");
        }
    }
}
