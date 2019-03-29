package com.mad.petshelterfinder.filterpets;

import com.mad.petshelterfinder.BasePresenter;
import com.mad.petshelterfinder.BaseView;
import com.mad.petshelterfinder.model.PetStatusOptions;

import java.util.ArrayList;

/**
 * Defines the contract between the view {@link FilterPetsFragment} and the presenter
 * {@link FilterPetsPresenter}
 */
interface FilterPetsContract {
    /**
     * User interface and triggers events
     */
    interface View extends BaseView<Presenter> {
        /**
         * Returns a list of species to filter in the pet list
         *
         * @param statusFilter the statuses of the pets to show
         * @param speciesFilter a list of pet species that will be shown
         */
        void returnFilterResults(ArrayList<PetStatusOptions> statusFilter, ArrayList<String> speciesFilter);

        /**
         * Passes list of pet species to presenter
         *
         * @param speciesNames list of all pet species
         */
        void displaySpeciesList(ArrayList<String> speciesNames);
    }

    /**
     * Presenter interface to send data to the model and display values to the view
     */
    interface Presenter extends BasePresenter {
        void loadPetSpecies();
    }
}
