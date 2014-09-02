package swiatowski.piotr.bibliotekapwr.parserHTML;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Piotrek on 2014-09-02.
 */
public class ParseURL extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        try {
            Document doc  = Jsoup.connect(HtmlConstants.URL + strings[0]).get();

            String title = doc.title();

            Log.d("doszlo", title);
            Element text = doc.select("div.text1").get(3);
            String [] tab = text.ownText().split(" ");
            Log.d("doszlo", tab[5]);    // number of all position

            

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }
}
