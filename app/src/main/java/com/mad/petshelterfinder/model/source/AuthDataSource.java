package com.mad.petshelterfinder.model.source;

/**
 * Interface
 */
public interface AuthDataSource {
    /**
     * update the flag on the user's favourite pets by id
     *
     * @param petId          the id of the pet to update
     * @param isInFavourites true to add pet to user's favourite, false if otherwise
     */
    void updateUserFavouritePetById(String petId, boolean isInFavourites);

    /**
     * Log out current user
     */
    void logout();

    /**
     * Check by pet id if it is favourited by the user
     *
     * @param petId    the pet to check
     * @param callback callback to handle firebase events
     */
    void isPetFavouritedByUser(String petId, final GetFavouriteStatusCallback callback);

    /**
     * Update the user's saved shelter table and add/remove shelter from that list
     *
     * @param shelterId    the shelter to update
     * @param isFavourited true if the user favourited the pet, false if not
     */
    void updateUserShelterById(String shelterId, boolean isFavourited);

    /**
     * Checks if the user saved the shelter to their list
     *
     * @param shelterId the shelter to check
     * @param callback  callback to handle firebase events
     */
    void isShelterSavedByUser(String shelterId, final GetFavouriteStatusCallback callback);

    /**
     * Callback to handle firebase events
     */
    interface GetFavouriteStatusCallback {
        /**
         * @param isFavourited true if the user favourited the pet/shelter, false if not
         */
        void onLoad(boolean isFavourited);
    }

    /**
     * Get all of the user's favourited pets
     *
     * @param callback callback to handle firebase events
     */
    void getUserFavouritePetList(final PetsDataSource.GetPetByIdCallback callback);

    /**
     * Get all of the user's favourited shelters
     *
     * @param callback callback to handle firebase events
     */
    void getUserFavouriteShelters(final SheltersDataSource.GetShelterByIdCallback callback);

    /**
     * Pass login credentials to Firebase auth
     *
     * @param email    email of the user
     * @param password password of the user
     * @param callback callback to handle async auth events
     */
    void loginUser(String email, String password, AuthCallbacks callback);

    /**
     * Callbacks that handle async Firebase Auth events
     */
    interface AuthCallbacks {
        /**
         * Called when login was successful
         */
        void onSuccess();

        /**
         * Called when login has failed
         *
         * @param message from Firebase auth on why login failed
         */
        void onFailure(String message);
    }

    /**
     * Pass registration credentials to Firebase auth to add new user
     *
     * @param email    email of the user
     * @param password password of the user
     * @param callback callback to handle async auth events
     */
    void registerUser(String email, String password, AuthCallbacks callback);
}
