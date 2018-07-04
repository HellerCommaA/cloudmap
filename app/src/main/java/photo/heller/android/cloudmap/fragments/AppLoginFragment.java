package photo.heller.android.cloudmap.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import photo.heller.android.cloudmap.R;
import photo.heller.android.cloudmap.interfaces.FragmentFinished;

public class AppLoginFragment extends Fragment implements View.OnClickListener {
    private final String TAG = AppLoginFragment.class.getSimpleName();
    Button mRegisterButton;
    Button mSubmitButton;
    TextView mEmailText;
    TextView mPasswordText;
    FirebaseAuth mAuth;
    FragmentFinished mInterface;
    SignUpFragment mSignupFragment;

    public AppLoginFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentFinished) {
            mInterface = (FragmentFinished) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AppLoginFragmentInterface");
        }
    }

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
        mSignupFragment = new SignUpFragment();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                registerButtonClicked();
                break;
            case R.id.loginButton:
                loginButtonClicked();
                break;
        }
    }

    private void loginButtonClicked() {
        if (mEmailText.length() > 0 && mPasswordText.length() > 0) {
            // submit
            String username = mEmailText.getText().toString();
            String password = mPasswordText.getText().toString();

            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.login_ok, Toast.LENGTH_SHORT).show();
                        mInterface.onFragmentFinished(AppLoginFragment.this, R.layout.fragment_cloud_map, false);
                    } else {
                        Log.d(TAG, "onComplete: " + task.getException());
                        Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void registerButtonClicked() {
        mInterface.onFragmentFinished(this, R.layout.fragment_sign_up, true);
    }

    @Override
    public void onDetach() {
        mInterface = null;
        super.onDetach();
    }
}
