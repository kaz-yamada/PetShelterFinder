package com.mad.petshelterfinder.register;

import com.mad.petshelterfinder.model.source.AuthDataSource;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;

import static com.mad.petshelterfinder.util.StringUtils.isEmailValid;
import static com.mad.petshelterfinder.util.StringUtils.isPasswordValid;

/**
 * Presenter class to mediate between view and model
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mView;
    private AuthRepository mAuthRepository;

    /**
     * Constructor
     *
     * @param view           UI to send data to
     * @param authRepository Firebase Auth class to send and parse data from
     */
    RegisterPresenter(RegisterContract.View view, AuthRepository authRepository) {
        mView = view;
        mAuthRepository = authRepository;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void attemptRegistration(String email, String password) {
        boolean invalid = false;

        if (!isEmailValid(email)) {
            invalid = true;
            mView.displayEmailErrorMessage();
        }

        if (!isPasswordValid(password)) {
            invalid = true;
            mView.displayPasswordErrorMessage();
        }

        if (!invalid) {
            mView.enableInputs(false);
            mAuthRepository.registerUser(email, password, mRegisterCallbacks);
        }
    }

    /**
     * Callbacks functions for Firebase Auth operations
     */
    private AuthDataSource.AuthCallbacks mRegisterCallbacks = new AuthDataSource.AuthCallbacks() {
        @Override
        public void onSuccess() {
            mView.showRegistrationSuccess();
        }

        @Override
        public void onFailure(String message) {
            mView.showRegistrationFailure(message);
            mView.enableInputs(true);
        }
    };
}
