package com.mhaseeb.property.ui.property;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.api.ApiClient;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.common.utils.ImageCompression;
import com.mhaseeb.property.ui.home.HomeActivity;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPropertyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPropertyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPropertyFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, IPickResult {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //UI Widgets
    private Spinner spntype, spnAreaUnit;
    private RadioButton rbSale, rbRent;
    private EditText etTitle, etDescription, etPhone, etCity, etLocation, etAddress, etArea, etPrice;
    private Button btnAdd;
    private ImageView iv_profilePic;

    private File image = null;


    private String type = "Select Type", areaUnit = "Select Area";
    private String category = "";

    public AddPropertyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPropertyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPropertyFragment newInstance(String param1, String param2) {
        AddPropertyFragment fragment = new AddPropertyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_property, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        getActivity().setTitle("Add Property");
        spntype = view.findViewById(R.id.spnType);
        spnAreaUnit = view.findViewById(R.id.spnAreaUnit);
        rbSale = view.findViewById(R.id.rbSale);
        rbRent = view.findViewById(R.id.rbRent);
        iv_profilePic = view.findViewById(R.id.iv_profilePic);

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etPhone = view.findViewById(R.id.etPhone);
        etCity = view.findViewById(R.id.etCity);
        etLocation = view.findViewById(R.id.etLocation);
        etAddress = view.findViewById(R.id.etAddress);
        etArea = view.findViewById(R.id.etArea);
        etPrice = view.findViewById(R.id.etPrice);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        iv_profilePic.setOnClickListener(this);


        setUserFields();
        populateTypeSpinner();
        populateAreaUnitSpinner();

        ((HomeActivity)getActivity()).hideSearchView();
    }

    private void setUserFields() {
        etPhone.setText(PreferenceManager.getInstance().getPhoneNo(getActivity()));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void populateTypeSpinner() {
        List<String> type = Arrays.asList(getResources().getStringArray(R.array.type));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, type);
        spntype.setAdapter(typeAdapter);
        spntype.setOnItemSelectedListener(this);
    }

    private void populateAreaUnitSpinner() {
        List<String> areaUnit = Arrays.asList(getResources().getStringArray(R.array.area_unit));
        ArrayAdapter<String> areaUnitAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, areaUnit);
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
            Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
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
                AddProperty();
                break;
            case R.id.iv_profilePic:
                PickImageDialog.build(new PickSetup()).show(getActivity()).setOnPickResult(this);
                break;
        }
    }

    private void AddProperty() {
        if (validate()) {
            setCategory();

            PropertyModel model = new PropertyModel();
            model.setUserId(Integer.valueOf(PreferenceManager.getInstance().getId(getActivity())));
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
        ((HomeActivity) getActivity()).showLoading("Adding Property", "Please wait...");
        call.enqueue(new Callback<PropertyResponseModel>() {
            @Override
            public void onResponse(Call<PropertyResponseModel> call, Response<PropertyResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {
                        uploadImage(image, response.body().getData().getId());
//                        ((HomeActivity) getContext()).hideLoading();
//                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else if (response.body().getStats() == false) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        ((HomeActivity) getContext()).hideLoading();
                    }
                } else {
                    Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    ((HomeActivity) getContext()).hideLoading();
                }
            }

            @Override
            public void onFailure(Call<PropertyResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getContext()).hideLoading();

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
                        ((HomeActivity) getContext()).hideLoading();
//                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Property Added Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();

                    } else if (response.body().getStats() == false) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        ((HomeActivity) getContext()).hideLoading();
                    }
                } else {
                    Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    ((HomeActivity) getContext()).hideLoading();
                }
            }

            @Override
            public void onFailure(Call<PropertyResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getContext()).hideLoading();

            }
        });
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {

            ImageCompression imageCompression = new ImageCompression(getActivity());

            String path = pickResult.getPath();

            imageCompression.execute(path);

            image = new File(path);

            iv_profilePic.setImageBitmap(pickResult.getBitmap());

        } else {
            Toast.makeText(getActivity(), pickResult.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
