package photo.heller.android.cloudmap.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
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
    private final String PUBLIC_LOCATIONS = "/publicLocations/";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mLatLng;
    private DatabaseReference mPublicLatLng;
    private List<ModelEventListener<? extends Object>> mListeners;


    private MapModel() {
        mDatabase = FirebaseDatabase.getInstance();
        mLatLng = mDatabase.getReference(FirebaseAuth.getInstance().getUid() + LAT_LNG);
        mPublicLatLng = mDatabase.getReference(PUBLIC_LOCATIONS);
        mLatLng.addValueEventListener(this);
        mPublicLatLng.addValueEventListener(this);
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

    public void addPublicLatLng(CloudLatLng xLatLng) {
        mPublicLatLng.push().setValue(xLatLng);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        updateLocations(dataSnapshot);
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
        // this entire function feels like a hack
        mLatLng.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findAndDelete(dataSnapshot, position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mPublicLatLng.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findAndDelete(dataSnapshot, position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findAndDelete(DataSnapshot dataSnapshot, LatLng position) {
        // TODO this seems ... hacky refactor
        // O(n)
        boolean found = false;
        CloudLatLng needle = new CloudLatLng(position);
        for(DataSnapshot each : dataSnapshot.getChildren()) {
            CloudLatLng ll = each.getValue(CloudLatLng.class);
            if (ll == null) continue;
            if (ll.mLat == needle.mLat && ll.mLon == needle.mLon) {
                each.getRef().removeValue();
                found = true;
                break;
            }
        }
        if (found) updateLocations(dataSnapshot);
    }

    private void updateLocations(DataSnapshot xSnapshot) {
        if (xSnapshot == null) return;
        List<CloudLatLng> list = new ArrayList<>();
        for(DataSnapshot each : xSnapshot.getChildren()) {
            if (each == null) continue;
            list.add(each.getValue(CloudLatLng.class));
        }
        for(ModelEventListener each : mListeners) {
            if (each == null) continue;
            each.onModelChange(list);
        }
    }

    public void removeListener(ModelEventListener listener) {
        mListeners.remove(listener);
    }

    public void getAllLocations() {
        List<CloudLatLng> locations = new LinkedList<>();
        // note: this needs to be nested so that we don't call
        // the listeners twice.
        mLatLng.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<CloudLatLng> list = new ArrayList<>();
                for(DataSnapshot each : dataSnapshot.getChildren()) {
                    if (each == null) continue;
                    list.add(each.getValue(CloudLatLng.class));
                }
                mPublicLatLng.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot each : dataSnapshot.getChildren()) {
                            if (each == null) continue;
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // makes a given location public
    public void setPublic(LatLng xLocation) {
        deleteMarker(xLocation);
        addPublicLatLng(new CloudLatLng(xLocation));
    }
}
