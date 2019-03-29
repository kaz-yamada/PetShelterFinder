package com.mad.petshelterfinder.shelters.sheltermap;

import android.support.annotation.NonNull;

import com.mad.petshelterfinder.model.Shelter;
import com.mad.petshelterfinder.model.source.SheltersDataSource.LoadShelterListCallback;
import com.mad.petshelterfinder.model.source.repositories.SheltersRepository;

/**
 * Presenter class to mediate between view and model
 */
public class ShelterMapPresenter implements ShelterMapContract.Presenter {

    private SheltersRepository mSheltersRepository;
    private ShelterMapContract.View mView;

    ShelterMapPresenter(@NonNull ShelterMapContract.View view, SheltersRepository repository) {
        mSheltersRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mSheltersRepository.getShelters(mShelterEventListener);
    }

    @Override
    public void openShelterDetails(String shelterId) {
        mView.showShelterDetailsUi(shelterId);
    }

    private LoadShelterListCallback mShelterEventListener = new LoadShelterListCallback() {
        @Override
        public void onSheltersLoaded(Shelter shelter) {
            mView.displayShelter(shelter);
        }

        @Override
        public void onShelterRemoved(String shelterId) {
            mView.removeShelterFromView(shelterId);
        }

        @Override
        public void onLoadFinish(int count) {
            mView.notifyLoadFinished(count);
        }

        @Override
        public void onCancelled() {

        }
    };
}
