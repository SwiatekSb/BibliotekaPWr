package swiatowski.piotr.bibliotekapwr.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import swiatowski.piotr.bibliotekapwr.NotificationListActivity;
import swiatowski.piotr.bibliotekapwr.R;
import swiatowski.piotr.bibliotekapwr.SearchActivity;

/**
 * Created by Piotrek on 2014-11-11.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_CLICK = "ACTION_CLICK";
    // our actions for our buttons
    public static String ACTION_WIDGET_REFRESH = "ActionReceiverRefresh";
    public static String ACTION_WIDGET_SETTINGS = "ActionReceiverSettings";
    public static String ACTION_WIDGET_ABOUT = "ActionReceiverAbout";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
           // Log.w("WidgetExample", String.valueOf(number));
            // Set the text
           // remoteViews.setTextViewText(R.id.update, String.valueOf(number));

            // Register an onClickListener
            Intent intent = new Intent(context, MyWidgetProvider.class);
            Intent searchActivity = new Intent(context, SearchActivity.class);
            Intent notiActivity = new Intent(context, NotificationListActivity.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, searchActivity, 0);
            PendingIntent notiPendingIntent = PendingIntent.getActivity(context, 0, notiActivity, 0);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.btnSearchWidget, configPendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.btnNotifWidget, notiPendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
