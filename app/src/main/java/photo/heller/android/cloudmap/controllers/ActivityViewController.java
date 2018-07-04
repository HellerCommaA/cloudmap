package photo.heller.android.cloudmap.controllers;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;

import photo.heller.android.cloudmap.R;

public class ActivityViewController {

    private static ActivityViewController mInstance;
    private AppCompatActivity mActivity;

    private ActivityViewController() {

    }


    public static ActivityViewController getInstance() {
        if (mInstance == null) {
            mInstance = new ActivityViewController();
        }
        return mInstance;
    }

    public void setActivity(@NonNull AppCompatActivity xActivity) {
        mActivity = xActivity;
    }

    // replaces fragment container with a given fragment
    public void replaceFragments(Fragment xFragment, boolean xBackStack) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, xFragment);
        if (xBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
