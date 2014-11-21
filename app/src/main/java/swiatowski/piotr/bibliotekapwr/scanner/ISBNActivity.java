package swiatowski.piotr.bibliotekapwr.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import roboguice.activity.RoboActivity;
import swiatowski.piotr.bibliotekapwr.searcher.BookListActivity;
import swiatowski.piotr.bibliotekapwr.utlis.BundleConstants;

/**
 * Created by Piotrek on 2014-11-01.
 *
 */
public class ISBNActivity extends RoboActivity {

    private void setUpView() {

        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
      //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
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
       // super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
            Log.d("doszlos", "znalezione ");
                Intent bookListActivity = new Intent(this, BookListActivity.class);
                bookListActivity.putExtra(BundleConstants.SEARCH_VALUE, contents);
                startActivity(bookListActivity);
            } else if (resultCode == 0) {
                Log.d("doszlos", "Scan unsuccessful");
                onBackPressed();
                finish();

            }
        } else {
            finish();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}