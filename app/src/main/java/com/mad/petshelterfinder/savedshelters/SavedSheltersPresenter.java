package com.mad.petshelterfinder.savedshelters;

import android.support.annotation.NonNull;

import com.mad.petshelterfinder.model.Shelter;
import com.mad.petshelterfinder.model.source.SheltersDataSource;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;

/**
 * Presenter class to mediate between view and model
 */
public class SavedSheltersPresenter implements SavedSheltersContract.Presenter {

    private AuthRepository mAuthRepository;
    private SavedSheltersContract.View mView;

    SavedSheltersPresenter(AuthRepository authRepository, @NonNull SavedSheltersContract.View view) {
        mAuthRepository = authRepository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadSheltersByAddress();
    }

    @Override
    public void openShelterDetails(String shelterId) {
        mView.showShelterDetailsUi(shelterId);
    }

    @Override
    public void loadSheltersByAddress() {
        mView.toggleLoadingUi(true);
        mAuthRepository.getUserFavouriteShelters(mShelterEventListener);
    }

    private SheltersDataSource.GetShelterByIdCallback mShelterEventListener = new SheltersDataSource.GetShelterByIdCallback() {
        @Override
        public void onLoaded(Shelter shelter) {
            mView.toggleLoadingUi(false);
            mView.showShelterToView(shelter);
        }

        @Override
        public void onCancelled() {
            mView.toggleLoadingUi(false);
        }

        @Override
        public void noRecords() {
            mView.showNoShelters();
        }
    };
}
