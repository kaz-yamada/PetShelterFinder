package com.mad.petshelterfinder.savedshelters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.adapters.ShelterItemListener;
import com.mad.petshelterfinder.adapters.SheltersAdapter;
import com.mad.petshelterfinder.model.Shelter;
import com.mad.petshelterfinder.shelterdetail.ShelterDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing list of shelters saved by the user
 */
public class SavedSheltersFragment extends Fragment implements SavedSheltersContract.View {

    private SavedSheltersContract.Presenter mPresenter;
    private SheltersAdapter mAdapter;

    @BindView(R.id.no_shelters)
    LinearLayout mNoSheltersView;

    @BindView(R.id.shelter_shimmer_layout)
    ShimmerFrameLayout mShimmerLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SavedSheltersFragment() {
    }

    /**
     * Create a new instance of the fragment
     *
     * @return a new instance of the fragment
     */
    public static SavedSheltersFragment getInstance() {
        return new SavedSheltersFragment();
    }

    /**
     * Click listener for location row item
     */
    ShelterItemListener mShelterItemListener = new ShelterItemListener() {
        @Override
        public void onShelterItemClick(String shelterId) {
            mPresenter.openShelterDetails(shelterId);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter = null;
        mShelterItemListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shelter_list, container, false);

        ButterKnife.bind(this, root);

        // Set the adapter
        Context context = root.getContext();
        mAdapter = new SheltersAdapter(mShelterItemListener);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        RecyclerView recyclerView = root.findViewById(R.id.shelter_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation()
        );

        Drawable decoration = ContextCompat.getDrawable(context, R.drawable.divider);

        if (decoration != null) {
            itemDecoration.setDrawable(decoration);
            recyclerView.addItemDecoration(itemDecoration);
        }

        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void setPresenter(SavedSheltersContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void toggleLoadingUi(boolean isLoading) {
        // Start placeholder shimmer animation
        if (isLoading) {
            mShimmerLayout.setVisibility(View.VISIBLE);
            mShimmerLayout.startShimmer();
        } else {
            mShimmerLayout.stopShimmer();
            mShimmerLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void showShelterToView(Shelter shelter) {
        mAdapter.addShelterItem(shelter);
        mAdapter.notifyItemInserted(mAdapter.getItemCount());

        if (mNoSheltersView.getVisibility() == View.VISIBLE) {
            mNoSheltersView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showShelterDetailsUi(String shelterId) {
        Intent intent = new Intent(getContext(), ShelterDetailActivity.class);
        intent.putExtra(ShelterDetailActivity.EXTRA_SHELTER_ID, shelterId);
        startActivity(intent);
    }

    @Override
    public void showNoShelters() {
        mNoSheltersView.setVisibility(View.VISIBLE);
        toggleLoadingUi(false);
    }
}
