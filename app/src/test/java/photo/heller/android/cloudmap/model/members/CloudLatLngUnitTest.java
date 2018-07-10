package photo.heller.android.cloudmap.model.members;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CloudLatLngUnitTest {
    @Test
    public void getLatLongFromNewEmpty() {
        CloudLatLng ut = new CloudLatLng();
        assert(ut.getLat() == 0);
        assert(ut.getLon() == 0);

    }
    @Test
    public void getLatLongFromNewValues() {
        CloudLatLng ut = new CloudLatLng(123, 456);
        assert(ut.getLat() == 123);
        assert(ut.getLon() == 456);
    }

    @Test
    public void getLatLongFromLatLng() {
        LatLng ll = new LatLng(123, 456);
        CloudLatLng ut = new CloudLatLng(ll);
        assert(ut.getLat() == 90);
        assert(ut.getLon() == 96);
    }

    @Test
    public void checkPrivateDefaultState() {
        CloudLatLng ut = new CloudLatLng();
        assert(ut.isPrivate());
    }

}
