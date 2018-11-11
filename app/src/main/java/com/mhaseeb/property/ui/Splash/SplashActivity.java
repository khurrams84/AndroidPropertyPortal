package com.mhaseeb.property.ui.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.home.HomeActivity;
import com.mhaseeb.property.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent;
                if (PreferenceManager.getInstance().getIsLoggedIn()) {
                    mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                } else {
                    mainIntent = new Intent(SplashActivity.this, LoginActivity.class);

                }
                startActivity(mainIntent);
                finish();


            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
