package com.mad.petshelterfinder.shelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.favourtiedpets.FavouritedPetsActivity;
import com.mad.petshelterfinder.login.LoginActivity;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;
import com.mad.petshelterfinder.model.source.repositories.SheltersRepository;
import com.mad.petshelterfinder.petlist.PetListActivity;
import com.mad.petshelterfinder.savedshelters.SavedSheltersActivity;
import com.mad.petshelterfinder.shelters.shelterlist.ShelterListContract;
import com.mad.petshelterfinder.shelters.shelterlist.ShelterListFragment;
import com.mad.petshelterfinder.shelters.shelterlist.ShelterListPresenter;
import com.mad.petshelterfinder.shelters.sheltermap.ShelterMapFragment;
import com.mad.petshelterfinder.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mad.petshelterfinder.util.MenuUtils.setNavigationHeader;
import static com.mad.petshelterfinder.util.MenuUtils.setUpDrawerButton;

/**
 * Activity that displays animal shelters on a list or map.
 */
public class SheltersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int mCurrentItemId;

    private ShelterListPresenter mShelterListPresenter;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.drawer_navigation_view)
    NavigationView mDrawerNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelters);

        ButterKnife.bind(this);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Load default fragment
        Fragment currentFragment = ShelterMapFragment.getInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), currentFragment, R.id.main_content_panel);

        // Set up drawer navigation
        mDrawerNavigationView.setNavigationItemSelectedListener(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment newFragment = null;
                switch (item.getItemId()) {
                    case R.id.search_shelters_menu_item:
                        newFragment = ShelterListFragment.getInstance();
                        if (mShelterListPresenter == null) {
                            mShelterListPresenter = new ShelterListPresenter(
                                    (ShelterListContract.View) newFragment, SheltersRepository.getInstance());
                        }
                        break;
                    case R.id.shelter_map_menu_item:
                        newFragment = ShelterMapFragment.getInstance();
                        break;
                }

                if (newFragment != null && mCurrentItemId != item.getItemId()) {
                    ActivityUtils.replaceFragmentToActivity(
                            getSupportFragmentManager(), newFragment, R.id.main_content_panel
                    );
                    mCurrentItemId = item.getItemId();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNavigationHeader(
                mDrawerNavigationView,
                AuthRepository.getEmail(),
                AuthRepository.isLoggedIn()
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (setUpDrawerButton(mDrawerLayout, item.getItemId())) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.view_pets_menu_item:
                intent = new Intent(this, PetListActivity.class);
                break;
            case R.id.favourited_pets_menu_item:
                intent = new Intent(this, FavouritedPetsActivity.class);
                break;
            case R.id.saved_shelters_menu_item:
                intent = new Intent(this, SavedSheltersActivity.class);
                break;
            case R.id.login_menu_item:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.logout_menu_item:
                AuthRepository.getInstance().logout();
                break;
            default:
                break;
        }

        if (intent != null && mCurrentItemId != item.getItemId()) {
            startActivity(intent);
            mCurrentItemId = item.getItemId();
        }

        item.setChecked(true);

        // Close the navigation drawer when an item is selected.
        mDrawerLayout.closeDrawers();

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
