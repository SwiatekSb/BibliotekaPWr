package swiatowski.piotr.bibliotekapwr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import swiatowski.piotr.bibliotekapwr.parserHTML.HtmlConstants;
import swiatowski.piotr.bibliotekapwr.parserHTML.ParseURL;

/**
 * Created by Piotrek on 2014-11-01.
 */
@ContentView(R.layout.activity_book_list)
public class BookListActivity extends RoboActivity {

    private String mSearchWord;
    private List<BookRow> mBooks;
    private BookAdapter mBookAdapter;

    private ProgressDialog mProgressDialog;

    @InjectView(R.id.lvBooks)
    private ListView mBookList;

    @InjectView(R.id.btnMore)
    private Button mBtnMore;

    @Inject
    private ParseURL mParser;

    private Page mPage;

    //get param to parse html
    //parse html

    //get all rekord and add to book list


    //settList

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mSearchWord = extras.getString("SEARCH_VALUE");
            Log.d("doszlo", "pokaz value   "  + mSearchWord);
        }
    }

    private void getBooks() {
        String search = HtmlConstants.SEARCH_URL + mSearchWord;
        Log.d("doszlo", " urrl  "  + search);
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
        Log.d("doszlo", mBooks.size() + "   rozmiarr");
        mBookAdapter.refill(mBooks);
    }


    private void setUpView() {


        mBooks = new ArrayList<BookRow>();


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
                if (mPage.isNextPage()){
                    new LoadBooks().execute(new String[]{mPage.getNextPage()});
                }
            }
        });


        mBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
//                mBooks.get(position);
//                Log.d("doszlo",  position + "  position");
//                LinearLayout ll = (LinearLayout) view;
//                Log.d("doszlo",  view.toString() + "  position");
//                Spinner sp = (Spinner) ll.findViewById(R.id.spBookPLace);
//                Log.d("doszlo",  sp.getSelectedItem() + "  position");
//
////               String text = sp.getSelectedItem().toString();
////               Log.d("doszlo", "spinner text  " + text);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpView();
        //get Books
        getBundle();
        getBooks();

       // setUpView();
      setUpListeners();
    }

    /**
     * This Adapter is used to show Books.
     */
    private static class BookAdapter extends ArrayAdapter<BookRow> {

        private Activity mContext;
        private List<BookRow> mRestaurants;
        private int mLayoutResourceId;
        private Handler uiHandler = new Handler();

        public BookAdapter(Activity context, int layoutResourceId,
                                 List<BookRow> restaurants) {
            super(context, layoutResourceId, restaurants);
            mContext = context;
            mRestaurants = new ArrayList<BookRow>();
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

            final BookRow bookROw = mRestaurants.get(position);
            String[] names = bookROw.getListOfLibraryName();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, names); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.mSpLibrary.setAdapter(spinnerArrayAdapter);

            //String text = spinner.getSelectedItem().toString();
            final Intent intent = new Intent(mContext, BookInfoActivity.class);
            intent.putExtra("BookRow", bookROw);

            viewHolder.mTxtvTitle.setText(bookROw.getTitle() + "");
            viewHolder.mTxtvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //load more
                    mContext.startActivity(intent);
                }
            });
            final Intent intent2 = new Intent(mContext, RentInfoActivity.class);

            viewHolder.mBtnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent2.putExtra("selected", viewHolder.mSpLibrary.getSelectedItem().toString());
                    intent2.putExtra("book", bookROw);
                    Log.d("doszlo", "book " + bookROw + "    sel " + viewHolder.mSpLibrary.getSelectedItem().toString() + "");
                    mContext.startActivity(intent2);
                }
            });

            viewHolder.mTxtvAuthor.setText(bookROw.getAuthor() + "");
            viewHolder.mTxtvYear.setText(bookROw.getYear() + "");

            return convertView;
        }

        // create method refill to notyfiydatachange
        public void refill(List<BookRow> lists) {
//            mRestaurants.clear();
            mRestaurants.addAll(lists);
            Log.d("doszlo", lists.size() + "   w refil");
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }
}
