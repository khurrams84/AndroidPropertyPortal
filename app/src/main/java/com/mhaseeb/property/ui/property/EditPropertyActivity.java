package com.mhaseeb.property.ui.property;

import android.content.Intent;
import android.os.Bundle;
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

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.BaseActivity;
import com.mhaseeb.property.ui.common.api.ApiClient;
import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.common.utils.ImageUtil;
import com.mhaseeb.property.ui.property.api.IPropertyAPIService;
import com.mhaseeb.property.ui.property.model.PropertyModel;
import com.mhaseeb.property.ui.property.model.PropertyResponseModel;
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

public class EditPropertyActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, IPickResult {
    //UI Widgets
    private Spinner spntype, spnAreaUnit;
    private RadioButton rbSale, rbRent;
    private EditText etTitle, etDescription, etPhone, etCity, etLocation, etAddress, etArea, etPrice;
    private Button btnAdd;
    private ImageView iv_profilePic;

    private File image = null;


    private String type = "Select Type", areaUnit = "Select Area";
    private String category = "";
    private PropertyModel propertyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_property);
        handleIntent(getIntent());
        init();
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            propertyModel = (PropertyModel) getIntent().getSerializableExtra("property");
        }
    }

    private void init() {
        setTitle("Edit Property");
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
        etLocation.setVisibility(View.VISIBLE);
        etAddress = findViewById(R.id.etAddress);
        etArea = findViewById(R.id.etArea);
        etPrice = findViewById(R.id.etPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnAdd.setText(R.string.code_edit);
        iv_profilePic.setOnClickListener(this);


        setUserFields();
        populateTypeSpinner();
        populateAreaUnitSpinner();
    }

    private void setUserFields() {
        etTitle.setText(propertyModel.getTitle());
        etDescription.setText(propertyModel.getDescription());
        etPhone.setText(propertyModel.getPhoneOfSeller());
        etCity.setText(propertyModel.getCity());
        etAddress.setText(propertyModel.getAddress());
        etArea.setText(propertyModel.getArea());
        etPrice.setText(propertyModel.getPrice());
        if (propertyModel.getImages() != null && propertyModel.getImages().size() > 0) {
            ImageUtil.showPropertyImage(iv_profilePic, this, IAPIConstants.BASE_URL + propertyModel.getImages().get(0).getImagePath());
        }
        if (propertyModel.getCategory().equals("For Sale")) {
            rbSale.setChecked(true);
        } else {
            rbRent.setChecked(true);
        }

    }

    private void populateTypeSpinner() {
        List<String> type = Arrays.asList(getResources().getStringArray(R.array.type));
        int typeIndex = getTypeindex(type);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        spntype.setAdapter(typeAdapter);
        spntype.setOnItemSelectedListener(this);
        spntype.setSelection(typeIndex);

    }

    private int getTypeindex(List<String> type) {
        int index = 0;
        for (String st : type) {
            if (st.equals(propertyModel.getType()))
                return index;
            index++;
        }
        return 0;
    }

    private int getAreaUnitIndex(List<String> AreaUnit) {
        int index = 0;
        for (String st : AreaUnit) {
            if (st.equals(propertyModel.getAreaUnit()))
                return index;
            index++;
        }
        return 0;
    }

    private void populateAreaUnitSpinner() {
        List<String> areaUnit = Arrays.asList(getResources().getStringArray(R.array.area_unit));
        int areaUnitIndex = getAreaUnitIndex(areaUnit);
        ArrayAdapter<String> areaUnitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, areaUnit);
        spnAreaUnit.setAdapter(areaUnitAdapter);
        spnAreaUnit.setOnItemSelectedListener(this);
        spnAreaUnit.setSelection(areaUnitIndex);
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

//        if (TextUtils.isEmpty(etLocation.getText().toString().trim())) {
//            etLocation.setError("Location is required");
//            etLocation.requestFocus();
//            return false;
//        }
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

        if (propertyModel.getImages() == null && image == null) {
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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAdd:
                EditProperty();
                break;
            case R.id.iv_profilePic:
                PickImageDialog.build(new PickSetup()).show(this).setOnPickResult(this);
                break;
        }
    }

    private void EditProperty() {
        if (validate()) {
            setCategory();

            PropertyModel model = new PropertyModel();
            model.setUserId(Integer.valueOf(PreferenceManager.getInstance().getId(this)));
            model.setId(propertyModel.getId());
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
            callEditPropertyAPI(model);

        }
    }

    private void callEditPropertyAPI(PropertyModel model) {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        Call<PropertyResponseModel> call = iPropertyAPIService.updateproperty(model);
        showLoading("Updating Property", "Please wait...");
        call.enqueue(new Callback<PropertyResponseModel>() {
            @Override
            public void onResponse(Call<PropertyResponseModel> call, Response<PropertyResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {
                        if (image != null)
                            uploadImage(image, response.body().getData().getId());
                        else {
                            hideLoading();
                            finish();
                        }
//                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else if (response.body().getStats() == false) {
                        Toast.makeText(EditPropertyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        hideLoading();
                    }
                } else {
                    Toast.makeText(EditPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<PropertyResponseModel> call, Throwable t) {
                Toast.makeText(EditPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                hideLoading();

            }
        });

    }


    private void uploadImage(File file, int propertyId) {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody propertyIdRequest = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(propertyId));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<PropertyResponseModel> call = iPropertyAPIService.uploadImage(body, propertyIdRequest);
        call.enqueue(new Callback<PropertyResponseModel>() {
            @Override
            public void onResponse(Call<PropertyResponseModel> call, Response<PropertyResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {
                        hideLoading();
                        Toast.makeText(EditPropertyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    } else if (response.body().getStats() == false) {
                        Toast.makeText(EditPropertyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        hideLoading();
                    }
                } else {
                    Toast.makeText(EditPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<PropertyResponseModel> call, Throwable t) {
                Toast.makeText(EditPropertyActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                hideLoading();

            }
        });
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {

            image = new File(pickResult.getPath());
            iv_profilePic.setImageBitmap(pickResult.getBitmap());

        } else {
            Toast.makeText(EditPropertyActivity.this, pickResult.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
