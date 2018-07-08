package photo.heller.android.cloudmap.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

import photo.heller.android.cloudmap.MainActivity;
import photo.heller.android.cloudmap.R;
import photo.heller.android.cloudmap.controllers.ActivityViewController;
import photo.heller.android.cloudmap.model.MapModel;


public class LocationDetailFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String LAT_LON_BUNDLE = "photo.heller.android.LAT_LNG_BUNDLE";

    private final String TAG = LocationDetailFragment.class.getSimpleName();

    TextView mLat;
    TextView mLon;
    Button mDeleteButton;
    Switch mPublicSwitch;
    MapModel mModel;
    LatLng mLocation;

    public LocationDetailFragment() {
        // Required empty public constructor
    }

    public void setErrorToViews() {
        mLat.setText(R.string.lat);
        mLon.setText(R.string.lon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_detail, container, false);
        mLat = view.findViewById(R.id.latTextView);
        mLon = view.findViewById(R.id.lonTextView);
        mPublicSwitch = view.findViewById(R.id.publicSwitch);
        mPublicSwitch.setOnCheckedChangeListener(this);
        mModel = MapModel.getInstance();
        mDeleteButton = view.findViewById(R.id.deleteButton);
        mDeleteButton.setOnClickListener(this);
        Bundle b = getArguments();
        if (b != null) {
            LatLng extra = (LatLng) b.get(LAT_LON_BUNDLE);
            if (extra != null) {
                mLocation = extra;
                mLat.setText(String.format(Locale.US, "%f", extra.latitude));
                mLon.setText(String.format(Locale.US, "%f", extra.longitude));
            } else {
                setErrorToViews();
            }
        } else {
            setErrorToViews();
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.deleteButton:
                if (mLocation != null) {
                    mModel.deleteMarker(mLocation);
                    getActivity().onBackPressed(); // todo is this a hack?
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        switch (id) {
            case R.id.publicSwitch:
                publicSwitchClicked(b);
                break;
        }
    }

    private void publicSwitchClicked(boolean b) {
        if (b) {
            // turned on
            mModel.setPublic(mLocation);
        } else {
            // turned off
        }
    }
}
