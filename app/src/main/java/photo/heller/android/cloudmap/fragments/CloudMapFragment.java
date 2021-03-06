package photo.heller.android.cloudmap.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import photo.heller.android.cloudmap.R;
import photo.heller.android.cloudmap.controllers.ActivityViewController;
import photo.heller.android.cloudmap.interfaces.ModelEventListener;
import photo.heller.android.cloudmap.model.MapModel;
import photo.heller.android.cloudmap.model.members.CloudLatLng;

public class CloudMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, ModelEventListener, GoogleMap.OnMarkerClickListener {
    private final String TAG = CloudMapFragment.class.getSimpleName();

    private MapView mMapView;
    private GoogleMap mMap;
    private ActionBar mActionBar;
    private MapModel mModel;

    public CloudMapFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity tempFrag = getActivity();
        if (tempFrag != null) {
            mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.hide();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cloud_map, container, false);
        mMapView = v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        mModel = MapModel.getInstance();
        mModel.addEventListener(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap xMap) {
        mMap = xMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mModel.getAllLocations();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActionBar.show();
        mMapView.onDestroy();
        mModel.removeListener(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String uuid = FirebaseAuth.getInstance().getUid();
        CloudLatLng ll = new CloudLatLng(latLng);
        ll.setOwner(uuid);
        ll.setPrivate(true);
        mModel.addLatLng(ll);
    }

    @Override
    public void onModelChange(List db) {
        List l;
        l = db;
        if (l == null) {
            Log.e(TAG, "onModelChange: List db == null");
            return;
        }
        mMap.clear();
        for (Object each : l) {
            LatLng e = null;
            CloudLatLng cll = null;
            if (each instanceof LatLng) {
                e = (LatLng) each;
            } else if (each instanceof CloudLatLng) {
                cll = (CloudLatLng) each;
            }
            MarkerOptions o = new MarkerOptions();
            if (cll != null) {
                o.position(new LatLng(cll.getLat(), cll.getLon()));
            } else if (e != null) {
                o.position(e);
            } else {
                Log.d(TAG, "onModelChange: casts all failed");
                continue;
            }
            mMap.addMarker(o);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle b = new Bundle();
        b.putParcelable(LocationDetailFragment.LAT_LON_BUNDLE, marker.getPosition());
        ActivityViewController.getInstance().replaceFragments(new LocationDetailFragment(), true, b);
        return true; // consume the event
    }
}
