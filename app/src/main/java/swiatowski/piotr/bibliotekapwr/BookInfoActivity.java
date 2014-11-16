package swiatowski.piotr.bibliotekapwr;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import swiatowski.piotr.bibliotekapwr.parserHTML.ParseURL;

/**
 * Created by Piotrek on 2014-11-02.
 *
 */
@ContentView(R.layout.activity_book_info)
public class BookInfoActivity extends RoboActivity {

    @InjectView(R.id.txtvInfoDocumentType)
    private TextView mTxtvDocumentType;
    @InjectView(R.id.txtvInfoAuthor)
    private TextView mTxtvAuthor;
    @InjectView(R.id.txtvInfoInstituteName)
    private TextView mTxtvInstituteName;
    @InjectView(R.id.txtvInfoTitle)
    private TextView mTxtvTitle;
    @InjectView(R.id.txtvInfoPublishing)
    private TextView mTxtvPublishing;
    @InjectView(R.id.txtvInfoISBN)
    private TextView mTxtvISBN;

    @Inject
    private ParseURL mParser;
    private BookRow mBookRow;
    private ProgressDialog mProgressDialog;

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mBookRow = (BookRow) extras.getSerializable(BundleConstants.BOOK_ROW);
        }
    }

    private class LoadInfoBooks extends AsyncTask<String, String, Void> {

        private BookRow book;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                publishProgress("Loading info books");
                book = mParser.parseUrlInfo(mBookRow.getInfoHref());
//                mPage = mParser.parseUrl(strings);
//                mBooks.addAll(mPage.getBookList());
            } catch (Throwable t) {
                mProgressDialog.dismiss();
                t.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setMessage(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();

            setBookInfo(book);
            updateView();
        }
    }

    private void setBookInfo(BookRow bo) {

        mBookRow.setDocumentType(bo.getDocumentType());
        mBookRow.setFullTitle(bo.getFullTitle());
        mBookRow.setFullYear(bo.getFullYear());
        mBookRow.setInstituteName(bo.getInstituteName());
        mBookRow.setISBN(bo.getISBN());
    }

    private void updateView() {
        mTxtvAuthor.setText(mBookRow.getAuthor());
        mTxtvDocumentType.setText(mBookRow.getDocumentType());
        mTxtvInstituteName.setText(mBookRow.getInstituteName());
        mTxtvISBN.setText(mBookRow.getISBN());
        mTxtvPublishing.setText(mBookRow.getFullYear());
        mTxtvTitle.setText(mBookRow.getFullTitle());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(BookInfoActivity.this);

        getData();
        if (!mBookRow.getInfoData()) {
            new LoadInfoBooks().execute("param");
        } else {
            updateView();
        }
    }

}
