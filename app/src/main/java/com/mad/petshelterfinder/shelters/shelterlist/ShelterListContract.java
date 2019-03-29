package com.mad.petshelterfinder.shelters.shelterlist;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.Shelter;

/**
 * Defines the contract between the view {@link ShelterListFragment} and the presenter
 * {@link ShelterListPresenter}
 */
public interface ShelterListContract {
    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {

        /**
         * pass shelter object to be displayed in the view
         *
         * @param shelter shelter object
         */
        void showShelterToView(Shelter shelter);

        /**
         * open new activity to display detailed information of the pet shelter
         *
         * @param shelterId key of the pet shelter
         */
        void showShelterDetailsUi(String shelterId);

        /**
         * remove shelter from
         *
         * @param shelterId key of the pet shelter
         */
        void removeShelterFromView(String shelterId);

        /**
         * remove all items from recycler view
         */
        void resetList();
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


        /**
         * Load the pet list from the model
         *
         * @param showLoadingUi true to display loading placeholder, false to hide placeholders
         */
        void loadSheltersByAddress(boolean showLoadingUi);
    }
}
