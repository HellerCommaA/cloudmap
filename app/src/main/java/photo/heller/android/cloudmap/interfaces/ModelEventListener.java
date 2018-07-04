package photo.heller.android.cloudmap.interfaces;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface ModelEventListener<T> {
    void onModelChange(List<T> db);
}
