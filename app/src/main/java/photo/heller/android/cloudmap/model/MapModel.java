package photo.heller.android.cloudmap.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import photo.heller.android.cloudmap.interfaces.ModelEventListener;
import photo.heller.android.cloudmap.model.members.CloudLatLng;

public class MapModel implements ValueEventListener {

    private static MapModel mInstance;
    private final String TAG = MapModel.class.getSimpleName();
    private final String LAT_LNG = "/latLng/";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mLatLng;
    private List<ModelEventListener<? extends Object>> mListeners;


    private MapModel() {
        mDatabase = FirebaseDatabase.getInstance();
        mLatLng = mDatabase.getReference(LAT_LNG);
        mLatLng.addValueEventListener(this);
        mListeners = new LinkedList<ModelEventListener<? extends Object>>();
    }

    public static MapModel getInstance() {
        if (mInstance == null) {
            mInstance = new MapModel();
        }
        return mInstance;
    }

    public void addLatLng(CloudLatLng latLng) {
        mLatLng.push().setValue(latLng);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<LatLng> llList = new ArrayList<>();
        for (DataSnapshot each : dataSnapshot.getChildren()) {
            CloudLatLng ll = each.getValue(CloudLatLng.class);
            if (ll == null) {
                Log.e(TAG, "onDataChange: ll == null");
                continue;
            }
            llList.add(new LatLng(ll.mLat, ll.mLon));
        }
        for (ModelEventListener each : mListeners) {
            if (each == null) continue;
            each.onModelChange(llList);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d(TAG, "onCancelled: Database txn cancelled " + databaseError);
    }

    public void addEventListener(ModelEventListener listener) {
        if (listener != null) {
            mListeners.add(listener);
        } else {
            throw new RuntimeException("Can not pass null to event listener");
        }
    }

    public void deleteMarker(final LatLng position) {
        mLatLng.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // TODO this seems ... hacky refactor
                // O(n)
                CloudLatLng needle = new CloudLatLng(position);
                for(DataSnapshot each : dataSnapshot.getChildren()) {
                    CloudLatLng ll = each.getValue(CloudLatLng.class);
                    if (ll == null) continue;
                    if (ll.mLat == needle.mLat && ll.mLon == needle.mLon) {
                        each.getRef().removeValue();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void removeListener(ModelEventListener listener) {
        mListeners.remove(listener);
    }

    public void getAllLocations() {
        mLatLng.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<CloudLatLng> list = new ArrayList<>();
                for(DataSnapshot each : dataSnapshot.getChildren()) {
                    list.add(each.getValue(CloudLatLng.class));
                }
                for(ModelEventListener each : mListeners) {
                    each.onModelChange(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
