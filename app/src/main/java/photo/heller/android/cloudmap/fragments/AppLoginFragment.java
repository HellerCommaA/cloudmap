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
import android.widget.Toast;

import com.google.android.gms.common.internal.AuthAccountRequestCreator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import photo.heller.android.cloudmap.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppLoginFragment extends Fragment implements View.OnClickListener {
    Button mRegisterButton;
    Button mSubmitButton;
    TextView mEmailText;
    TextView mPasswordText;
    FirebaseAuth mAuth;

    private final String TAG = AppLoginFragment.class.getSimpleName();

    public AppLoginFragment() { }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_login, container, false);
        mRegisterButton = view.findViewById(R.id.registerButton);
        mSubmitButton = view.findViewById(R.id.loginButton);
        mEmailText = view.findViewById(R.id.emailText);
        mPasswordText = view.findViewById(R.id.passwordText);
        mRegisterButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
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
//          intentionally missing
//             default:
//                break;
        }
    }

    private void loginButtonClicked(View view) {
        if (mEmailText.length() > 0 && mPasswordText.length() > 0) {
            // submit
            String username = mEmailText.getText().toString();
            String password = mPasswordText.getText().toString();

            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.login_ok, Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        Log.d(TAG, "onComplete: " + task.getException());
                        Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
