package com.mad.petshelterfinder.favourtiedpets;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.Pet;

/**
 * Defines the contract between the view {@link FavouritedPetsFragment} and the presenter
 * {@link FavouritedPetsPresenter}
 */
public interface FavouritedPetsContract {
    interface View extends BaseView<Presenter> {
        /**
         * Pass a pet object to the view to be displayed
         *
         * @param pet the object to pass to the view
         */
        void displayPet(Pet pet);

        /**
         * Starts an activity to display detailed information of the pet
         *
         * @param key the identifier key of the pet
         */
        void showPetDetailsUi(String key);

        /**
         * Clear out all items on the list (just client side, i.e. not remove them from database)
         */
        void resetList();

        /**
         * Display 'no records found' in view
         */
        void showNoRecords();
    }

    /**
     * Presenter interface to send data to the model and display values to the view
     */
    interface Presenter extends BasePresenter {
    }
}
