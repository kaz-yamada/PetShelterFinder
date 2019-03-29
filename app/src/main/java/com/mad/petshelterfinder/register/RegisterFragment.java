package com.mad.petshelterfinder.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.mad.petshelterfinder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View component of registration activity
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;

    @BindView(R.id.email_text_view)
    EditText mEmailEt;

    @BindView(R.id.password_text_view)
    EditText mPasswordEt;

    @BindView(R.id.password_confirm_text_view)
    EditText mPasswordConfirmEt;

    @BindView(R.id.register_button)
    ActionProcessButton mRegisterButton;

    @BindView(R.id.register_error_text_view)
    TextView mErrorTv;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RegisterFragment() {
    }

    /**
     * Create new instance of fragment
     *
     * @return new instance of fragment
     */
    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        ButterKnife.bind(this, root);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPasswordEt.getText().toString().equals(mPasswordConfirmEt.getText().toString())) {
                    mPresenter.attemptRegistration(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
                } else {
                    mPasswordConfirmEt.setError(getString(R.string.error_confirm_password));
                }
            }
        });

        return root;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayEmailErrorMessage() {
        mEmailEt.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void displayPasswordErrorMessage() {
        mPasswordEt.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void enableInputs(boolean show) {
        mEmailEt.setEnabled(show);
        mEmailEt.setFocusable(show);
        mEmailEt.setFocusableInTouchMode(show);

        mPasswordEt.setEnabled(show);
        mPasswordEt.setFocusable(show);
        mPasswordEt.setFocusableInTouchMode(show);

        mPasswordConfirmEt.setEnabled(show);
        mPasswordConfirmEt.setFocusable(show);
        mPasswordConfirmEt.setFocusableInTouchMode(show);

        int progress = 0;
        String message = getString(R.string.register);
        if (!show) {
            progress = 1;
            message = getString(R.string.loading);
        }

        mRegisterButton.setText(message);
        mRegisterButton.setProgress(progress);
        mRegisterButton.setEnabled(show);
    }

    @Override
    public void showRegistrationSuccess() {
        FragmentActivity activity = getActivity();

        if (activity != null) {
            Intent intent = new Intent();
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }
    }

    @Override
    public void showRegistrationFailure(String message) {
        mErrorTv.setVisibility(View.VISIBLE);
        mErrorTv.setText(message);
    }
}
