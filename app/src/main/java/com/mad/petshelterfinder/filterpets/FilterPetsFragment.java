package com.mad.petshelterfinder.filterpets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.adapters.SpeciesListAdapter;
import com.mad.petshelterfinder.model.PetStatusOptions;
import com.mad.petshelterfinder.petlist.PetListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mad.petshelterfinder.model.PetStatusOptions.ADOPTED;
import static com.mad.petshelterfinder.model.PetStatusOptions.AVAILABLE;
import static com.mad.petshelterfinder.model.PetStatusOptions.FOSTER_CARE;


/**
 * Fragment to display form to filter pets
 */
public class FilterPetsFragment extends Fragment implements FilterPetsContract.View, View.OnClickListener {
    private FilterPetsContract.Presenter mPresenter;

    @BindView(R.id.filter_available_checkbox)
    CheckBox mAvailableCb;

    @BindView(R.id.filter_foster_checkbox)
    CheckBox mFosterCb;

    @BindView(R.id.filter_adopted_checkbox)
    CheckBox mAdoptedCb;

    @BindView(R.id.filter_apply_button)
    Button mApplyBtn;

    @BindView(R.id.filter_reset_button)
    Button mResetBtn;

    private SpeciesListAdapter mAdapter;
    private static final String ARGUMENT_SPECIES_LIST = "SPECIES_LIST";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FilterPetsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param speciesList the list of species that exist.
     * @return A new instance of fragment FilterPetsFragment.
     */
    public static FilterPetsFragment getInstance(ArrayList<String> speciesList) {
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(ARGUMENT_SPECIES_LIST, speciesList);
        FilterPetsFragment fragment = new FilterPetsFragment();
        fragment.setArguments(arguments);

        return new FilterPetsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_filter_pets, container, false);

        ButterKnife.bind(this, root);

        mApplyBtn.setOnClickListener(this);
        mResetBtn.setOnClickListener(this);

        // Set up recycler view
        mAdapter = new SpeciesListAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = root.findViewById(R.id.species_recycler_view);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_apply_button: {
                getCheckedOptions();
                break;
            }
            case R.id.filter_reset_button: {
                mAvailableCb.setChecked(true);
                mFosterCb.setChecked(true);
                mAdoptedCb.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void setPresenter(FilterPetsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void returnFilterResults(ArrayList<PetStatusOptions> statusFilter, ArrayList<String> speciesFilter) {
        FragmentActivity activity = getActivity();

        if (activity != null) {
            Intent intent = new Intent();

            intent.putExtra(FilterPetsActivity.FILTER_SPECIES_KEY, speciesFilter);
            intent.putExtra(FilterPetsActivity.FILTER_STATUS_KEY, statusFilter);

            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }
    }

    @Override
    public void displaySpeciesList(ArrayList<String> speciesName) {
        for (String species : speciesName) {
            mAdapter.addToList(species);
        }
    }

    /**
     * Apply filters to send to {@link PetListActivity}
     */
    private void getCheckedOptions() {
        // Species filter
        ArrayList<String> speciesFilters = mAdapter.getCheckedItems();

        // Status filter
        ArrayList<PetStatusOptions> statusFilters = new ArrayList<>();

        if (mAvailableCb.isChecked()) statusFilters.add(AVAILABLE);
        if (mFosterCb.isChecked()) statusFilters.add(FOSTER_CARE);
        if (mAdoptedCb.isChecked()) statusFilters.add(ADOPTED);

        returnFilterResults(statusFilters, speciesFilters);
    }
}
