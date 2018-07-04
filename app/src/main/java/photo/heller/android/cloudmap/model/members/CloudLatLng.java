package photo.heller.android.cloudmap.model.members;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class CloudLatLng {
    public double mLat;
    public double mLon;

    public CloudLatLng() {

    }

    public CloudLatLng(LatLng xLatLng) {
        mLat = xLatLng.latitude;
        mLon = xLatLng.longitude;
    }

    public CloudLatLng(double lat, double lon) {
        mLat = lat;
        mLon = lon;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%f,%f", mLat, mLon);
    }
}
