package com.mad.petshelterfinder.petlist;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.model.PetStatusOptions;

import java.util.ArrayList;

/**
 * Defines the contract between the view {@link PetListFragment} and the presenter
 * {@link PetListPresenter}
 */
public interface PetListContract {
    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {
        /**
         * Start a new activity based on the pet id
         *
         * @param petId the id of the pet to view
         */
        void showPetDetailsUi(String petId);

        /**
         * Pass a pet object to the view to be displayed
         *
         * @param pet the object to pass to the view
         */
        void displayPet(Pet pet);

        /**
         * Show the UI to filter the list of pets
         */
        void showFilterMenu();

        /**
         * Display a message to the user that filter has been applied
         */
        void showSuccessfullyFilteredMessage();

        /**
         * Display loading indicator in the ui
         */
        void setLoadingUi();

        /**
         * Reset the recycler view list
         */
        void resetList();

        /**
         * Remove pet item from the view by id
         *
         * @param petId uniquie id of pet to remove
         */
        void removePetFromList(String petId);
    }

    /**
     * Presenter interface to send data to the model and display values to the view
     */
    interface Presenter extends BasePresenter {

        /**
         * Load the pet list from the model
         *
         * @param showLoadingUi true
         */
        void loadPetList(boolean showLoadingUi);

        /**
         * Open an activity to view the details of the selected pet
         *
         * @param petId the id of the pet
         */
        void openPetDetails(String petId);

        /**
         * Apply the filter options for the pet list
         *
         * @param speciesFilter a list of pet species to display to the user
         * @param statusFilter  list of statuses to display
         */
        void setFiltering(ArrayList<PetStatusOptions> statusFilter, ArrayList<String> speciesFilter);

        /**
         * Runs after activity returns a result
         *
         * @param requestCode the request code
         * @param resultCode  the result code
         */
        void result(int requestCode, int resultCode);

        /**
         * returns the list of all know pet species in database
         *
         * @return list of all pet species
         */
        ArrayList<String> getDefaultSpeciesFilter();
    }

}
