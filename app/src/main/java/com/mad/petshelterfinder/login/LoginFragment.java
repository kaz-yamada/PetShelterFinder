package com.mad.petshelterfinder.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.petlist.PetListActivity;
import com.mad.petshelterfinder.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Display a login form
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    private static final String SUCCESS_MESSAGE = "Registration successful";

    private LoginContract.Presenter mPresenter;

    @BindView(R.id.email)
    AutoCompleteTextView mEmailTv;

    @BindView(R.id.password)
    EditText mPasswordEt;

    @BindView(R.id.login_error_text_view)
    TextView mErrorTv;

    @BindView(R.id.sign_in_button)
    ActionProcessButton mLoginButton;

    @BindView(R.id.register_button)
    ActionProcessButton mRegisterButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LoginFragment() {
    }

    /**
     * Create and return a new instance of the fragment class
     *
     * @return new instance of fragment
     */
    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, root);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.attemptLogin(mEmailTv.getText().toString(), mPasswordEt.getText().toString());
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivityForResult(intent, RegisterActivity.REQUEST_REGISTER_USER);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegisterActivity.REQUEST_REGISTER_USER && resultCode == Activity.RESULT_OK) {
            if (getView() != null) {
                Snackbar.make(getView(), SUCCESS_MESSAGE, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayEmailErrorMessage() {
        mEmailTv.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void displayPasswordErrorMessage() {
        mPasswordEt.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void enableInputs(boolean show) {
        mEmailTv.setEnabled(show);
        mEmailTv.setFocusable(show);
        mEmailTv.setFocusableInTouchMode(show);

        mPasswordEt.setEnabled(show);
        mPasswordEt.setFocusable(show);
        mPasswordEt.setFocusableInTouchMode(show);

        int progress = 0;
        String message = getString(R.string.action_sign_in_short);
        if (!show) {
            progress = 1;
            message = getString(R.string.loading);

        }

        mLoginButton.setText(message);
        mLoginButton.setProgress(progress);
        mLoginButton.setEnabled(show);

        mRegisterButton.setEnabled(show);
    }

    @Override
    public void showInvalidLogin() {
        mErrorTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccess() {
        mErrorTv.setVisibility(View.GONE);
        enableInputs(false);

        Intent intent = new Intent(getContext(), PetListActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void setLoginButtonProgress() {
        mLoginButton.setText(getString(R.string.loading));
    }
}
