package com.mad.petshelterfinder.favourtiedpets;

import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.model.source.PetsDataSource;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class FavouritedPetsPresenter implements FavouritedPetsContract.Presenter {
    private final AuthRepository mAuthRepository;
    private FavouritedPetsContract.View mView;

    FavouritedPetsPresenter(AuthRepository authRepository, FavouritedPetsContract.View view) {
        mAuthRepository = authRepository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.resetList();
        mAuthRepository.getUserFavouritePetList(new PetsDataSource.GetPetByIdCallback() {
            @Override
            public void onLoaded(Pet pet, String shelterName) {
                mView.displayPet(pet);
            }

            @Override
            public void onCancelled() {

            }

            @Override
            public void noRecords() {
                mView.showNoRecords();
            }
        });
    }
}
