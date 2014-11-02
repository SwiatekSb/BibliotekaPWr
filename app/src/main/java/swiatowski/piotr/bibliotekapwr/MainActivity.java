package swiatowski.piotr.bibliotekapwr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Piotrek on 2014-11-01.
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
        final Intent intent = new Intent(this, SearchActivity.class);
        final Intent intent2 = new Intent(this, ISBNActivity.class);
        final Intent intent4 = new Intent(this, RentInfoActivity.class);

        mBtnSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        mBtnScanActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

        mBtnInformationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent4);
            }
        });

        String key = "http://aleph.bg.pwr.wroc.pl/F/9MLF2QFTTTRBK12MX187PX4E5M762P45IHID2HPKFXLN7318UE-11440?func=item-hold-request&doc_library=TUR50&adm_doc_number=000163511&item_sequence=000020&year=&volume=&sub_library=BG-MG&type=&no_loaned=N&start_rec_key=&end_rec_key=";

        // key generat only once
        Uri uri = Uri.parse(key);
        final Intent intent3 = new Intent(Intent.ACTION_VIEW, uri);

        mBtnMyBooksActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent3);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpListeners();
    }

}
