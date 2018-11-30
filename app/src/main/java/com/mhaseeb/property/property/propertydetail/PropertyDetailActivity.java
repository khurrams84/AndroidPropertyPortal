package com.mhaseeb.property.property.propertydetail;

import android.Manifest;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhaseeb.property.BaseActivity;
import com.mhaseeb.property.R;
import com.mhaseeb.property.common.api.ApiClient;
import com.mhaseeb.property.common.config.IAPIConstants;
import com.mhaseeb.property.common.persistence.AppDatabase;
import com.mhaseeb.property.common.persistence.FavouriteEntity;
import com.mhaseeb.property.common.preferences.PreferenceManager;
import com.mhaseeb.property.common.service.ServiceLocator;
import com.mhaseeb.property.common.utils.ImageUtil;
import com.mhaseeb.property.property.editproperty.EditPropertyActivity;
import com.mhaseeb.property.property.api.IPropertyAPIService;
import com.mhaseeb.property.property.model.FavoritesModel;
import com.mhaseeb.property.property.model.MarkFavouriteResponse;
import com.mhaseeb.property.property.model.PropertyModel;
import com.mhaseeb.property.property.propertydetail.presenter.PropertyDetailPresenterImpl;
import com.mhaseeb.property.widget.FavouriteAppWidget;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IPropertyDetailContract.View {

    private TextView tvPrice, tvTitle, tvAddress, tvDescription, tvType, tvCategory, tvCountry, tvCity, tvPhoneNo;
    private CheckBox cbFavorites;
    private Button btnCall, btnSms;
    private ImageView propertyImage, ivEdit;
    private PropertyModel propertyModel;
    private boolean isEditable = false;
    private IPropertyDetailContract.Presenter presenter;
    private boolean updated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_listing_detail);
        propertyModel = (PropertyModel) getIntent().getSerializableExtra("property");
        if (getIntent().hasExtra("isEditable"))
            isEditable = getIntent().getBooleanExtra("isEditable", false);

        initView();

    }

    private void initView() {

        presenter = new PropertyDetailPresenterImpl(this, this);

        cbFavorites = findViewById(R.id.cbFavorites);
        tvPrice = findViewById(R.id.tvPrice);
        tvTitle = findViewById(R.id.tvTitle);
        tvAddress = findViewById(R.id.tvAddress);
        tvDescription = findViewById(R.id.tvDescription);
        tvType = findViewById(R.id.tvType);
        tvCategory = findViewById(R.id.tvCategory);
        tvCountry = findViewById(R.id.tvCountry);
        tvCity = findViewById(R.id.tvCity);
        tvPhoneNo = findViewById(R.id.tvPhoneNo);
        propertyImage = findViewById(R.id.ivMainImage);
        ivEdit = findViewById(R.id.ivEdit);

        btnCall = findViewById(R.id.btnCall);
        btnSms = findViewById(R.id.btnSms);
        btnSms.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        if (isEditable)
            ivEdit.setVisibility(View.VISIBLE);

        tvPrice.setText("$" + propertyModel.getPrice());
        tvTitle.setText(propertyModel.getTitle());
        tvAddress.setText(propertyModel.getAddress());
        tvDescription.setText(propertyModel.getDescription());
        tvType.setText(propertyModel.getType());
        tvCategory.setText(propertyModel.getCategory());
        tvCountry.setText(propertyModel.getCountry());
        tvCity.setText(propertyModel.getCity());
        tvPhoneNo.setText(propertyModel.getPhoneOfSeller());
        cbFavorites.setChecked(propertyModel.isFavourite());
        cbFavorites.setOnCheckedChangeListener(this);

        if (propertyModel.getImages() != null && propertyModel.getImages().size() > 0) {
            ImageUtil.showPropertyImage(propertyImage, this, IAPIConstants.BASE_URL + propertyModel.getImages().get(0).getImagePath());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCall:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + propertyModel.getPhoneOfSeller()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                break;
            case R.id.btnSms:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", propertyModel.getPhoneOfSeller());
//                smsIntent.putExtra("sms_body","Body of Message");
                startActivity(smsIntent);
                break;
            case R.id.ivEdit:
                Intent i = new Intent(this, EditPropertyActivity.class);
                i.putExtra("property", propertyModel);
                startActivity(i);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (PreferenceManager.getInstance().getIsLoggedIn()) {
            FavoritesModel favoritesModel = new FavoritesModel();
            favoritesModel.setPropertyId(propertyModel.getId());
            favoritesModel.setStatus(isChecked);
            favoritesModel.setUserId(Integer.valueOf(PreferenceManager.getInstance().getId(this)));
            setResult(Activity.RESULT_OK);
            updated = true;
            presenter.markFavourite(favoritesModel);

        }
    }

    @Override
    public void onMarkFavouriteSuccess() {

    }

    @Override
    public void onMarkFavouriteFailure(String error) {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent returnIntent = getIntent();
        returnIntent.putExtra(IAPIConstants.PROPERTY_UPDATE_INTENT, updated);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
