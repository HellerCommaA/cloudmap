package photo.heller.android.cloudmap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import photo.heller.android.cloudmap.fragments.AppLoginFragment;
import photo.heller.android.cloudmap.fragments.MapContainerFragment;
import photo.heller.android.cloudmap.fragments.SignUpFragment;
import photo.heller.android.cloudmap.interfaces.FragmentFinished;

public class MainFragment extends AppCompatActivity implements FragmentFinished {

    private final String TAG = MainFragment.class.getSimpleName();
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FloatingActionButton mFab;
    AppLoginFragment mAppLoginFragment;
    MapContainerFragment mMapContainerFragment;
    SignUpFragment mSignupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        mFab = findViewById(R.id.debugFab);

        mAppLoginFragment = new AppLoginFragment();
        mMapContainerFragment = new MapContainerFragment();
        mSignupFragment = new SignUpFragment();

        // temp debug FAB
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                replaceFragments(mAppLoginFragment, false);
            }
        });
        // end DEBUG FAB

        if (mAuth.getCurrentUser() == null) {
            // not logged in show login frag
            launchFragment(mAppLoginFragment, false);
        } else {
            // loggedin show map
            launchFragment(mMapContainerFragment, false);
        }
    }

    // replaces fragment container with a given fragment
    void replaceFragments(Fragment xFragment, boolean xBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, xFragment);
        if (xBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // launches a fragment
    void launchFragment(Fragment xFragment, boolean xBackstack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (xBackstack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.frag_container, xFragment);
        fragmentTransaction.commit();
    }

    /**
     * this is called when one of our three activites finish
     * @param xFrag the fragment that we'll kill
     * @param xId R.layout id of next fragment
     */
    @Override
    public void onFragmentFinished(Fragment xFrag, Integer xId, boolean xAddToBackStack) {
        // TODO refactor to enum of known fragments so we don't hit default case
        switch(xId) {
            case R.layout.fragment_app_login:
                replaceFragments(mAppLoginFragment, xAddToBackStack);
                break;
            case R.layout.fragment_map_container:
                replaceFragments(mMapContainerFragment, xAddToBackStack);
                break;
            case R.layout.fragment_sign_up:
                replaceFragments(mSignupFragment, xAddToBackStack);
                break;
        }
    }
}
