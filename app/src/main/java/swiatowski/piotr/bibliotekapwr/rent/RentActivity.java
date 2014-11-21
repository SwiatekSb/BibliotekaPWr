package swiatowski.piotr.bibliotekapwr.rent;

import android.os.Bundle;
import android.webkit.WebView;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import swiatowski.piotr.bibliotekapwr.R;
import swiatowski.piotr.bibliotekapwr.parserHTML.HtmlConstants;
import swiatowski.piotr.bibliotekapwr.utlis.BundleConstants;

/**
 * Created by Piotrek on 2014-11-11.
 */
@ContentView(R.layout.activity_rent_webview)
public class RentActivity extends RoboActivity {

    @InjectView(R.id.webView1)
    private WebView webView;

    private String mUrlToRent;

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUrlToRent = extras.getString(BundleConstants.RENT_URL);
        }
    }

    private void loadRentPage() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(HtmlConstants.ALEPH_URL + mUrlToRent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
        loadRentPage();
    }
}
