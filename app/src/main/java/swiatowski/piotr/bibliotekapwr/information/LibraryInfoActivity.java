package swiatowski.piotr.bibliotekapwr.information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import swiatowski.piotr.bibliotekapwr.R;
import swiatowski.piotr.bibliotekapwr.db.entity.LibraryEntity;
import swiatowski.piotr.bibliotekapwr.utlis.BundleConstants;

/**
 * Created by Piotrek on 2014-11-16.
 */
@ContentView(R.layout.library_info_activity)
public class LibraryInfoActivity extends RoboActivity {

    @InjectView(R.id.btnLibNavigate)
    private Button btnNavigate;

    @InjectView(R.id.txtvLibraryInfoName)
    private TextView txtvName;

    @InjectView(R.id.txtvLibraryInfoAddress)
    private TextView txtvInfo;
    @InjectView(R.id.txtvLibraryInfoBuilding)
    private TextView txtvBuilding;
    @InjectView(R.id.txtvLibraryStartTime)
    private TextView txtvStartTime;
    @InjectView(R.id.txtvLibraryInfoEndTime)
    private TextView txtvCloseTime;

    private LibraryEntity mLibraryEntity;


    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mLibraryEntity = (LibraryEntity) extras.getSerializable(BundleConstants.SELECTED_LIBRARY);
        }
    }

    private void setUpView() {
        txtvName.setText(mLibraryEntity.getName());
        txtvBuilding.setText(mLibraryEntity.getBuilding());
        txtvInfo.setText(mLibraryEntity.getAddress());
        txtvStartTime.setText(mLibraryEntity.getStartTime());
        txtvCloseTime.setText(mLibraryEntity.getCloseTime());

    }

    private void setUpListeners() {
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentForMaps(mLibraryEntity.getName(), String.valueOf(mLibraryEntity.getLatitiude()), String.valueOf(mLibraryEntity.getLongitiure())));
            }
        });
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();

        setUpView();
        setUpListeners();
    }
}
