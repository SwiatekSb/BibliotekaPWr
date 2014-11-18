package swiatowski.piotr.bibliotekapwr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Piotrek on 2014-11-01.
 *
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

    @InjectView(R.id.btnSearchActivity)
    private Button mBtnSearchActivity;
    @InjectView(R.id.btnSacnActivity)
    private Button mBtnScanActivity;
    @InjectView(R.id.btnMyBooksActivity)
    private Button mBtnMyBooksActivity;
    @InjectView(R.id.btnInformationActivity)
    private Button mBtnInformationActivity;

    private void setUpListeners() {
        mBtnSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(searchActivity);
            }
        });

        mBtnScanActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isbnActivity = new Intent(getApplicationContext(), ISBNActivity.class);

                startActivity(isbnActivity);
            }
        });

        mBtnInformationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLibrary = new Intent(getApplicationContext(), LibraryListActivity.class);
                startActivity(intentLibrary);
            }
        });


        mBtnMyBooksActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMyBookActivity = new Intent(getApplicationContext(), MyBookActivity.class);
                startActivity(intentMyBookActivity);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpListeners();
    }

}
