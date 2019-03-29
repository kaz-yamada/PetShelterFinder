package com.mad.petshelterfinder.savedshelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import com.mad.petshelterfinder.petlist.PetListActivity;
import com.mad.petshelterfinder.shelters.SheltersActivity;
import com.mad.petshelterfinder.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mad.petshelterfinder.util.MenuUtils.setNavigationHeader;
import static com.mad.petshelterfinder.util.MenuUtils.setUpDrawerButton;

/**
 * Display list of shelters saved by the user
 */
public class SavedSheltersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_navigation_view)
    NavigationView mDrawerNavigationView;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;

    private SavedSheltersPresenter mSavedSheltersPresenter;
    private SavedSheltersFragment mSavedSheltersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_shelters);

        ButterKnife.bind(this);
        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set up drawer navigation
        mDrawerNavigationView.setNavigationItemSelectedListener(this);

        mSavedSheltersFragment = (SavedSheltersFragment) getSupportFragmentManager().findFragmentById(R.id.main_content_panel);
        if (mSavedSheltersFragment == null) {
            mSavedSheltersFragment = SavedSheltersFragment.getInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mSavedSheltersFragment, R.id.main_content_panel);

        mSavedSheltersPresenter = new SavedSheltersPresenter(AuthRepository.getInstance(), mSavedSheltersFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSavedSheltersFragment == null) {
            mSavedSheltersPresenter = new SavedSheltersPresenter(AuthRepository.getInstance(), mSavedSheltersFragment);
        }

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
            case R.id.view_shelters_menu_item:
                intent = new Intent(this, SheltersActivity.class);
                break;
            case R.id.view_pets_menu_item:
                intent = new Intent(this, PetListActivity.class);
                break;
            case R.id.favourited_pets_menu_item:
                intent = new Intent(this, FavouritedPetsActivity.class);
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

        if (intent != null) {
            startActivity(intent);
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
