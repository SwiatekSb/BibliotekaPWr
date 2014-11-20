package swiatowski.piotr.bibliotekapwr.services;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

import com.google.inject.Inject;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import roboguice.service.RoboService;
import swiatowski.piotr.bibliotekapwr.BookListActivity;
import swiatowski.piotr.bibliotekapwr.BundleConstants;
import swiatowski.piotr.bibliotekapwr.db.NotificationDataSource;
import swiatowski.piotr.bibliotekapwr.db.entity.NotificationEntity;
import swiatowski.piotr.bibliotekapwr.parserHTML.HtmlConstants;

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

    private Handler uiHandler = new Handler();

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
                List<NotificationEntity> notificationEntities = mNotificationDataSource.getAll();

                for (final NotificationEntity noti: notificationEntities) {
                    Date date = new Date();
                    final int day = date.getDate();

                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (noti.getDay() != day ) {
                                alert("Ksiazka " + noti.getTitle() + " dostepna", noti.getSignature());
                                mNotificationDataSource.update(noti.getDay(), noti.getId());
                            }
                        }
                    });

                }
                //getAllNotifications();
            }
        };
        mNotificationsTimer.schedule(notificationDownloaderTask, 0, NOTIFICATION_DOWNLOADER_TIMER);
    }

    public void alert(String title, final String signature) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(title);
        builder.setMessage("Sprawdz lub usuń powiadomienie");
        builder.setPositiveButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Do something
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Sprawdź", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                final Intent bookListActivity = new Intent(getApplicationContext(), BookListActivity.class);

                bookListActivity.putExtra(BundleConstants.SEARCH_VALUE, signature.replace("/", "%") + HtmlConstants.SIGNATURE);
                bookListActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(bookListActivity);

                dialog.dismiss();
            }
        });
      //  Looper.prepare();
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();

    }

    private void showAlertDialog(AlertDialog.Builder dialogBuilder){


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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
