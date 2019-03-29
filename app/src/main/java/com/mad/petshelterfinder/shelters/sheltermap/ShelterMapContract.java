package com.mad.petshelterfinder.shelters.sheltermap;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.Shelter;


/**
 * Defines the contract between the view {@link ShelterMapFragment} and the presenter
 * {@link ShelterMapPresenter}
 */
interface ShelterMapContract {
    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {
        /**
         * display shelter object for use in the view
         *
         * @param shelter object to display
         */
        void displayShelter(Shelter shelter);

        /**
         * open new activity to display detailed information of the pet shelter
         *
         * @param shelterId key of the pet shelter
         */
        void showShelterDetailsUi(String shelterId);

        /**
         * @param shelterId
         */
        void removeShelterFromView(String shelterId);

        /**
         * Inform the view that async loading has finished
         *
         * @param count total amount of shelters that were parsed from the database
         */
        void notifyLoadFinished(int count);
    }

    /**
     * Presenter interface to send data to the model and display values to the view
     */
    interface Presenter extends BasePresenter {
        /**
         * Get shelter details by id
         *
         * @param shelterId key of the pet shelter
         */
        void openShelterDetails(String shelterId);
    }
}
