package swiatowski.piotr.bibliotekapwr.favourite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import swiatowski.piotr.bibliotekapwr.R;
import swiatowski.piotr.bibliotekapwr.db.BookDataSource;
import swiatowski.piotr.bibliotekapwr.db.entity.BookEntity;

/**
 * Created by Piotrek on 2014-11-20.
 */
@ContentView(R.layout.activity_book_list)
public class FavouriteListActivity extends RoboActivity {

    private List<BookEntity> mBooks;
    private NotificationAdapter mBookAdapter;

    @Inject
    private BookDataSource mBookDataSOurce;

    @InjectView(R.id.lvBooks)
    private ListView mBookList;

    @InjectView(R.id.btnMore)
    private Button mBtnMore;

    private void setUpView() {
        mBooks = mBookDataSOurce.getAll();
        Log.d("doszlo", mBooks.size() + "  size ");


        mBookAdapter = new NotificationAdapter(this,
                R.layout.row_notification_book, mBooks);
        mBookList.setAdapter(mBookAdapter);

        mBtnMore.setVisibility(View.GONE);
    }

    private void setUpListeners() {
        mBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             //   alertDelete("Powiadomienie", i);
            }
        });
    }

    public void alertDelete(String title, final int i) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FavouriteListActivity.this);

        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage("Czy chcesz usunąć powiadomienie ?");
        dialogBuilder.setNegativeButton("Usuń", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                mBookAdapter.refill(mBooks);
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
        setUpView();

        setUpListeners();
    }

    private static class NotificationAdapter extends ArrayAdapter<BookEntity> {

        private Activity mContext;
        private List<BookEntity> mNotificationList;
        private int mLayoutResourceId;
        private Handler uiHandler = new Handler();

        public NotificationAdapter(Activity context, int layoutResourceId,
                                   List<BookEntity> bookRowList) {
            super(context, layoutResourceId, bookRowList);
            mContext = context;
            mNotificationList = bookRowList;
            mLayoutResourceId = layoutResourceId;
        }

        static class ViewHolder {
            public TextView mTxtvTitle;
            public TextView mTxtvSignature;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = mContext.getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.mTxtvTitle = (TextView) convertView
                        .findViewById(R.id.txtvNotiTitle);
                viewHolder.mTxtvSignature = (TextView) convertView
                        .findViewById(R.id.txtvNotiSignature);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final BookEntity bookRow = mNotificationList.get(position);

            viewHolder.mTxtvTitle.setText(bookRow.getTitle() + "");
            viewHolder.mTxtvSignature.setText(bookRow.getAuthor() + "");

            return convertView;
        }

        public void refill(List<BookEntity> lists) {
            mNotificationList = new ArrayList<BookEntity>();
            mNotificationList.addAll(lists);

            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }
}