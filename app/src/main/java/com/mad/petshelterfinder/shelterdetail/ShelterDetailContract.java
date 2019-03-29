package com.mad.petshelterfinder.shelterdetail;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.Shelter;


/**
 * Interface to communicate between view and presenter
 */
interface ShelterDetailContract {
    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {
        /**
         * pass shelter object to be displayed in the view
         *
         * @param shelter shelter object
         */
        void displayShelterDetails(Shelter shelter);

        /**
         * opens url in browser
         *
         * @param url url of pet shelter website
         */
        void showShelterWebsite(String url);

        /**
         * message to display when shelter is saved by user
         *
         * @param message whether if shelter was added or removed from favourites
         */
        void showMessage(String message);

        /**
         * adds or removes the shelter from the user's favourites
         *
         * @param isInFavourites true to add to favourites, false if ot
         */
        void setFavouriteButton(boolean isInFavourites);

        /**
         * shows the favourite button if the user is logged in, hide if not
         */
        void showFavouriteButton();
    }

    /**
     * Presenter interface to mediate communication between view and model
     */
    interface Presenter extends BasePresenter {
        /**
         * Open browser to display the shelter's webpage
         */
        void openShelterWebsite();

        /**
         * Toggle the shelter's favourite status
         */
        void toggleFavourite();
    }
}
