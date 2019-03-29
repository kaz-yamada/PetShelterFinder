package com.mad.petshelterfinder.filterpets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.source.repositories.PetsRepository;
import com.mad.petshelterfinder.util.ActivityUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Display form to filter pet list
 */
public class FilterPetsActivity extends AppCompatActivity {
    private FilterPetsPresenter mFilterPetsPresenter;

    public static final int REQUEST_FILTER_PETS = 1;

    public static final String FILTER_SPECIES_KEY = "FILTER_SPECIES_KEY";
    public static final String FILTER_STATUS_KEY = "FILTER_STATUS_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_pets);

        ButterKnife.bind(this);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        ArrayList<String> speciesList = getIntent().getStringArrayListExtra(FILTER_SPECIES_KEY);

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

        FilterPetsFragment currentFragment = (FilterPetsFragment) getSupportFragmentManager().findFragmentById(R.id.main_content_panel);

        if (currentFragment == null) {
            currentFragment = FilterPetsFragment.getInstance(speciesList);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), currentFragment, R.id.main_content_panel);
        mFilterPetsPresenter = new FilterPetsPresenter(speciesList, currentFragment, PetsRepository.getInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFilterPetsPresenter = null;
    }
}
