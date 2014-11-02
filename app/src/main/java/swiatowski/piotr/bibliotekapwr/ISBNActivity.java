package swiatowski.piotr.bibliotekapwr;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

/**
 * Created by Piotrek on 2014-11-01.
 */
@ContentView(R.layout.activity_start)
public class ISBNActivity  extends RoboActivity {


    private void setUpView() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 0);
    }

    private void setUpListeners() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                Intent intent2 = new Intent(this, BookListActivity.class);

                        intent2.putExtra("SEARCH_VALUE", contents);
                        startActivity(intent2);

                Log.d("scanD",contents + "");
                // Handle successful scan

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Log.i("App", "Scan unsuccessful");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpView();
        setUpListeners();

    }
}
