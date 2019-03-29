package com.mad.petshelterfinder.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.source.repositories.AuthRepository;
import com.mad.petshelterfinder.util.ActivityUtils;

/**
 * Displays registration form to add new user to firebase auth
 */
public class RegisterActivity extends AppCompatActivity {
    RegisterPresenter mRegisterPresenter;

    public static final int REQUEST_REGISTER_USER = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        RegisterFragment registrationFragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_content_panel);

        if (registrationFragment == null) {
            registrationFragment = RegisterFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registrationFragment, R.id.main_content_panel);
        }

        mRegisterPresenter = new RegisterPresenter(registrationFragment, AuthRepository.getInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter = null;
    }
}
