package photo.heller.android.cloudmap.model.members;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class CloudLatLng {

    private double mLat;
    private double mLon;
    private boolean mIsPrivate = true;
    private String mOwner = "";

    public CloudLatLng() {

    }

    public void setOwner(String xOwner) {
        mOwner = xOwner;
    }

    public String getOwner() {
        return mOwner;
    }

    public boolean isPrivate() {
        return mIsPrivate;
    }

    public void setPrivate(boolean xPrivate) {
        mIsPrivate = xPrivate;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double xLat) {
        mLat = xLat;
    }

    public double getLon() {
        return mLon;
    }

    public void setLon(double xLon) {
        mLon = xLon;
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
