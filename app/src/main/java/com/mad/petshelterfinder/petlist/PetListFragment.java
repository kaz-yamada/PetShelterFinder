package com.mad.petshelterfinder.petlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.adapters.PetItemListener;
import com.mad.petshelterfinder.adapters.PetListAdapter;
import com.mad.petshelterfinder.filterpets.FilterPetsActivity;
import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.model.PetStatusOptions;
import com.mad.petshelterfinder.petdetail.PetDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View component of displaying a list of pets
 */
public class PetListFragment extends Fragment implements PetListContract.View {

    private PetListContract.Presenter mPresenter;
    private PetListAdapter mAdapter;
    private Context mContext;

    @BindView(R.id.pet_shimmer_layout)
    ShimmerFrameLayout mShimmerLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PetListFragment() {
    }

    /**
     * Create and return a new instance of the fragment class
     *
     * @return new instance of fragment
     */
    public static PetListFragment getInstance() {
        return new PetListFragment();
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
    public void onDetach() {
        super.onDetach();
        mPresenter = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pets, container, false);

        ButterKnife.bind(this, root);

        // Set up recycler view
        mContext = root.getContext();

        setUpRecyclerView(root);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void setPresenter(@NonNull PetListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_menu_item: {
                showFilterMenu();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPetDetailsUi(String petId) {
        Intent intent = new Intent(mContext, PetDetailActivity.class);
        intent.putExtra(PetDetailActivity.EXTRA_PET_ID, petId);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void displayPet(Pet pet) {
        mAdapter.addPetItem(pet);
    }

    @Override
    public void showFilterMenu() {
        Intent intent = new Intent(mContext, FilterPetsActivity.class);
        intent.putStringArrayListExtra(FilterPetsActivity.FILTER_SPECIES_KEY, mPresenter.getDefaultSpeciesFilter());

        startActivityForResult(intent, FilterPetsActivity.REQUEST_FILTER_PETS);
    }

    @Override
    public void showSuccessfullyFilteredMessage() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.filters_applied), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setLoadingUi() {
        // Start placeholder shimmer animation
        if (!mShimmerLayout.isShown()) {
            mShimmerLayout.setVisibility(View.VISIBLE);
        }

        mShimmerLayout.startShimmer();
    }

    @Override
    public void resetList() {
        mAdapter.resetList();
    }

    @Override
    public void removePetFromList(String petId) {
        mAdapter.removeItem(petId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {

                ArrayList<String> speciesFilter = extras.getStringArrayList(FilterPetsActivity.FILTER_SPECIES_KEY);

                @SuppressWarnings("unchecked")
                ArrayList<PetStatusOptions> statusFilter = (ArrayList<PetStatusOptions>) extras.getSerializable(FilterPetsActivity.FILTER_STATUS_KEY);

                mPresenter.result(requestCode, resultCode);
                mPresenter.setFiltering(statusFilter, speciesFilter);
            }
        }
    }

    /**
     * Initialize recycler view
     *
     * @param view fragment view
     */
    private void setUpRecyclerView(View view) {
        mAdapter = new PetListAdapter(mItemListener, mContext);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        RecyclerView recyclerView = view.findViewById(R.id.pet_list_recycler_view);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation()
        );

        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.divider);

        if (drawable != null) itemDecoration.setDrawable(drawable);

        // Set up recycler view
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * Listener for clicking on pet in the recycler view
     */
    PetItemListener mItemListener = new PetItemListener() {
        @Override
        public void onItemClick(int clickedPosition) {
            String key = mAdapter.getPetItemId(clickedPosition);
            mPresenter.openPetDetails(key);
        }

        @Override
        public void itemLoaded() {
            if (mShimmerLayout.isShimmerStarted()) {
                mShimmerLayout.stopShimmer();
                mShimmerLayout.setVisibility(View.GONE);
            }
        }
    };
}
