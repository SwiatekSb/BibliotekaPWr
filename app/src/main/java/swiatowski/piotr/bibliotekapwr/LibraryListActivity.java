package swiatowski.piotr.bibliotekapwr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import swiatowski.piotr.bibliotekapwr.db.LibraryDataSource;
import swiatowski.piotr.bibliotekapwr.db.entity.LibraryEntity;

/**
 * Created by Piotrek on 2014-11-16.
 */
@ContentView(R.layout.activity_book_list)
public class LibraryListActivity extends RoboActivity {

    private List<LibraryEntity> mLibrariesList;
    private LibraryAdapter mBookAdapter;

    @Inject
    private LibraryDataSource mLibraryDataSource;

    @InjectView(R.id.lvBooks)
    private ListView mBookList;

    @InjectView(R.id.btnMore)
    private Button mBtnMore;


    private void setUpView() {
        mLibrariesList = mLibraryDataSource.getAll();

        mBookAdapter = new LibraryAdapter(this,
                R.layout.row_notification_book, mLibrariesList);
        mBookList.setAdapter(mBookAdapter);

        mBtnMore.setVisibility(View.GONE);
    }

    private void setUpListeners() {
        mBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), LibraryInfoActivity.class);

                intent.putExtra(BundleConstants.SELECTED_LIBRARY, mLibrariesList.get(i));
                startActivity(intent);
            }
        });
    }
   // A-1 : Lat: 51.107350  Lon:17.061911
           // * D-21 Lat: 51.109756   Lon:17.058116
            //* D-1:  Lat: 51.110411  Lon: 17.058133
           // * D-2  Lat: 51.110196  Lon: 17.057371
           // * A-3:  Lat: 51.108058  Lon: 17.064023
           // * D-20: Lat: 51.110654   Lon: 17.059496
           // * B-4   Lat: 51.108324  Lon: 17.065026
            //* C-6  Lat: 51.108443   Lon:17.060628
            //* C-3 Lat: 51.108967  Lon:17.060236
            //* C-5: Lat: 51.109448   Lon: 17.058959
            //* H-4 Lat: 51.108853  Lon: 17.054491
           // * L-1  Lat: 51.104533   Lon: 17.054287
           // * E-1 i  E-4  Lat: 51.119145  Lon: 17.054371

    private void addDataToDataBase() {
        LibraryEntity a1 = new LibraryEntity( 51.107350f,17.061911f,"Wypożyczalnia Główna", "Wyb. Wyspiańskiego 27", "A-1", "pn-pt 8:00; sob-nd 9:00", "pn-pt 18:00; sob-nd 14:00");
        LibraryEntity czyt = new LibraryEntity( 51.107350f,17.061911f,"Czytelnia Główna", "Wyb. Wyspiańskiego 27", "A-1","pn-pt 8:00; sob-nd 9:00", "pn-pt 18:00; sob-nd 14:00");
        //LibraryEntity d21 = new LibraryEntity(51.109756,17.058116,)
        LibraryEntity d20 = new LibraryEntity(51.110654f, 17.059496f, "OCW Elektrycznego", "ul. Janiszewskiego 8" , "D-20", "pn-pt 9:00", "pn-cz 17:00; pt 15:00");
        LibraryEntity e4 = new LibraryEntity(51.119145f,17.054371f, "OCW Architektury", "ul. B.Prusa 53/55", "E-4, E-1", "pn-pt 9:00", "pn,wt,cz,pt 15:00; sr 17:00");
        LibraryEntity d1 = new LibraryEntity(51.110411f,17.058133f, "OCW Budownictwa Lądowego i Wodnego", "pl. Grunwaldzki 13", "D-1", "pn-pt 9:00", "pn,pt 15:00; wt,sr,cz 17:00");
        LibraryEntity c3 = new LibraryEntity(51.108967f, 17.060236f, "OCW Elektroniki", "ul. Janiszewskiego 11/17", "C-3", "pn-pt 9:00", "pn-cz 17:00; pt 15:00");
        LibraryEntity c5 = new LibraryEntity(51.109448f,17.058959f, "OCW Elektroniki C5","ul. Janiszewskiego 9", "C-5",  "pn-pt 9:00", "pn-cz 17:00; pt 15:00");
        LibraryEntity c6= new LibraryEntity(51.108443f, 17.060628f, "OCW Elektroniki C6", "ul. Norwida 4/6", "C-6" , "pn-pt 9:00", "pn-cz 17:00; pt 15:00");
        LibraryEntity d2 = new LibraryEntity(51.110196f, 17.057371f, "OCW Inżynierii Środowiska","pl. Grunwaldzki 9", "D-2","pn-pt 9:00", "pn-pt 15:00");
        LibraryEntity l1 = new LibraryEntity(51.104533f,17.054287f, "OCW Geoinżynierii","ul. Na Grobli 15", "L-1", "pn-pt 9:00", "pn-cz 16:00; pt 15:00");
        LibraryEntity b4 = new LibraryEntity(51.108324f, 17.065026f, "OCW Informatyki i Zarządzania","ul. Łukasiewicza 5", "B-4",  "pn-pt 9:00", "pn-cz 18:00; pt 15:00" );
        LibraryEntity h4 = new LibraryEntity(51.108853f,17.054491f, "OCW Języków Obcych","Wyb. Wyspiańskiego 8", "H-4","pn-pt 9:00", "pn-cz 17:00; pt 16:00"  );



        mLibraryDataSource.insert(a1);
        mLibraryDataSource.insert(d20);
        mLibraryDataSource.insert(czyt);
        mLibraryDataSource.insert(e4);
        mLibraryDataSource.insert(d1);
        mLibraryDataSource.insert(c3);
        mLibraryDataSource.insert(c5);
        mLibraryDataSource.insert(c6);
        mLibraryDataSource.insert(d2);
        mLibraryDataSource.insert(l1);
        mLibraryDataSource.insert(b4);
        mLibraryDataSource.insert(h4);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  addDataToDataBase();
        setUpView();

        setUpListeners();
    }


    private static class LibraryAdapter extends ArrayAdapter<LibraryEntity> {

        private Activity mContext;
        private List<LibraryEntity> mLibraryList;
        private int mLayoutResourceId;
        private Handler uiHandler = new Handler();

        public LibraryAdapter(Activity context, int layoutResourceId,
                              List<LibraryEntity> bookRowList) {
            super(context, layoutResourceId, bookRowList);
            mContext = context;
            mLibraryList = bookRowList;
            mLayoutResourceId = layoutResourceId;
        }

        static class ViewHolder {
            public TextView mTxtvLibraryName;
            public TextView mTxtvLibraryBuilding;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = mContext.getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.mTxtvLibraryName = (TextView) convertView
                        .findViewById(R.id.txtvNotiTitle);
                viewHolder.mTxtvLibraryBuilding = (TextView) convertView
                        .findViewById(R.id.txtvNotiSignature);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final LibraryEntity bookRow = mLibraryList.get(position);
            viewHolder.mTxtvLibraryName.setText(bookRow.getName() + "");
            viewHolder.mTxtvLibraryBuilding.setText(bookRow.getBuilding() + "");

            return convertView;
        }

        public void refill(List<LibraryEntity> lists) {
            mLibraryList.addAll(lists);
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }
}
