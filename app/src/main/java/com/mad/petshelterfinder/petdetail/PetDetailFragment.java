package com.mad.petshelterfinder.petdetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.shelterdetail.ShelterDetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mad.petshelterfinder.util.StringUtils.capitaliseString;

/**
 *
 */
public class PetDetailFragment extends Fragment implements PetDetailContract.View {
    @NonNull
    private final static String ARGUMENT_TASK_ID = "PET_ID";
    private final static int DETAIL_IMAGE_SIZE = 380;

    private PetDetailContract.Presenter mPresenter;

    @BindView(R.id.detail_image_view)
    ImageView mImageView;

    @BindView(R.id.detail_favourite_icon)
    ImageButton mFavouriteButton;

    @BindView(R.id.detail_name_text_view)
    TextView mNameTv;

    @BindView(R.id.detail_age_text_view)
    TextView mAgeTv;

    @BindView(R.id.detail_breed_text_view)
    TextView mBreedTv;

    @BindView(R.id.detail_sex_text_view)
    TextView mSexTv;

    @BindView(R.id.detail_shelter_text_view)
    TextView mShelterTv;

    @BindView(R.id.detail_species_text_view)
    TextView mSpeciesTv;

    @BindView(R.id.detail_stats_text_view)
    TextView mStatsTv;

    @BindView(R.id.detail_learn_more_button)
    Button mLearnMoreButton;

    @BindView(R.id.view_shelter_pets_button)
    Button mViewShelterButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PetDetailFragment() {
    }

    /**
     * Create a new instance of the fragment
     *
     * @param petId the id of the pet to fetch from Firebase
     * @return a new instance
     */
    public static PetDetailFragment getInstance(String petId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, petId);
        PetDetailFragment fragment = new PetDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
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

        View root = inflater.inflate(R.layout.fragment_pet_detail, container, false);
        ButterKnife.bind(this, root);

        mLearnMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openPetUrl();
            }
        });

        mViewShelterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openShelterList();
            }
        });

        mFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toggleFavourite();
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void showPetUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void setPetImage(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .resize(DETAIL_IMAGE_SIZE, DETAIL_IMAGE_SIZE)
                .centerCrop()
                .into(mImageView);
    }

    @Override
    public void showPetShelter(String shelterId) {
        Intent intent = new Intent(getContext(), ShelterDetailActivity.class);
        intent.putExtra(ShelterDetailActivity.EXTRA_SHELTER_ID, shelterId);
        startActivity(intent);
    }

    @Override
    public void setFavouriteButton(boolean wasAddedToFavourites) {
        int imgResource = R.drawable.ic_favorite_border;
        if (wasAddedToFavourites) {
            imgResource = R.drawable.ic_favorite;
        }

        mFavouriteButton.setImageResource(imgResource);
    }

    @Override
    public void showUpdateMessage(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showFavouriteButton() {
        mFavouriteButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(PetDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayPetDetails(Pet pet, String shelterName) {
        mNameTv.setText(pet.getName());
        mAgeTv.setText(String.format(getString(R.string.age_format), pet.getAge()));
        mBreedTv.setText(pet.getBreed());
        mSexTv.setText(pet.getGender());
        mShelterTv.setText(shelterName);
        mSpeciesTv.setText(capitaliseString(pet.getSpecies()));

        String stats = "";

        Context context = getActivity();

        if (pet.isDesexed() && context != null) {
            stats += context.getString(R.string.desexed);
        }
        if (pet.isMicrochipped() && context != null) {
            stats += context.getString(R.string.microchipped);
        }

        mStatsTv.setText(stats);
    }
}
