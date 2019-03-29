package com.mad.petshelterfinder.login;

import com.mad.petshelterfinder.model.source.AuthDataSource;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;

import static com.mad.petshelterfinder.util.StringUtils.isEmailValid;
import static com.mad.petshelterfinder.util.StringUtils.isPasswordValid;

/**
 * Presenter to mediate data between view and firebase Auth helper class
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    private AuthRepository mAuthRepository;

    LoginPresenter(LoginContract.View view, AuthRepository authRepository) {
        mView = view;
        mAuthRepository = authRepository;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void attemptLogin(String email, String password) {
        boolean isValid = true;

        if (!isEmailValid(email)) {
            isValid = false;
            mView.displayEmailErrorMessage();
        }

        if (!isPasswordValid(password)) {
            isValid = false;
            mView.displayPasswordErrorMessage();
        }

        if (isValid) {
            mView.setLoginButtonProgress();
            mAuthRepository.loginUser(email, password, mAuthCallbacks);
            mView.enableInputs(false);
        }
    }

    /**
     * Callbacks to handle login events
     */
    private AuthDataSource.AuthCallbacks mAuthCallbacks = new AuthDataSource.AuthCallbacks() {
        @Override
        public void onSuccess() {
            mView.showSuccess();
        }

        @Override
        public void onFailure(String message) {
            mView.showInvalidLogin();
            mView.enableInputs(true);
        }
    };
}
