package com.mhaseeb.property.ui.property.listing;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.api.ApiClient;
import com.mhaseeb.property.ui.common.persistence.AppDatabase;
import com.mhaseeb.property.ui.common.persistence.FavouriteEntity;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.common.service.ServiceLocator;
import com.mhaseeb.property.ui.home.HomeActivity;
import com.mhaseeb.property.ui.property.OnSearchTextListener;
import com.mhaseeb.property.ui.property.api.IPropertyAPIService;
import com.mhaseeb.property.ui.property.listing.adapter.PropertyListingAdapter;
import com.mhaseeb.property.ui.property.model.FavoritesModel;
import com.mhaseeb.property.ui.property.model.GetUserPropertiesResponseModel;
import com.mhaseeb.property.ui.property.model.MarkFavouriteResponse;
import com.mhaseeb.property.ui.property.model.PropertyModel;
import com.mhaseeb.property.ui.widget.FavouriteAppWidget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropertyListingFragment extends Fragment implements OnSearchTextListener {
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
    private AdView adView;

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

        ((HomeActivity) getActivity()).setSearchOnTextListener(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPropertyListing.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recyclerview));
//        rvPropertyListing.addItemDecoration(dividerItemDecoration);


//        setAdapter();
        ((HomeActivity) getActivity()).showSearchView();
        callGetPropertyAPI("");
        adView =  view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    private void callGetPropertyAPI(String query) {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        Call<GetUserPropertiesResponseModel> call;
        if (PreferenceManager.getInstance().getIsLoggedIn())
            call = iPropertyAPIService.getAllUserProperties(Integer.valueOf(PreferenceManager.getInstance().getId(getContext())), query);
        else
            call = iPropertyAPIService.getAllProperties(query);
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
        Call<MarkFavouriteResponse> call = iPropertyAPIService.markFavoriteProperty(model);
//        ((HomeActivity) getActivity()).showLoading("Getting Properties", "Please wait...");
        call.enqueue(new Callback<MarkFavouriteResponse>() {
            @Override
            public void onResponse(Call<MarkFavouriteResponse> call, final Response<MarkFavouriteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == true && response.body().getData() != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                PropertyModel propertyModel = response.body().getData().getProperty();

                                Intent intent = new Intent(getActivity(), FavouriteAppWidget.class);
                                intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                                int ids[] = AppWidgetManager.getInstance(getActivity()).getAppWidgetIds(new ComponentName(getActivity(), FavouriteAppWidget.class));
                                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                                getActivity().sendBroadcast(intent);

                                //if property is marked favourite
                                if (response.body().getData().getStatus()) {
                                    FavouriteEntity favouriteEntity = new FavouriteEntity(propertyModel);
                                    ServiceLocator.getService(AppDatabase.class).FavouriteDao().deleteByPropertyId(propertyModel.getId());
                                    ServiceLocator.getService(AppDatabase.class).FavouriteDao().insertOnlySingleFavourite(favouriteEntity);
                                } else {
                                    ServiceLocator.getService(AppDatabase.class).FavouriteDao().deleteByPropertyId(propertyModel.getId());
                                }

                            }
                        }).start();

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
            public void onFailure(Call<MarkFavouriteResponse> call, Throwable t) {
                Log.e("Error", "Error in marking favourite");
            }
        });


    }

    private void setAdapter() {

        PropertyListingAdapter listingAdapter = new PropertyListingAdapter(getActivity(), this, mListener, (ArrayList<PropertyModel>) propertyListing);
        rvPropertyListing.setAdapter(listingAdapter);
    }

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

    @Override
    public void onTextSubmit(String query) {
        callGetPropertyAPI(query);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(PropertyModel property);
    }

}
