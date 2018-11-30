package com.mhaseeb.property.property.favorites;

import android.content.Context;
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
import com.mhaseeb.property.common.api.ApiClient;
import com.mhaseeb.property.common.preferences.PreferenceManager;
import com.mhaseeb.property.home.HomeActivity;
import com.mhaseeb.property.property.api.IPropertyAPIService;
import com.mhaseeb.property.property.favorites.adapter.FavoritesPropertyAdapter;
import com.mhaseeb.property.property.model.Datum;
import com.mhaseeb.property.property.model.FavoritesResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesPropertyFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvPropertyListing;
    private List<Datum> favoritesPropertyListing;

    public static FavoritesPropertyFragment newInstance(String param1, String param2) {
        FavoritesPropertyFragment fragment = new FavoritesPropertyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property_listing, container, false);
        init(view);
        return view;

    }

    private void init(View view) {
        getActivity().setTitle("Favorites");
        rvPropertyListing = view.findViewById(R.id.rvPropertyListing);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPropertyListing.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPropertyListing.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recyclerview));
        ((HomeActivity)getActivity()).hideSearchView();
//        rvPropertyListing.addItemDecoration(dividerItemDecoration);


//        setAdapter();
        callGetFavoritesPropertyAPI();

    }

    private void callGetFavoritesPropertyAPI() {
        IPropertyAPIService iPropertyAPIService = ApiClient.getClient().create(IPropertyAPIService.class);
        Call<FavoritesResponseModel> call = iPropertyAPIService.getFavoriteProperties(Integer.valueOf(PreferenceManager.getInstance().getId(getContext())));
        ((HomeActivity) getActivity()).showLoading("Getting Favorites", "Please wait...");

        call.enqueue(new Callback<FavoritesResponseModel>() {
            @Override
            public void onResponse(Call<FavoritesResponseModel> call, Response<FavoritesResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == true && response.body().getData() != null) {
                        ((HomeActivity) getContext()).hideLoading();

                        favoritesPropertyListing = new ArrayList<>();
                        favoritesPropertyListing = response.body().getData();
                        if (favoritesPropertyListing.size() > 0)
                            setAdapter();
                        else
                            Toast.makeText(getContext(), "No favorite properties found!", Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus() == false) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        ((HomeActivity) getContext()).hideLoading();
                    }
                } else {
                    Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    ((HomeActivity) getContext()).hideLoading();
                }
            }

            @Override
            public void onFailure(Call<FavoritesResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                ((HomeActivity) getContext()).hideLoading();
            }
        });
    }

    private void setAdapter() {

        FavoritesPropertyAdapter favoritesPropertyAdapter = new FavoritesPropertyAdapter(getActivity(), mListener, (ArrayList<Datum>) favoritesPropertyListing);
        rvPropertyListing.setAdapter(favoritesPropertyAdapter);
    }

    public void onButtonPressed(String from) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(from);
//        }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFavoritesFragmentInteraction(Datum favoritesModel);
    }
}
