package com.mhaseeb.property.ui.property;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.BaseActivity;
import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.common.utils.ImageUtil;
import com.mhaseeb.property.ui.home.HomeActivity;
import com.mhaseeb.property.ui.property.listing.adapter.PropertyListingAdapter;
import com.mhaseeb.property.ui.property.model.PropertyModel;

public class PropertyDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvPrice, tvTitle, tvAddress, tvDescription, tvType, tvCategory, tvCountry, tvCity, tvPhoneNo;
    private CheckBox cbFavorites;
    private Button btnCall, btnSms;
    private ImageView propertyImage, ivEdit;

    private PropertyModel propertyModel;
    private boolean isEditable = false;

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

        tvPrice.setText(R.string.code_dollar + propertyModel.getPrice());
        tvTitle.setText(propertyModel.getTitle());
        tvAddress.setText(propertyModel.getAddress());
        tvDescription.setText(propertyModel.getDescription());
        tvType.setText(propertyModel.getType());
        tvCategory.setText(propertyModel.getCategory());
        tvCountry.setText(propertyModel.getCountry());
        tvCity.setText(propertyModel.getCity());
        tvPhoneNo.setText(propertyModel.getPhoneOfSeller());
        cbFavorites.setChecked(propertyModel.isFavourite());

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
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
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
}
