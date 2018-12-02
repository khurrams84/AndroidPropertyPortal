package com.mhaseeb.property.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.BaseActivity;
import com.mhaseeb.property.ui.common.config.AppConstants;
import com.mhaseeb.property.ui.common.persistence.AppDatabase;
import com.mhaseeb.property.ui.common.persistence.FavouriteDao;
import com.mhaseeb.property.ui.common.persistence.FavouriteEntity;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.common.service.ServiceLocator;
import com.mhaseeb.property.ui.login.LoginActivity;
import com.mhaseeb.property.ui.property.AddPropertyFragment;
import com.mhaseeb.property.ui.property.OnSearchTextListener;
import com.mhaseeb.property.ui.property.favorites.FavoritesPropertyFragment;
import com.mhaseeb.property.ui.property.PropertyDetailActivity;
import com.mhaseeb.property.ui.property.listing.PropertyListingFragment;
import com.mhaseeb.property.ui.property.listing.UsersPropertyListingFragment;
import com.mhaseeb.property.ui.property.model.Datum;
import com.mhaseeb.property.ui.property.model.PropertyModel;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        PropertyListingFragment.OnFragmentInteractionListener,
        AddPropertyFragment.OnFragmentInteractionListener,
        FavoritesPropertyFragment.OnFragmentInteractionListener,
        UsersPropertyListingFragment.OnFragmentInteractionListener,
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private boolean doubleBackToExitPressedOnce = false;

    private TextView tvHeaderUsername, tvHeaderEmail;
    private Fragment selectedFragment;
    OnSearchTextListener onSearchTextListener;

    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setDrawer();
        initUI();
        setListingFragment();

    }

    private void setListingFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new PropertyListingFragment(), AppConstants.TAG_FRAGMENT_PROPERTY_LISTING)
                .commit();
    }

    private void launchFragment(Fragment selectedFragment, String tag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment, tag)
                .commit();
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
        Fragment myFragment;
        myFragment = getSupportFragmentManager().findFragmentByTag(AppConstants.TAG_FRAGMENT_PROPERTY_LISTING);
        if (myFragment != null && myFragment.isVisible()) {
            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.toast_Please_click_BACK_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
        //Checking for Add property fragment
        myFragment = getSupportFragmentManager().findFragmentByTag(AppConstants.TAG_FRAGMENT_ADD_PROPERTY);
        if (myFragment != null && myFragment.isVisible()) {
            setListingFragment();
        }
        //Checking for Favorites property fragment
        myFragment = getSupportFragmentManager().findFragmentByTag(AppConstants.TAG_FRAGMENT_FAVORITES);
        if (myFragment != null && myFragment.isVisible()) {
            setListingFragment();
        }
        //Checking for user property fragment
        myFragment = getSupportFragmentManager().findFragmentByTag(AppConstants.TAG_FRAGMENT_MY_ADS);
        if (myFragment != null && myFragment.isVisible()) {
            setListingFragment();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        return true;
    }

    public void showSearchView() {
        if (searchView != null)
            searchView.setVisibility(View.VISIBLE);

    }

    public void hideSearchView() {
        if (searchView != null)
            searchView.setVisibility(View.INVISIBLE);
    }
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
                if (!PreferenceManager.getInstance().getIsGuestLoggedIn(this))
                    launchFragment(new AddPropertyFragment(), AppConstants.TAG_FRAGMENT_ADD_PROPERTY);
                else
                    Toast.makeText(this, getString(R.string.toast_Please_login_from_user_account_to_add_property), Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_my_adds:
                if (!PreferenceManager.getInstance().getIsGuestLoggedIn(this))
                    launchFragment(new UsersPropertyListingFragment(), AppConstants.TAG_FRAGMENT_MY_ADS);
                else
                    Toast.makeText(this, getString(R.string.toast_Please_login_from_user_account_to_add_property), Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_favorites:
                if (!PreferenceManager.getInstance().getIsGuestLoggedIn(this))
                    launchFragment(new FavoritesPropertyFragment(), AppConstants.TAG_FRAGMENT_FAVORITES);
                else
                    Toast.makeText(this, getString(R.string.toast_Please_login_from_user_account_to_add_property), Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                PreferenceManager.getInstance().disposeAll(HomeActivity.this);
                goToLogin();
                finish();

                break;
        }

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

    @Override
    public void onFragmentInteraction(PropertyModel property, boolean isEditable) {
//        launchFragment(new PropertyListingDetailFragment());
        Intent i = new Intent(HomeActivity.this, PropertyDetailActivity.class);
        i.putExtra("property", property);
        i.putExtra("isEditable", isEditable);
        startActivity(i);
    }

    @Override
    public void onFavoritesFragmentInteraction(Datum favoritesModel) {

        Intent i = new Intent(HomeActivity.this, PropertyDetailActivity.class);
        i.putExtra("property", favoritesModel.getProperty());
        startActivity(i);

    }

    @Override
    public void onFragmentInteraction(PropertyModel property) {
        //        launchFragment(new PropertyListingDetailFragment());
        Intent i = new Intent(HomeActivity.this, PropertyDetailActivity.class);
        i.putExtra("property", property);
        startActivity(i);
    }

    public void setSearchOnTextListener(OnSearchTextListener listener) {
        onSearchTextListener = listener;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onSearchTextListener.onTextSubmit(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() == 0) {
            onSearchTextListener.onTextSubmit("");
        }
        return false;
    }

    @Override
    public boolean onClose() {
        //onSearchTextListener.onTextSubmit("");
        return false;
    }
}
