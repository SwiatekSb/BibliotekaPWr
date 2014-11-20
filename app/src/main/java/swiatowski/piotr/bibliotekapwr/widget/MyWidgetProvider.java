package swiatowski.piotr.bibliotekapwr.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import swiatowski.piotr.bibliotekapwr.NotificationListActivity;
import swiatowski.piotr.bibliotekapwr.R;
import swiatowski.piotr.bibliotekapwr.SearchActivity;

/**
 * Created by Piotrek on 2014-11-11.
 */
public class MyWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            Intent searchActivity = new Intent(context, SearchActivity.class);
            Intent notiActivity = new Intent(context, NotificationListActivity.class);

            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, searchActivity, 0);
            PendingIntent notiPendingIntent = PendingIntent.getActivity(context, 0, notiActivity, 0);

            remoteViews.setOnClickPendingIntent(R.id.btnSearchWidget, configPendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.btnNotifWidget, notiPendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
