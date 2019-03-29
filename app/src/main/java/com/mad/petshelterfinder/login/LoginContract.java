package com.mad.petshelterfinder.login;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;

/**
 * Interface to communicate between view and presenter
 */
interface LoginContract {

    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {
        /**
         * Send message to view that entered email is invalid
         */
        void displayEmailErrorMessage();

        /**
         * Send message to view that entered password is invalid
         */
        void displayPasswordErrorMessage();

        /**
         * enable or disable buttons during asynchronous tasks
         *
         * @param show true to enable user to interact with view inputs, false otherwise
         */
        void enableInputs(final boolean show);

        /**
         * Inform view that Firebase states that login credentials are invalid
         */
        void showInvalidLogin();

        /**
         * Executes when login was successful
         */
        void showSuccess();

        /**
         * Set the login button to display loading message
         */
        void setLoginButtonProgress();
    }

    /**
     * Presenter interface to mediate communication between view and model
     */
    interface Presenter extends BasePresenter {
        /**
         * Attempt login via Firebase Auth
         *
         * @param email    user email
         * @param password user password
         */
        void attemptLogin(String email, String password);
    }
}
