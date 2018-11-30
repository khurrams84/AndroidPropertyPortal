package com.mhaseeb.property.property.addproperty;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mhaseeb.property.BaseActivity;
import com.mhaseeb.property.R;
import com.mhaseeb.property.common.api.ApiClient;
import com.mhaseeb.property.common.config.IAPIConstants;
import com.mhaseeb.property.common.preferences.PreferenceManager;
import com.mhaseeb.property.common.utils.ImageCompression;
import com.mhaseeb.property.home.HomeActivity;
import com.mhaseeb.property.property.api.IPropertyAPIService;
import com.mhaseeb.property.property.model.PropertyModel;
import com.mhaseeb.property.property.model.PropertyResponseModel;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by muhammadmoiz on 11/21/18.
 */

public class AddPropertyActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, IPickResult {

    //UI Widgets
    private Spinner spntype, spnAreaUnit;
    private RadioButton rbSale, rbRent;
    private EditText etTitle, etDescription, etPhone, etCity, etLocation, etAddress, etArea, etPrice;
    private Button btnAdd;
    private ImageView iv_profilePic;
    private File image = null;

    private PickImageDialog pickImageDialog;

    private String type = "Select Type", areaUnit = "Select Area";
    private String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_property);
        init();
    }

    private void init() {
        setTitle("Add Property");
        spntype = findViewById(R.id.spnType);
        spnAreaUnit = findViewById(R.id.spnAreaUnit);
        rbSale = findViewById(R.id.rbSale);
        rbRent = findViewById(R.id.rbRent);
        iv_profilePic = findViewById(R.id.iv_profilePic);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etPhone = findViewById(R.id.etPhone);
        etCity = findViewById(R.id.etCity);
        etLocation = findViewById(R.id.etLocation);
        etAddress = findViewById(R.id.etAddress);
        etArea = findViewById(R.id.etArea);
        etPrice = findViewById(R.id.etPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        iv_profilePic.setOnClickListener(this);


        setUserFields();
        populateTypeSpinner();
        populateAreaUnitSpinner();
//        hideSearchView();
    }

    private void setUserFields() {
        etPhone.setText(PreferenceManager.getInstance().getPhoneNo(this));

    }

    private void populateTypeSpinner() {
        List<String> type = Arrays.asList(getResources().getStringArray(R.array.type));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        spntype.setAdapter(typeAdapter);
        spntype.setOnItemSelectedListener(this);
    }

    private void populateAreaUnitSpinner() {
        List<String> areaUnit = Arrays.asList(getResources().getStringArray(R.array.area_unit));
        ArrayAdapter<String> areaUnitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, areaUnit);
        spnAreaUnit.setAdapter(areaUnitAdapter);
        spnAreaUnit.setOnItemSelectedListener(this);
    }

    private void setCategory() {
        if (rbSale.isChecked())
            category = "For Sale";
        else
            category = "For Rent";
    }

    /**
     * Method to validate user inputs
     *
     * @return
     */
    private boolean validate() {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            etTitle.setError("Title is required");
            etTitle.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etDescription.getText().toString().trim())) {
            etDescription.setError("Description is required");
            etDescription.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            etPhone.setError("Phone no is required");
            etPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etCity.getText().toString().trim())) {
            etCity.setError("City is required");
            etCity.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etArea.getText().toString().trim())) {
            etArea.setError("Area is required");
            etArea.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etLocation.getText().toString().trim())) {
            etLocation.setError("Location is required");
            etLocation.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
            etAddress.setError("Address is required");
            etAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
            etPrice.setError("Price is required");
            etPrice.requestFocus();
            return false;
        }

        if (image == null) {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
//            case R.id.spnCities:
//                city = ((SpinnerCitiesModel) adapterView.getItemAtPosition(i)).getName();
//                break;
            case R.id.spnType:
                type = (String) adapterView.getItemAtPosition(i);
                break;
            case R.id.spnAreaUnit:
                areaUnit = (String) adapterView.getItemAtPosition(i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAdd:
                AddProperty();
                break;
            case R.id.iv_profilePic:
                PickImageDialog.build(new PickSetup()).show(this).setOnPickResult(this);
                break;
        }
    }

    private void AddProperty() {
        if (validate()) {
            setCategory();

            PropertyModel model = new PropertyModel();
            model.setUserId(Integer.valueOf(PreferenceManager.getInstance().getId(this)));
            model.setTitle(etTitle.getText().toString().trim());
            model.setDescription(etDescription.getText().toString().trim());
            model.setAddress(etAddress.getText().toString().trim());
            model.setType(type);
            model.setCategory(category);
            model.setCountry("pakistan");
            model.setCity(etCity.getText().toString().trim());
            model.setPhoneOfSeller(etPhone.getText().toString().trim());
            model.setAreaUnit(areaUnit);
            model.setArea(etArea.getText().toString().trim());
            model.setPrice(etPrice.getText().toString().trim());
            model.setBedrooms("4");
            model.setBathrooms("2");
            model.setKitchen("1");
            model.setYearBuild("2012");
            callAddPropertyAPI(model);

        }
    }

    private void callAddPropertyAPI(PropertyModel model) {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        Call<PropertyResponseModel> call = iPropertyAPIService.addproperty(model);
        showLoading("Adding Property", "Please wait...");
        call.enqueue(new Callback<PropertyResponseModel>() {
            @Override
            public void onResponse(Call<PropertyResponseModel> call, Response<PropertyResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {
                        uploadImage(image, response.body().getData().getId());
//                        ((HomeActivity) getContext()).hideLoading();
//                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else if (response.body().getStats() == false) {
                        Toast.makeText(AddPropertyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        hideLoading();
                    }
                } else {
                    Toast.makeText(AddPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<PropertyResponseModel> call, Throwable t) {
                Toast.makeText(AddPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                hideLoading();

            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void uploadImage(File file, int propertyId) {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody propertyIdRequest = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(propertyId));
        MultipartBody.Part body =  MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<PropertyResponseModel> call = iPropertyAPIService.uploadImage(body, propertyIdRequest);
        call.enqueue(new Callback<PropertyResponseModel>() {
            @Override
            public void onResponse(Call<PropertyResponseModel> call, Response<PropertyResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {
                        hideLoading();
//                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddPropertyActivity.this, "Property Added Successfully", Toast.LENGTH_SHORT).show();
                        closeActivity(true);

                    } else if (response.body().getStats() == false) {
                        Toast.makeText(AddPropertyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        hideLoading();
                    }
                } else {
                    Toast.makeText(AddPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<PropertyResponseModel> call, Throwable t) {
                Toast.makeText(AddPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                hideLoading();

            }
        });
    }

    private void closeActivity(boolean propertyAdded) {
        Intent returnIntent = getIntent();
        returnIntent.putExtra(IAPIConstants.PROPERTY_UPDATE_INTENT, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {

            ImageCompression imageCompression = new ImageCompression(this);
            String path = pickResult.getPath();
            imageCompression.execute(path);
            image = new File(path);
            iv_profilePic.setImageBitmap(pickResult.getBitmap());

        } else {
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
