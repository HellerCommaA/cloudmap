package photo.heller.android.cloudmap;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import photo.heller.android.cloudmap.fragments.AppLoginFragment;
import photo.heller.android.cloudmap.fragments.MapContainerFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current = mAuth.getCurrentUser();

        if (current == null) {
            // not logged in show login frag
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AppLoginFragment frag = new AppLoginFragment();
            fragmentTransaction.add(R.id.frag_container, frag);
            fragmentTransaction.commit();
            return;
        }

        // now we know user is logged in
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapContainerFragment mapContainerFragment = new MapContainerFragment();
        fragmentTransaction.add(R.id.frag_container, mapContainerFragment);
        fragmentTransaction.commit();
    }
}
