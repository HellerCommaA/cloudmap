package photo.heller.android.cloudmap;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import photo.heller.android.cloudmap.fragments.AppLoginFragment;

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
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // not logged in show login frag
            AppLoginFragment frag = new AppLoginFragment();
            fragmentTransaction.add(R.id.frag_container, frag);
            fragmentTransaction.commit();
            return;
        }
        // now we know user is logged in
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
