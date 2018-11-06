package com.mhaseeb.property.ui.property.listing;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.api.ApiClient;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.home.HomeActivity;
import com.mhaseeb.property.ui.property.api.IPropertyAPIService;
import com.mhaseeb.property.ui.property.listing.adapter.PropertyListingAdapter;
import com.mhaseeb.property.ui.property.model.FavoritesModel;
import com.mhaseeb.property.ui.property.model.FavoritesResponseModel;
import com.mhaseeb.property.ui.property.model.GetUserPropertiesResponseModel;
import com.mhaseeb.property.ui.property.model.PropertyModel;
import com.mhaseeb.property.ui.property.model.PropertyResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PropertyListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PropertyListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyListingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvPropertyListing;
    private List<PropertyModel> propertyListing;

    public PropertyListingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PropertyListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PropertyListingFragment newInstance(String param1, String param2) {
        PropertyListingFragment fragment = new PropertyListingFragment();
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
        View view = inflater.inflate(R.layout.fragment_property_listing, container, false);
        init(view);
        return view;

    }

    private void init(View view) {
        getActivity().setTitle("Home");
        rvPropertyListing = view.findViewById(R.id.rvPropertyListing);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPropertyListing.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPropertyListing.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recyclerview));
//        rvPropertyListing.addItemDecoration(dividerItemDecoration);


//        setAdapter();
        callGetPropertyAPI();

    }

    private void callGetPropertyAPI() {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        Call<GetUserPropertiesResponseModel> call;
        if (PreferenceManager.getInstance().getIsLoggedIn(getContext()))
            call = iPropertyAPIService.getAllUserProperties(Integer.valueOf(PreferenceManager.getInstance().getId(getContext())));
        else
            call = iPropertyAPIService.getAllProperties();
        ((HomeActivity) getActivity()).showLoading("Getting Properties", "Please wait...");
        call.enqueue(new Callback<GetUserPropertiesResponseModel>() {
            @Override
            public void onResponse(Call<GetUserPropertiesResponseModel> call, Response<GetUserPropertiesResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStats() == true && response.body().getData() != null) {
                        ((HomeActivity) getContext()).hideLoading();

                        propertyListing = new ArrayList<>();
                        propertyListing = response.body().getData();
                        setAdapter();
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
            public void onFailure(Call<GetUserPropertiesResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getContext()).hideLoading();
            }
        });

    }

    private void callMarkFavoritePropertyAPI(FavoritesModel model) {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        Call<FavoritesResponseModel> call = iPropertyAPIService.markFavoriteProperty(model);
//        ((HomeActivity) getActivity()).showLoading("Getting Properties", "Please wait...");
        call.enqueue(new Callback<FavoritesResponseModel>() {
            @Override
            public void onResponse(Call<FavoritesResponseModel> call, Response<FavoritesResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == true && response.body().getData() != null) {
//                        ((HomeActivity) getContext()).hideLoading();

                    } else if (response.body().getStatus() == false) {
//                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        ((HomeActivity) getContext()).hideLoading();
                    }
                } else {
//                    Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
//                    ((HomeActivity) getContext()).hideLoading();
                }
            }

            @Override
            public void onFailure(Call<FavoritesResponseModel> call, Throwable t) {

            }
        });


    }

    private void setAdapter() {
//
//        propertyListing = new ArrayList<>();
//        propertyListing.add(new PropertyModel(1, "New House For sale", "", "181 Sloane Street Goulburn NSW 2580", "House", "For Sale", "",
//                "", "", "", "", "$550,000", "", "", "", "", 110));
//        propertyListing.add(new PropertyModel(2, "New House For Rent", "", "182 Sloane Street Goulburn NSW 2580", "Flat", "For Rent", "",
//                "", "", "", "", "$365,00", "", "", "", "", 110));
//        propertyListing.add(new PropertyModel(3, "Villa for sale", "", "183 Sloane Street Goulburn NSW 2580", "Villa", "For Sale", "",
//                "", "", "", "", "$9875,00", "", "", "", "", 110));
//        propertyListing.add(new PropertyModel(4, "New House For Rent", "", "184 Sloane Street Goulburn NSW 2580", "Flat", "For Rent", "",
//                "", "", "", "", "$365,00", "", "", "", "", 110));
//        propertyListing.add(new PropertyModel(5, "New House For Rent", "", "185 Sloane Street Goulburn NSW 2580", "Flat", "For Rent", "",
//                "", "", "", "", "$365,00", "", "", "", "", 110));
//        propertyListing.add(new PropertyModel(6, "New House For Rent", "", "186 Sloane Street Goulburn NSW 2580", "Flat", "For Rent", "",
//                "", "", "", "", "$365,00", "", "", "", "", 110));
        PropertyListingAdapter listingAdapter = new PropertyListingAdapter(getActivity(), this, mListener, (ArrayList<PropertyModel>) propertyListing);
        rvPropertyListing.setAdapter(listingAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onFavoritesButtonPressed(FavoritesModel model) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(from);
//        }

        callMarkFavoritePropertyAPI(model);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(PropertyModel property);
    }
}
