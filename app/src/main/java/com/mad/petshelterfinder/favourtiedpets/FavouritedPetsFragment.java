package com.mad.petshelterfinder.favourtiedpets;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.adapters.PetItemListener;
import com.mad.petshelterfinder.adapters.PetListAdapter;
import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.petdetail.PetDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class FavouritedPetsFragment extends Fragment implements FavouritedPetsContract.View {

    private PetListAdapter mAdapter;
    private FavouritedPetsContract.Presenter mPresenter;
    private Context mContext;

    @BindView(R.id.pet_shimmer_layout)
    ShimmerFrameLayout mShimmerLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavouritedPetsFragment() {
    }

    public static FavouritedPetsFragment getInstance() {
        return new FavouritedPetsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourited_pets, container, false);
        ButterKnife.bind(this, root);

        mContext = root.getContext();

        setUpRecyclerView(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(FavouritedPetsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayPet(Pet pet) {
        mAdapter.addPetItem(pet);
    }

    @Override
    public void showPetDetailsUi(String petId) {
        Intent intent = new Intent(mContext, PetDetailActivity.class);
        intent.putExtra(PetDetailActivity.EXTRA_PET_ID, petId);
        startActivity(intent);
    }

    @Override
    public void resetList() {
        mAdapter.resetList();
    }

    @Override
    public void showNoRecords() {
        mShimmerLayout.stopShimmer();
        mShimmerLayout.setVisibility(View.GONE);
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
    final PetItemListener mItemListener = new PetItemListener() {
        @Override
        public void onItemClick(int clickedPosition) {
            String key = mAdapter.getPetItemId(clickedPosition);
            showPetDetailsUi(key);
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
