package com.mad.petshelterfinder.shelters.sheltermap;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.mad.petshelterfinder.model.source.repositories.SheltersRepository;
import com.mad.petshelterfinder.shelterdetail.ShelterDetailActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mad.petshelterfinder.util.Constants.DEFAULT_CAMERA_ZOOM;
import static com.mad.petshelterfinder.util.Constants.SYDNEY_LATITUDE;
import static com.mad.petshelterfinder.util.Constants.SYDNEY_LONGITUDE;
import static com.mad.petshelterfinder.util.StringUtils.logMessage;

/**
 * Display nearby pet shelters on a map view
 */
public class ShelterMapFragment extends Fragment implements ShelterMapContract.View {

    private ShelterMapContract.Presenter mPresenter;
    private GoogleMap mGoogleMap;
    private Geocoder mGeocoder;
    private static final int MAX_RESULTS = 5;
    private ArrayList<Shelter> mShelters;

    @BindView(R.id.map_view)
    MapView mMapView;

    @BindView(R.id.map_progress_bar)
    ProgressBar mProgressBar;

    public ShelterMapFragment() {
    }

    /**
     * Create and return a new instance of the fragment class
     *
     * @return new instance of fragment
     */
    public static ShelterMapFragment getInstance() {
        return new ShelterMapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        new ShelterMapPresenter(this, SheltersRepository.getInstance());

        mMapView.onCreate(savedInstanceState);

        if (getActivity() != null) {
            Context context = getActivity().getApplicationContext();
            mGeocoder = new Geocoder(context, Locale.getDefault());
        }

        mShelters = new ArrayList<>();
        mPresenter.start();

        return view;
    }

    private String getShelterFromMarkerId(String markerId) {
        for (Shelter shelter : mShelters) {
            if (markerId.equals(shelter.getMarkerId())) {
                return shelter.getShelterId();
            }
        }
        return null;
    }

    @Override
    public void displayShelter(Shelter shelter) {
        mShelters.add(shelter);
    }

    @Override
    public void showShelterDetailsUi(String shelterId) {
        Intent intent = new Intent(getContext(), ShelterDetailActivity.class);
        intent.putExtra(ShelterDetailActivity.EXTRA_SHELTER_ID, shelterId);
        startActivity(intent);
    }

    @Override
    public void removeShelterFromView(String shelterId) {

    }

    @Override
    public void notifyLoadFinished(int count) {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Double lat, lon;
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                mGoogleMap = googleMap;

                LatLng sydney = new LatLng(SYDNEY_LATITUDE, SYDNEY_LONGITUDE);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, DEFAULT_CAMERA_ZOOM));
                mProgressBar.setVisibility(View.GONE);
                mMapView.setVisibility(View.VISIBLE);

                for (int i = 0; i < mShelters.size(); i++) {
                    try {
                        List<Address> address = mGeocoder.getFromLocationName(
                                mShelters.get(i).getFullAddress(), MAX_RESULTS);

                        if (address.size() > 0) {
                            lat = address.get(0).getLatitude();
                            lon = address.get(0).getLongitude();

                            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lon))
                                    .title(mShelters.get(i).getName())
                                    .snippet(mShelters.get(i).getFullAddress())
                            );
                            mShelters.get(i).setMarkerId(marker.getId());

                            builder.include(marker.getPosition());
                        }
                    } catch (IOException e) {
                        logMessage(e.toString());
                        e.printStackTrace();
                    }
                }

                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String shelterId = getShelterFromMarkerId(marker.getId());
                        if (shelterId != null) {
                            mPresenter.openShelterDetails(shelterId);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void setPresenter(ShelterMapContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
