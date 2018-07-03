package photo.heller.android.cloudmap.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import photo.heller.android.cloudmap.R;


public class MapContainerFragment extends Fragment {
    public MapContainerFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_container, container, false);

        return view;
    }

}
