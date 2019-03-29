package com.mad.petshelterfinder.savedshelters;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.Shelter;

/**
 * Interface to communicate between view and presenter
 */
interface SavedSheltersContract {
    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {
        /**
         * Send shelter object to view
         *
         * @param shelter object to display in the view
         */
        void showShelterToView(Shelter shelter);

        /**
         * Start new activity to display shelter's details
         *
         * @param shelterId unique id of shelter
         */
        void showShelterDetailsUi(String shelterId);

        /**
         * show or hide shimmer placeholder during async tasks
         *
         * @param isLoading true to show placeholder, false otherwise
         */
        void toggleLoadingUi(boolean isLoading);

        /**
         * Show message that user has no saved shelters
         */
        void showNoShelters();
    }

    /**
     * Presenter interface to send data to the model and display values to the view
     */
    interface Presenter extends BasePresenter {
        /**
         * gets the shelters's id to pass into intent
         *
         * @param shelterId shelter's id to display
         */
        void openShelterDetails(String shelterId);

        /**
         * Starts parsing Firebase database to get shelter list
         */
        void loadSheltersByAddress();
    }
}
