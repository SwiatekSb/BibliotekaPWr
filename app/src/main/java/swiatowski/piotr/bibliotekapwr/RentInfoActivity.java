package swiatowski.piotr.bibliotekapwr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import swiatowski.piotr.bibliotekapwr.parserHTML.ParseURL;

/**
 * Created by Piotrek on 2014-11-02.
 */
@ContentView(R.layout.activity_rent_info)
public class RentInfoActivity extends RoboActivity {


    @InjectView(R.id.btnNavigate)
    private Button mBtnNavigate;

    @InjectView(R.id.txtvRentTitle)
    private TextView mTitle;

    @InjectView(R.id.txtvLibraryName)
    private TextView mLibraryName;

    @InjectView(R.id.lvRentBooks)
    private ListView mLvBooks;

    private BookRow mBookRow;
    private String selected;
    private LibraryBook mLibraryBook;

    private RentAdapter mRentAdapter;

    private ProgressDialog mProgressDialog;

    private List<Rent> mListRent;

    @Inject
    private ParseURL mParser;

    private void getData() {
        Log.d("doszlo", "getData");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mBookRow = (BookRow) extras.getSerializable("book");
            selected = extras.getString("selected");
            Log.d("doszlo", "book " + mBookRow + "    sel " + selected + "");

        }
    }


    private Intent intentForMaps(String tag, String latitude, String longitude) {
        // building URI with StringBuilder since String.format may cause
        // fractional part of a floating point numbers to be separated with
        // a different character than expected (which is .)
        return new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse((new StringBuilder("geo:"))
                        .append(latitude)
                        .append(",")
                        .append(longitude)
                        .append("?q=")
                        .append(latitude)
                        .append(",")
                        .append(longitude)
                        .append("(")
                        .append(tag)
                        .append(")")
                        .toString()));
    }

    private void setUpListeners() {
        mBtnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentForMaps("test", "51.107350", "17.061911"));
            }
        });
    }

    private void getLibrary() {
       mLibraryBook =  mBookRow.getLibraryByName(selected);

    }

    private void setUpView() {
        mTitle.setText(mBookRow.getTitle() + "");
        if (mLibraryBook != null) {
            mLibraryName.setText(mLibraryBook.getName() + "");
        }
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
                publishProgress("Loading book rent info");
                mListRent = mParser.parseUrlRent(mLibraryBook.getInfoBookHref());
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
            updateList();
        }
    }

    private void updateList() {
        mRentAdapter = new RentAdapter(this,
                R.layout.row_rent_book, mListRent);
        mLvBooks.setAdapter(mRentAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(RentInfoActivity.this);
        getData();
        getLibrary();
        setUpListeners();
        setUpView();
        new LoadBooks().execute(new String[] {mLibraryBook.getInfoBookHref()});

    }

    private static class RentAdapter extends ArrayAdapter<Rent> {

        private Activity mContext;
        private List<Rent> mRestaurants;
        private int mLayoutResourceId;
        private Handler uiHandler = new Handler();

        public RentAdapter(Activity context, int layoutResourceId,
                           List<Rent> restaurants) {
            super(context, layoutResourceId, restaurants);
            mContext = context;
            mRestaurants = restaurants;
            mLayoutResourceId = layoutResourceId;
        }

        static class ViewHolder {
            public TextView mTxtvSignature;
            public TextView mTxtvStatus;
            public TextView mTxtvDate;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = mContext.getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.mTxtvSignature = (TextView) convertView
                        .findViewById(R.id.txtvSignature);
                viewHolder.mTxtvStatus = (TextView) convertView
                        .findViewById(R.id.txtvStatus);
                viewHolder.mTxtvDate = (TextView) convertView
                        .findViewById(R.id.txtvData);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final Rent bookROw = mRestaurants.get(position);


            viewHolder.mTxtvSignature.setText(bookROw.getSignature() + "");
            viewHolder.mTxtvStatus.setText(bookROw.getStatus() + "");
            viewHolder.mTxtvDate.setText(bookROw.getData() + "");

            return convertView;
        }

        // create method refill to notyfiydatachange
        public void refill(List<BookRow> lists) {
//            mRestaurants.clear();
         //   mRestaurants.addAll(lists);
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
