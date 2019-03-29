package com.mad.petshelterfinder.shelterdetail;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.Shelter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mad.petshelterfinder.util.Constants.DETAIL_CAMERA_ZOOM;

/**
 * displays detailed information of pet shelter
 */
public class ShelterDetailActivity extends AppCompatActivity implements ShelterDetailContract.View {

    public static final String EXTRA_SHELTER_ID = "LOCATION_ID";
    private static final int MAX_RESULTS = 5;

    ShelterDetailContract.Presenter mPresenter;
    private Geocoder mGeocoder;

    @BindView(R.id.shelter_name_text_view)
    TextView mNameTv;

    @BindView(R.id.shelter_street_text_view)
    TextView mStreetTv;

    @BindView(R.id.shelter_address_text_view)
    TextView mAddressTv;

    @BindView(R.id.shelter_phone_text_view)
    TextView mPhoneTv;

    @BindView(R.id.shelter_email_text_view)
    TextView mEmailTv;

    @BindView(R.id.shelter_phone_layout)
    LinearLayout mPhoneLayout;

    @BindView(R.id.shelter_email_layout)
    LinearLayout mEmailLayout;

    @BindView(R.id.shelter_learn_more_button)
    Button mLearnMoreBtn;

    @BindView(R.id.shelter_view_pets_button)
    Button mViewPetsButton;

    @BindView(R.id.detail_favourite_icon)
    ImageButton mFavouriteButton;

    @BindView(R.id.map_view)
    MapView mMapView;

    private Shelter mShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);

        ButterKnife.bind(this);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String shelterId = getIntent().getStringExtra(EXTRA_SHELTER_ID);

        mPresenter = new ShelterDetailPresenter(shelterId, this);

        mLearnMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openShelterWebsite();
            }
        });

        mFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toggleFavourite();
            }
        });

        mMapView.onCreate(savedInstanceState);
        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    @Override
    public void setPresenter(ShelterDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayShelterDetails(Shelter shelter) {
        mShelter = shelter;

        mNameTv.setText(mShelter.getName());
        mStreetTv.setText(mShelter.getAddress());

        String address = String.format("%s %s, %s", mShelter.getSuburb(), mShelter.getState(), mShelter.getPostcode());
        mAddressTv.setText(address);

        if (shelter.getPhone() == null) {
            mPhoneLayout.setVisibility(View.GONE);
        } else {
            mPhoneTv.setText(shelter.getPhone());
        }

        if (shelter.getEmail() == null) {
            mEmailLayout.setVisibility(View.GONE);
        } else {
            mEmailTv.setText(shelter.getEmail());
        }

        loadDetailMap();
    }

    @Override
    public void showShelterWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void showMessage(String message) {
        View view = findViewById(R.id.shelter_detail_view);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
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
    public void showFavouriteButton() {
        mFavouriteButton.setVisibility(View.VISIBLE);
    }

    /**
     * Load street level map of the pet shelter
     */
    private void loadDetailMap() {

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                try {
                    List<Address> addressList = mGeocoder.getFromLocationName(
                            mShelter.getFullAddress(), MAX_RESULTS
                    );

                    if (addressList.size() > 0) {
                        LatLng latLng = new LatLng(
                                addressList.get(0).getLatitude(), addressList.get(0).getLongitude()
                        );

                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(mShelter.getName())
                                .snippet(mShelter.getFullAddress())
                        );

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(marker.getPosition());

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DETAIL_CAMERA_ZOOM));
                        googleMap.getUiSettings().setScrollGesturesEnabled(false);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
