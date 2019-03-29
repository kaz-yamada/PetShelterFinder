package com.mad.petshelterfinder.filterpets;

import com.mad.petshelterfinder.model.source.PetsDataSource;
import com.mad.petshelterfinder.model.source.repositories.PetsRepository;

import java.util.ArrayList;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class FilterPetsPresenter implements FilterPetsContract.Presenter {

    private FilterPetsContract.View mView;
    private final PetsRepository mPetsRepository;
    private ArrayList<String> mDefaultSpeciesList;

    FilterPetsPresenter(ArrayList<String> speciesList, FilterPetsContract.View view, PetsRepository petsRepository) {
        mView = view;
        mPetsRepository = petsRepository;
        mDefaultSpeciesList = speciesList;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPetSpecies();
    }

    @Override
    public void loadPetSpecies() {
        if (mDefaultSpeciesList == null) {
            mPetsRepository.getSpeciesList(new PetsDataSource.GetSpeciesCallback() {
                @Override
                public void onLoaded(ArrayList<String> speciesList) {
                    mView.displaySpeciesList(speciesList);
                }
            });
        } else {
            mView.displaySpeciesList(mDefaultSpeciesList);
        }
    }
}
