package com.mhaseeb.property.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.BaseActivity;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.login.LoginActivity;
import com.mhaseeb.property.ui.property.AddPropertyFragment;
import com.mhaseeb.property.ui.property.PropertyListingFragment;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        PropertyListingFragment.OnFragmentInteractionListener,
        AddPropertyFragment.OnFragmentInteractionListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    private TextView tvHeaderUsername, tvHeaderEmail;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setDrawer();
        initUI();
        setListingFragment();
    }

    private void setListingFragment() {
        selectedFragment = new PropertyListingFragment();
        launchFragment(selectedFragment);
    }

    private void launchFragment(Fragment selectedFragment) {

        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        transactions.replace(R.id.fragment_container, selectedFragment);
        transactions.commit();
    }

    private void initUI() {
        View view = navigationView.getHeaderView(0);
        tvHeaderEmail = view.findViewById(R.id.tvHeaderEmail);
        tvHeaderUsername = view.findViewById(R.id.tvHeaderUsername);
        if (!PreferenceManager.getInstance().getIsGuestLoggedIn(this)) {
            tvHeaderEmail.setText(PreferenceManager.getInstance().getEmail(this));
            tvHeaderUsername.setText(PreferenceManager.getInstance().getFirstName(this) + " " + PreferenceManager.getInstance().getLastName(this));
        }

    }

    private void setDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.nav_item_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                setListingFragment();
                break;
            case R.id.nav_add_property:
                launchFragment(new AddPropertyFragment());
                break;
            case R.id.nav_logout:
                PreferenceManager.getInstance().disposeAll(HomeActivity.this);
                goToLogin();
                finish();

                break;
        }

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
