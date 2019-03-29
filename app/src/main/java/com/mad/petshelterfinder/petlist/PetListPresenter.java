package com.mad.petshelterfinder.petlist;

import android.app.Activity;

import com.mad.petshelterfinder.filterpets.FilterPetsActivity;
import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.model.PetStatusOptions;
import com.mad.petshelterfinder.model.source.PetsDataSource;
import com.mad.petshelterfinder.model.source.repositories.PetsRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mad.petshelterfinder.model.PetStatusOptions.ADOPTED;
import static com.mad.petshelterfinder.model.PetStatusOptions.AVAILABLE;
import static com.mad.petshelterfinder.model.PetStatusOptions.FOSTER_CARE;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class PetListPresenter implements PetListContract.Presenter {
    private PetListContract.View mView;
    private final PetsRepository mPetsRepository;

    private ArrayList<String> mSpeciesFilter;
    private ArrayList<PetStatusOptions> mStatusFilter;
    private ArrayList<String> mDefaultSpeciesFilter;

    private boolean mFirstLoad = true;

    /**
     * Constructor
     *
     * @param petsRepository database model class to fetch pets from
     * @param view           the UI component
     */
    PetListPresenter(PetsRepository petsRepository, PetListContract.View view) {
        mPetsRepository = petsRepository;
        mView = view;

        mView.setPresenter(this);
        mSpeciesFilter = new ArrayList<>();
        mDefaultSpeciesFilter = new ArrayList<>();
        mStatusFilter = new ArrayList<>(Arrays.asList(AVAILABLE, FOSTER_CARE, ADOPTED));
    }

    @Override
    public void start() {
        loadPetList(false);
    }

    @Override
    public void loadPetList(boolean showLoadingUi) {
        if (showLoadingUi) {
            mView.setLoadingUi();
        }
        mView.resetList();
        mPetsRepository.getPetList(mLoadPetListCallback);
    }

    @Override
    public void openPetDetails(String petId) {
        mView.showPetDetailsUi(petId);
    }

    @Override
    public void setFiltering(ArrayList<PetStatusOptions> statusFilter, ArrayList<String> speciesFilter) {
        mSpeciesFilter = speciesFilter;
        mStatusFilter = statusFilter;
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (FilterPetsActivity.REQUEST_FILTER_PETS == requestCode && Activity.RESULT_OK == resultCode) {
            mView.showSuccessfullyFilteredMessage();
        }
    }

    @Override
    public ArrayList<String> getDefaultSpeciesFilter() {
        return mDefaultSpeciesFilter;
    }

    /**
     * Callback functions to handle firebase events
     */
    private PetsDataSource.LoadPetListCallback mLoadPetListCallback = new PetsDataSource.LoadPetListCallback() {
        @Override
        public void onPetLoaded(Pet pet) {
            if (mFirstLoad) {
                mView.displayPet(pet);

                if (!mDefaultSpeciesFilter.contains(pet.getSpecies())) {
                    mDefaultSpeciesFilter.add(pet.getSpecies());
                }

                mSpeciesFilter = mDefaultSpeciesFilter;
                return;
            }

            if (mSpeciesFilter.contains(pet.getSpecies()) && checkStatusFilter(pet.getStatus())) {
                mView.displayPet(pet);
            }
        }

        @Override
        public void onPetRemoved(String petId) {
            mView.removePetFromList(petId);
        }

        @Override
        public void onLoadFinished() {
            mFirstLoad = false;
        }

        @Override
        public void onCancelled() {

        }
    };

    /**
     * Check if the pet to show is one of the filtered
     *
     * @param petStatus the status of the pet to check
     * @return if the pet is to be filtered out or not
     */
    private boolean checkStatusFilter(String petStatus) {
        PetStatusOptions option;

        switch (petStatus.toLowerCase()) {
            case "available":
                option = AVAILABLE;
                break;
            case "foster":
                option = FOSTER_CARE;
                break;
            case "adopted":
                option = ADOPTED;
                break;
            default:
                return false;
        }

        return mStatusFilter.contains(option);
    }
}
