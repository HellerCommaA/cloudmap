package photo.heller.android.cloudmap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import photo.heller.android.cloudmap.fragments.AppLoginFragment;
import photo.heller.android.cloudmap.fragments.MapContainerFragment;
import photo.heller.android.cloudmap.interfaces.ActivityFinished;

public class MainActivity extends AppCompatActivity implements ActivityFinished {

    private final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        mFab = findViewById(R.id.debugFab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                loginFragment();
            }
        });

        if (mAuth.getCurrentUser() == null) {
            // not logged in show login frag
            loginFragment();
        } else {
            // loggedin show map
            loggedInMapFragment();
        }
    }

    private void loginFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AppLoginFragment frag = new AppLoginFragment();
        fragmentTransaction.add(R.id.frag_container, frag);
        fragmentTransaction.commit();
    }

    private void loggedInMapFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapContainerFragment mapContainerFragment = new MapContainerFragment();
        fragmentTransaction.add(R.id.frag_container, mapContainerFragment);
        fragmentTransaction.commit();
    }

    /**
     * this is called when one of our two activites finish
     * @param xId R.layout id
     */
    @Override
    public void activityFinished(int xId) {
        switch(xId) {
            case R.layout.fragment_app_login:
                loggedInMapFragment();
                break;
            case R.layout.fragment_sign_up:
                loginFragment();
                break;
        }
    }
}
