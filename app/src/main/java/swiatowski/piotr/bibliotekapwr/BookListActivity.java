package swiatowski.piotr.bibliotekapwr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import swiatowski.piotr.bibliotekapwr.db.entity.BookEntity;
import swiatowski.piotr.bibliotekapwr.parserHTML.HtmlConstants;
import swiatowski.piotr.bibliotekapwr.parserHTML.ParseURL;

/**
 * Created by Piotrek on 2014-11-01.
 *
 */
@ContentView(R.layout.activity_book_list)
public class BookListActivity extends RoboActivity {

    private String mSearchWord;
    private List<BookEntity> mBooks;
    private BookAdapter mBookAdapter;

    private ProgressDialog mProgressDialog;

    @InjectView(R.id.lvBooks)
    private ListView mBookList;

    @InjectView(R.id.btnMore)
    private Button mBtnMore;

    @Inject
    private ParseURL mParser;

    private Page mPage;

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mSearchWord = extras.getString(BundleConstants.SEARCH_VALUE);
        }
    }

    private void getBooks() {
        String search = HtmlConstants.SEARCH_URL + mSearchWord;
        new LoadBooks().execute(new String[]{search});
    }

    private class LoadBooks extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                publishProgress("Loading books");
                mPage = mParser.parseUrl(strings);
                mBooks.addAll(mPage.getBookList());
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
            updateList();
        }
    }

    private void updateList() {
        mBookAdapter.refill(mBooks);
    }

    private void setUpView() {
        mBooks = new ArrayList<BookEntity>();
        mBookAdapter = new BookAdapter(this,
                R.layout.row_book_information, mBooks);
        mBookList.setAdapter(mBookAdapter);
        mProgressDialog = new ProgressDialog(BookListActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }

    private void setUpListeners() {
        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPage.isNextPage()) {
                    new LoadBooks().execute(new String[]{mPage.getNextPage()});
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpView();

        getBundle();
        getBooks();

        setUpListeners();
    }

    /**
     * This Adapter is used to show Books.
     */
    private static class BookAdapter extends ArrayAdapter<BookEntity> {

        private Activity mContext;
        private List<BookEntity> mBookRowList;
        private int mLayoutResourceId;
        private Handler uiHandler = new Handler();

        public BookAdapter(Activity context, int layoutResourceId,
                           List<BookEntity> bookRowList) {
            super(context, layoutResourceId, bookRowList);
            mContext = context;
            mBookRowList = new ArrayList<BookEntity>();
            mLayoutResourceId = layoutResourceId;
        }

        static class ViewHolder {
            public TextView mTxtvTitle;
            public TextView mTxtvAuthor;
            public TextView mTxtvYear;
            public Spinner mSpLibrary;
            public Button mBtnConfirm;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = mContext.getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.mTxtvTitle = (TextView) convertView
                        .findViewById(R.id.txtBookTitleRow);
                viewHolder.mTxtvAuthor = (TextView) convertView
                        .findViewById(R.id.txtvBookAuthorRow);
                viewHolder.mTxtvYear = (TextView) convertView
                        .findViewById(R.id.txtvBookYearRow);
                viewHolder.mSpLibrary = (Spinner) convertView.findViewById(R.id.spBookPLace);
                viewHolder.mBtnConfirm = (Button) convertView.findViewById(R.id.btnConfirm);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final BookEntity bookRow = mBookRowList.get(position);
            String[] names = bookRow.getListOfLibraryName();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, names); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.mSpLibrary.setAdapter(spinnerArrayAdapter);

            viewHolder.mTxtvTitle.setText(bookRow.getTitle() + "");
            viewHolder.mTxtvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bookInfoActivity = new Intent(mContext, BookInfoActivity.class);
                    bookInfoActivity.putExtra(BundleConstants.BOOK_ROW, bookRow);
                    mContext.startActivity(bookInfoActivity);
                }
            });

            viewHolder.mBtnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent rentInfoActivity = new Intent(mContext, RentInfoActivity.class);
                    rentInfoActivity.putExtra(BundleConstants.SELECTED_ROW, viewHolder.mSpLibrary.getSelectedItem().toString());
                    rentInfoActivity.putExtra(BundleConstants.BOOK_ROW, bookRow);
                    mContext.startActivity(rentInfoActivity);
                }
            });

            viewHolder.mTxtvAuthor.setText(bookRow.getAuthor() + "");
            viewHolder.mTxtvYear.setText(bookRow.getYear() + "");

            return convertView;
        }

        public void refill(List<BookEntity> lists) {
            mBookRowList.addAll(lists);
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

}
