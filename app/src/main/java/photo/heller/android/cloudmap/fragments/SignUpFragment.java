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

public class SignUpFragment extends Fragment implements View.OnClickListener{

    private final String TAG = SignUpFragment.class.getSimpleName();
    private Button mRegisterButton;
    private TextView mPasswordTextView;
    private TextView mPasswordConfirmTextView;
    private TextView mEmailTextView;
    private FirebaseAuth mAuth;
    private FragmentFinished mCallback;

    public SignUpFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentFinished) {
            mCallback = (FragmentFinished) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentFinished");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mRegisterButton = view.findViewById(R.id.signupRegisterButton);
        mPasswordTextView = view.findViewById(R.id.signupPasswordText);
        mPasswordConfirmTextView = view.findViewById(R.id.signupPasswordTextConfirm);
        mEmailTextView = view.findViewById(R.id.signupEmailText);
        mRegisterButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onClick(View view) {
        String password = mPasswordTextView.getText().toString();
        String confirmPw = mPasswordConfirmTextView.getText().toString();
        String username = mEmailTextView.getText().toString();
        if (password.length() > 0 && confirmPw.length() > 0 && username.length() > 0) {
            if (password.equals(confirmPw)) {
                mAuth.createUserWithEmailAndPassword(mEmailTextView.getText().toString(), mPasswordTextView.getText().toString()).
                    addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: successfully registered in");
                                Toast.makeText(getActivity(), R.string.register_ok_may_login, Toast.LENGTH_SHORT).show();
                                mCallback.onFragmentFinished(SignUpFragment.this, R.layout.fragment_app_login, false);
                            } else {
                                Log.d(TAG, "onComplete: did not successfully register");
                                Toast.makeText(getActivity(), R.string.register_failed, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: " + task.getException());
                            }
                        }
                    }
                );
            }
        }
    }
}
