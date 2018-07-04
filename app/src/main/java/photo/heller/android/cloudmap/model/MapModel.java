package photo.heller.android.cloudmap.model;

import android.provider.ContactsContract;
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

    private final String TAG = MapModel.class.getSimpleName();

    private FirebaseDatabase mDatabase;
    private DatabaseReference mLatLng;
    private List<ModelEventListener<? extends Object>> mListeners;

    private final String LAT_LNG = "/latLng/";



    private static MapModel mInstance;


    public static MapModel getInstance() {
        if (mInstance == null) {
            mInstance = new MapModel();
        }
        return mInstance;
    }

    private MapModel() {
        mDatabase = FirebaseDatabase.getInstance();
        mLatLng = mDatabase.getReference(LAT_LNG);
        mLatLng.addValueEventListener(this);
        mListeners = new LinkedList<ModelEventListener<? extends Object>>();
    }

    public void addLatLng(CloudLatLng latLng) {
        mLatLng.push().setValue(latLng);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<LatLng> llList = new ArrayList<>();
        for(DataSnapshot each : dataSnapshot.getChildren()) {
            CloudLatLng ll = each.getValue(CloudLatLng.class);
            Log.d(TAG, "onDataChange: AEH cloudLatLong " + ll.mLat);
            Log.d(TAG, "onDataChange: AEH CloudLatLong " + ll.mLon);
            llList.add(new LatLng(ll.mLat, ll.mLon));
        }
        for(ModelEventListener each : mListeners) {
            each.onModelChange(llList);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public void addEventListener(ModelEventListener listener) {
        mListeners.add(listener);
    }
}
