package com.mad.petshelterfinder.model.source;

import com.mad.petshelterfinder.model.Pet;

import java.util.ArrayList;

/**
 * Model interface to handle Firebase database operations concerning Pets
 */
public interface PetsDataSource {

    /**
     * Callbacks for getting the pet list from firebase
     */
    interface LoadPetListCallback {
        /**
         * Callback for when a pet item has been successfully fetched from Firebase
         *
         * @param pet the fetched pet object
         */
        void onPetLoaded(Pet pet);

        /**
         * @param petId the id of the removed pet
         */
        void onPetRemoved(String petId);

        /**
         * Fires when all rows of the table have been queried
         */
        void onLoadFinished();

        /**
         * handles when query has been cancelled
         */
        void onCancelled();
    }

    /**
     * Make a request to firebase to get all pets stored in the database
     *
     * @param callback functions to handle Firebase events
     */
    void getPetList(LoadPetListCallback callback);

    /**
     * Callbacks for getting a pet from firebase by unique id
     */
    interface GetPetByIdCallback {
        /**
         * Callback for when a pet item has been successfully fetched from Firebase
         *
         * @param pet         the fetched pet object
         * @param shelterName the name of shelter of where the pet is located
         */
        void onLoaded(Pet pet, String shelterName);

        /**
         * Handles when the request has been cancelled
         */
        void onCancelled();

        /**
         * handles when no records have been returned
         */
        void noRecords();
    }

    /**
     * Make a request to firebase to get a single pet by its unique id
     *
     * @param petId    the id of the pet to get
     * @param callback functions to handle Firebase events
     */
    void getPetById(String petId, GetPetByIdCallback callback);

    /**
     *
     */
    interface GetSpeciesCallback {

        /**
         * Called when value from Firebase has been loaded
         *
         * @param speciesList all species that are in the database
         */
        void onLoaded(ArrayList<String> speciesList);
    }

    /**
     * Get all of the species in the pets table
     *
     * @param callback functions to handle Firebase events
     */
    void getSpeciesList(GetSpeciesCallback callback);

}
