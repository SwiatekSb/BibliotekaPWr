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
public class ISBNActivity extends RoboActivity {

    private void setUpView() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpView();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");

                Intent bookListActivity = new Intent(this, BookListActivity.class);
                bookListActivity.putExtra(BundleConstants.SEARCH_VALUE, contents);
                startActivity(bookListActivity);
            } else if (resultCode == RESULT_CANCELED) {
                Log.i("App", "Scan unsuccessful");
            }
        }
    }

}
