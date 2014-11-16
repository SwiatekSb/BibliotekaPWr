package swiatowski.piotr.bibliotekapwr.services;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.inject.Inject;

import java.util.Timer;
import java.util.TimerTask;

import roboguice.service.RoboService;
import swiatowski.piotr.bibliotekapwr.db.NotificationDataSource;

/**
 * Created by Piotrek on 2014-11-11.
 */
public class SynchronizationService extends RoboService {

    @Inject
    private NotificationDataSource mNotificationDataSource;

    private static final long NOTIFICATION_DOWNLOADER_TIMER = 1000 * 60 * 1; // [1 minute]
    @Inject
    private Timer mNotificationsTimer;

    private static final int UPDATE_INTERVAL = 30; // [minutes]



    @Override
    public void onCreate() {
        super.onCreate();
//        BusProvider.getEventBus().register(this);
        Log.d("doszlo", " chodzi sfewrewrrwerwe");
        setupNotificationsDownloaderTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        BusProvider.getEventBus().unregister(this);
    }

    private void setupNotificationsDownloaderTask() {
        Log.e("NOTIFICATION", "SETUP");
        TimerTask notificationDownloaderTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("doszlo", " chodzi serviceeeee e e e e e  e  e");
                //getAllNotifications();
            }
        };
        mNotificationsTimer.schedule(notificationDownloaderTask, 0, NOTIFICATION_DOWNLOADER_TIMER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("doszlo", " dasdasdasdasda");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
