package photo.heller.android.cloudmap.model.members;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CloudLatLngUnitTest {
    @Test
    public void getLatLongFromNewEmpty() {
        CloudLatLng ut = new CloudLatLng();
        assert(ut.mLat == 0);
        assert(ut.mLon == 0);

    }
    @Test
    public void getLatLongFromNewValues() {
        CloudLatLng ut = new CloudLatLng(123, 456);
        assert(ut.mLat == 123);
        assert(ut.mLon == 456);
    }

    @Test
    public void getLatLongFromLatLng() {
        LatLng ll = new LatLng(123, 456);
        CloudLatLng ut = new CloudLatLng(ll);
        assert(ut.mLat == 90);
        assert(ut.mLon == 96);
    }

}
