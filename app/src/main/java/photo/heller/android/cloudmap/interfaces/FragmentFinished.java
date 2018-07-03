package photo.heller.android.cloudmap.interfaces;

import android.support.v4.app.Fragment;

public interface FragmentFinished {
    void onFragmentFinished(Fragment xFrag, Integer xNextFrag, boolean xAddToBackStack);
}
