package photo.heller.android.cloudmap;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SignUpFragment extends Fragment implements View.OnClickListener{

    private final String TAG = SignUpFragment.class.getSimpleName();
    private Button mRegisterButton;
    private TextView mPasswordTextView;
    private TextView mPasswordConfirmTextView;
    private TextView mEmailTextView;

    public SignUpFragment() {
        // Required empty public constructor
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
        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: AEH register button clicked");
    }
}
