package photo.heller.android.cloudmap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        // can i do this here????
        FirebaseUser current = mAuth.getCurrentUser();
        if (current == null) {
            Log.d(TAG, "onStart: NOT LOGGED IN");
        } else {
            Log.d(TAG, "onStart: username: " + current.getEmail());
        }
        // end can i do this here

        if (current == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // not logged in show login frag
            AppLogin frag = new AppLogin();
            fragmentTransaction.add(R.id.frag_container, frag);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseUser current = mAuth.getCurrentUser();
//        if (current == null) {
//            Log.d(TAG, "onStart: NOT LOGGED IN");
//        } else {
//            Log.d(TAG, "onStart: username: " + current.getEmail());
//        }
    }
}
