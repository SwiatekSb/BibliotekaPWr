package swiatowski.piotr.bibliotekapwr.rent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import swiatowski.piotr.bibliotekapwr.R;
import swiatowski.piotr.bibliotekapwr.db.BookDataSource;
import swiatowski.piotr.bibliotekapwr.db.LibraryDataSource;
import swiatowski.piotr.bibliotekapwr.db.NotificationDataSource;
import swiatowski.piotr.bibliotekapwr.db.entity.BookEntity;
import swiatowski.piotr.bibliotekapwr.db.entity.LibraryEntity;
import swiatowski.piotr.bibliotekapwr.db.entity.NotificationEntity;
import swiatowski.piotr.bibliotekapwr.model.LibraryBook;
import swiatowski.piotr.bibliotekapwr.model.Rent;
import swiatowski.piotr.bibliotekapwr.parserHTML.ParseURL;
import swiatowski.piotr.bibliotekapwr.utlis.BundleConstants;

/**
 * Created by Piotrek on 2014-11-02.
 *
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

    @Inject
    private NotificationDataSource notificationDataSource;
    @Inject
    private BookDataSource mBookDataSource;

    @Inject
    private LibraryDataSource mLibraryDataSource;

    private BookEntity mBookRow;
    private String selected;
    private LibraryBook mLibraryBook;

    private RentAdapter mRentAdapter;

    private ProgressDialog mProgressDialog;

    private List<Rent> mListRent;

    @Inject
    private ParseURL mParser;

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mBookRow = (BookEntity) extras.getSerializable(BundleConstants.BOOK_ROW);
            selected = extras.getString(BundleConstants.SELECTED_ROW);
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
                String nameLib = mLibraryBook.getName();
                String[] tab = nameLib.split("\\(");
                LibraryEntity entity = mLibraryDataSource.get(tab[0] + "");
                if (entity != null)
                     startActivity(intentForMaps(entity.getName(), String.valueOf(entity.getLatitiude()), String.valueOf(entity.getLongitiure())));
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

        final Intent rentBookIntent = new Intent(this, RentActivity.class);
        mLvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String linkToRent = mListRent.get(i).getHref();

                if (!linkToRent.equals("")) {
                    rentBookIntent.putExtra(BundleConstants.RENT_URL, linkToRent);
                    startActivity(rentBookIntent);
                } else {
                    //TODO podzial na typ
                    alert("Ksiazka niedostepna");
                    alertNotification("Powiadomienie", linkToRent, i);

                }
            }
        });
    }

    public void alertNotification(String title, final String linkToRent, final int i) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RentInfoActivity.this);

        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage("Czy chcesz ustawić powiadomienie ?");
        dialogBuilder.setNegativeButton("Ustaw", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("doszlo", " pokaaaa" + "  " +  mListRent.get(i).getHref() + "   " +mBookRow.getTitle()+ "");
            notificationDataSource.insert(new NotificationEntity(mListRent.get(i).getSignature(), mBookRow.getInfoHref(), linkToRent, mBookRow.getTitle()));
            dialog.dismiss();
            }
        });
        dialogBuilder.setPositiveButton("Cofnij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        showAlertDialog(dialogBuilder);

    }

    public void alert(String title) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RentInfoActivity.this);


        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage("Czy chcesz dodać ksiazke do ulubionych ?")        ;
        dialogBuilder.setNegativeButton("Dodaj", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //add book to database
                mBookDataSource.insert(mBookRow);
                dialog.dismiss();
            }
        });
        dialogBuilder.setPositiveButton("Cofnij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        showAlertDialog(dialogBuilder);

    }

    private void showAlertDialog(AlertDialog.Builder dialogBuilder){
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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
        public void refill(List<BookEntity> lists) {
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
