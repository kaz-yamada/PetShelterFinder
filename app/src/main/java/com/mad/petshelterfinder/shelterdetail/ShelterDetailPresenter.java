package com.mad.petshelterfinder.shelterdetail;

import com.mad.petshelterfinder.model.Shelter;
import com.mad.petshelterfinder.model.source.AuthDataSource;
import com.mad.petshelterfinder.model.source.SheltersDataSource;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;
import com.mad.petshelterfinder.model.source.repositories.SheltersRepository;

/**
 * Presenter class to display the shelter's details
 */
public class ShelterDetailPresenter implements ShelterDetailContract.Presenter {

    private ShelterDetailContract.View mView;
    private SheltersRepository mSheltersRepository;
    private AuthRepository mAuthRepository;
    private Shelter mShelter;
    private final String mShelterId;
    private boolean mIsSaved = false;

    ShelterDetailPresenter(String shelterId, ShelterDetailActivity shelterDetailActivity) {
        mShelterId = shelterId;
        mSheltersRepository = SheltersRepository.getInstance();
        mAuthRepository = AuthRepository.getInstance();
        mView = shelterDetailActivity;
    }

    private SheltersDataSource.GetShelterByIdCallback mGetShelterByIdCallback = new SheltersDataSource.GetShelterByIdCallback() {
        @Override
        public void onLoaded(Shelter shelter) {
            mShelter = shelter;
            mShelter.setShelterId(mShelterId);

            mView.displayShelterDetails(mShelter);
        }

        @Override
        public void onCancelled() {
        }

        @Override
        public void noRecords() {

        }
    };

    @Override
    public void start() {
        if (mAuthRepository.isLoggedIn()) {
            mView.showFavouriteButton();

            // Listener for clicking favourite button
            mAuthRepository.isShelterSavedByUser(mShelterId, new AuthDataSource.GetFavouriteStatusCallback() {
                @Override
                public void onLoad(boolean isFavourited) {
                    mIsSaved = isFavourited;
                    mView.setFavouriteButton(mIsSaved);
                }
            });
        }

        mSheltersRepository.getShelterById(mShelterId, mGetShelterByIdCallback);
    }

    @Override
    public void openShelterWebsite() {
        mView.showShelterWebsite(mShelter.getWebsite());
    }


    @Override
    public void toggleFavourite() {
        mAuthRepository.updateUserShelterById(mShelterId, !mIsSaved);

        String message = "Shelter added to favourites";
        if (mIsSaved) {
            message = "Shelter removed from favourites";
        }
        mView.showMessage(message);
    }
}
