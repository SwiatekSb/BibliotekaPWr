package swiatowski.piotr.bibliotekapwr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Piotrek on 2014-11-11.
 */
@ContentView(R.layout.activity_my_book)
public class MyBookActivity extends RoboActivity{


    @InjectView(R.id.btnFavourite)
    private Button mBtnFavourite;

    @InjectView(R.id.btnMNotification)
    private Button mBtnNotification;

    private void setUpListeners() {
        final Intent intentNotificationList = new Intent(this, NotificationListActivity.class);
        mBtnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentNotificationList);
            }
        });

        mBtnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getApplicationContext(), FavouriteListActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpListeners();
    }
}
