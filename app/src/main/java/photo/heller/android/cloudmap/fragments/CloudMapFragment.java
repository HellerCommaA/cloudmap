package photo.heller.android.cloudmap.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import photo.heller.android.cloudmap.R;
import photo.heller.android.cloudmap.interfaces.ModelEventListener;
import photo.heller.android.cloudmap.model.MapModel;
import photo.heller.android.cloudmap.model.members.CloudLatLng;

public class CloudMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, ModelEventListener {
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
        mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        mActionBar.hide();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
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
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mModel.addLatLng(new CloudLatLng(latLng));
    }

    @Override
    public void onModelChange(List db) {
        Log.d(TAG, "onModelChange: AEH onModelChange!!!!");
        List<LatLng> l;
        l = db;
        mMap.clear();
        for(LatLng each : l) {
            MarkerOptions o = new MarkerOptions();
            o.position(each);
            mMap.addMarker(o);
        }
    }
}
