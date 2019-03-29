package com.mad.petshelterfinder.petdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;
import com.mad.petshelterfinder.model.source.repositories.PetsRepository;
import com.mad.petshelterfinder.util.ActivityUtils;

/**
 * displays detailed information of pet
 */
public class PetDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PET_ID = "PET_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

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

        String petId = getIntent().getStringExtra(EXTRA_PET_ID);

        PetDetailFragment petDetailFragment = (PetDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_content_panel);

        if (petDetailFragment == null) {
            petDetailFragment = PetDetailFragment.getInstance(petId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    petDetailFragment, R.id.main_content_panel);
        }

        new PetDetailPresenter(
                petId,
                PetsRepository.getInstance(),
                AuthRepository.getInstance(),
                petDetailFragment
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
