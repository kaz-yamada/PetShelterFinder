package com.mad.petshelterfinder.register;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;

/**
 * Interface to communicate between view and presenter
 */
public interface RegisterContract {
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
        void enableInputs(boolean show);

        /**
         * Notify view that registration has been successful
         */
        void showRegistrationSuccess();

        /**
         * Notify view that registration has failed
         *
         * @param message from Firebase on why registration failed
         */
        void showRegistrationFailure(String message);
    }

    /**
     * Presenter interface to send data to the model and display values to the view
     */
    interface Presenter extends BasePresenter {

        /**
         * Register new user with email and password
         *
         * @param email    unique id to register with
         * @param password password of the user
         */
        void attemptRegistration(String email, String password);
    }
}
