package com.mad.petshelterfinder.petdetail;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.Pet;

/**
 * Interface to communicate between view and presenter
 */
public interface PetDetailContract {
    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {
        /**
         * Pass pet object and what pet shelter they are in to the view
         *
         * @param pet         information to display
         * @param shelterName name of the shelter where the pet is or was located
         */
        void displayPetDetails(Pet pet, String shelterName);

        /**
         * Sends url to view to open browser with pet's website
         *
         * @param url the website containing the pet's info
         */
        void showPetUrl(String url);

        /**
         * Uses Picasso to get an image via url and display
         *
         * @param imageUrl url of the image
         */
        void setPetImage(String imageUrl);

        /**
         * Opens shelter details by its id
         *
         * @param shelterId the shelter to view
         */
        void showPetShelter(String shelterId);

        /**
         * adds or removes the pet from the user's favourites
         *
         * @param isInFavourites true to add to favourites, false if ot
         */
        void setFavouriteButton(boolean isInFavourites);

        /**
         * Display a message to view on favourite event was completed
         *
         * @param message whether if pet was added or removed from favourites
         */
        void showUpdateMessage(String message);

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
         * Display the pet's website
         */
        void openPetUrl();

        /**
         * Display the shelter the pet is located in
         */
        void openShelterList();

        /**
         * Toggle the pet's favourite status
         */
        void toggleFavourite();
    }
}