package com.mad.petshelterfinder.model.source;

import android.support.annotation.NonNull;

import com.mad.petshelterfinder.model.Shelter;

/**
 * Model interface to handle Firebase database operations concerning Pets
 */
public interface SheltersDataSource {

    /**
     * Fetch all items from the shelters table in the database
     *
     * @param callback functions to handle Firebase events
     */
    void getShelters(@NonNull LoadShelterListCallback callback);

    /**
     * Callbacks for getting the shelter list from firebase
     */
    interface LoadShelterListCallback {
        /**
         * Callback for when a shelter item has been successfully fetched from Firebase
         *
         * @param shelter the fetched shelter object
         */
        void onSheltersLoaded(Shelter shelter);

        /**
         * @param shelterId the id of the removed shelter
         */
        void onShelterRemoved(String shelterId);

        /**
         * Fires when all rows of the table have been queried
         */
        void onLoadFinish(int count);

        /**
         * handles when query has been cancelled
         */
        void onCancelled();
    }

    /**
     * Callbacks a single shelter item by its key
     */
    interface GetShelterByIdCallback {
        /**
         * Callback for when a shelter item has been successfully fetched from Firebase
         *
         * @param shelter the fetched shelter object
         */
        void onLoaded(Shelter shelter);

        /**
         * handles when query has been cancelled
         */
        void onCancelled();

        /**
         * handles when no records have been returned
         */
        void noRecords();
    }

    /**
     * Make a request to firebase to get a single shelter by its unique id
     *
     * @param shelterId the id of the pet to get
     * @param callback  functions to handle Firebase events
     */
    void getShelterById(String shelterId, GetShelterByIdCallback callback);
}
