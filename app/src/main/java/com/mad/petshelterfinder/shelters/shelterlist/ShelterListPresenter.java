package com.mad.petshelterfinder.shelters.shelterlist;

import android.support.annotation.NonNull;

import com.mad.petshelterfinder.model.Shelter;
import com.mad.petshelterfinder.model.source.SheltersDataSource.LoadShelterListCallback;
import com.mad.petshelterfinder.model.source.repositories.SheltersRepository;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class ShelterListPresenter implements ShelterListContract.Presenter {
    private SheltersRepository mSheltersRepository;
    private ShelterListContract.View mView;

    public ShelterListPresenter(@NonNull ShelterListContract.View view, SheltersRepository repository) {
        mSheltersRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadSheltersByAddress(false);
    }

    @Override
    public void openShelterDetails(String shelterId) {
        mView.showShelterDetailsUi(shelterId);
    }

    @Override
    public void loadSheltersByAddress(boolean showLoadingUi) {
        mView.resetList();
        mSheltersRepository.getShelters(mShelterEventListener);
    }

    private LoadShelterListCallback mShelterEventListener = new LoadShelterListCallback() {
        @Override
        public void onSheltersLoaded(Shelter shelter) {
            mView.showShelterToView(shelter);
        }

        @Override
        public void onShelterRemoved(String shelterId) {
            mView.removeShelterFromView(shelterId);
        }

        @Override
        public void onLoadFinish(int count) {

        }

        @Override
        public void onCancelled() {

        }
    };
}
