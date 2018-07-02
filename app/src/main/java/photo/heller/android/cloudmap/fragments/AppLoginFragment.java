package photo.heller.android.cloudmap.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import photo.heller.android.cloudmap.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppLoginFragment extends Fragment implements View.OnClickListener {
    Button mRegisterButton;
    Button mSubmitButton;
    TextView mEmailText;
    TextView mPasswordText;
    private final String TAG = AppLoginFragment.class.getSimpleName();

    public AppLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_login, container, false);
        mRegisterButton = view.findViewById(R.id.registerButton);
        mSubmitButton = view.findViewById(R.id.loginButton);
        mEmailText = view.findViewById(R.id.emailText);
        mPasswordText = view.findViewById(R.id.passwordText);        

        mRegisterButton.setOnClickListener(this);

        mSubmitButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.registerButton:
                registerButtonClicked(view);
                break;
            case R.id.loginButton:
                loginButtonClicked(view);
                break;

        }
    }

    private void loginButtonClicked(View view) {
        if (mEmailText.length() > 0 && mPasswordText.length() > 0) {
            // submit
        }        
    }

    private void registerButtonClicked(View view) {
        Log.d(TAG, "registerButtonClicked: AEH: registerButtonClicked");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        SignUpFragment frag = new SignUpFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frag_container, frag).commit();
    }

}
