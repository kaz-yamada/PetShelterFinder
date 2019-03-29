package com.mad.petshelterfinder.petdetail;

import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.model.source.AuthDataSource;
import com.mad.petshelterfinder.model.source.PetsDataSource;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;
import com.mad.petshelterfinder.model.source.repositories.PetsRepository;

/**
 * Presenter class to display the pet's details
 */
class PetDetailPresenter implements PetDetailContract.Presenter {
    private PetDetailContract.View mView;
    private AuthRepository mAuthRepository;
    private PetsRepository mPetsRepository;
    private String mPetId;
    private Pet mPet;
    private boolean mIsInFavourites = false;

    /**
     * Constructor
     *
     * @param petId          the id of the pet to view
     * @param petsRepository database object to parse data from
     * @param view           the view to send data to
     */
    PetDetailPresenter(
            String petId,
            PetsRepository petsRepository,
            AuthRepository authRepository,
            PetDetailContract.View view) {
        mPetId = petId;
        mPetsRepository = petsRepository;
        mAuthRepository = authRepository;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mAuthRepository.isLoggedIn()) {
            mView.showFavouriteButton();

            // Listener for clicking favourite button
            mAuthRepository.isPetFavouritedByUser(mPetId, new AuthDataSource.GetFavouriteStatusCallback() {
                @Override
                public void onLoad(boolean isFavourited) {
                    mIsInFavourites = isFavourited;
                    mView.setFavouriteButton(mIsInFavourites);
                }
            });
        }

        mPetsRepository.getPetById(mPetId, new PetsDataSource.GetPetByIdCallback() {
            @Override
            public void onLoaded(Pet pet, String shelterName) {
                mPet = pet;
                mPet.setPetId(mPetId);

                mView.displayPetDetails(mPet, shelterName);
                mView.setPetImage(mPet.getImageUrl());
            }

            @Override
            public void onCancelled() {

            }

            @Override
            public void noRecords() {
            }
        });
    }

    @Override
    public void openPetUrl() {
        mView.showPetUrl(mPet.getUrl());
    }

    @Override
    public void openShelterList() {
        mView.showPetShelter(mPet.getShelterId());
    }

    @Override
    public void toggleFavourite() {
        mAuthRepository.updateUserFavouritePetById(mPetId, !mIsInFavourites);

        String message = "Pet added to favourites";
        if (mIsInFavourites) {
            message = "Pet removed from favourites";
        }
        mView.showUpdateMessage(message);
    }
}
