package swiatowski.piotr.bibliotekapwr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Piotrek on 2014-11-01.
 *
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends RoboActivity {

    @InjectView(R.id.btnSearch)
    private Button mBtnSearch;
    @InjectView(R.id.EtSearchWord)
    private EditText mEtSearchWord;
    @InjectView(R.id.SpType)
    private Spinner mSpOptions;
    @InjectView(R.id.SpPlace)
    private Spinner mSpPlaces;

    private void setUpView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpOptions.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.places, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpPlaces.setAdapter(adapter);
    }

    private void setUpListeners() {
        final Intent bookListActivity = new Intent(this, BookListActivity.class);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookListActivity.putExtra(BundleConstants.SEARCH_VALUE, mEtSearchWord.getText() + "");
                startActivity(bookListActivity);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpView();
        setUpListeners();
    }
}
