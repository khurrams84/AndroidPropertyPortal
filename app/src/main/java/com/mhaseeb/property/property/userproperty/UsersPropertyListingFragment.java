package com.mhaseeb.property.property.userproperty;

import android.arch.lifecycle.ViewModelProviders;
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
import com.mhaseeb.property.common.preferences.PreferenceManager;
import com.mhaseeb.property.home.HomeActivity;
import com.mhaseeb.property.property.model.PropertyModel;
import com.mhaseeb.property.property.userproperty.viewmodel.UserPropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class UsersPropertyListingFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvPropertyListing;
    private List<PropertyModel> propertyListing;
    private UserPropertyViewModel userPropertyViewModel;

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
        getActivity().setTitle("My Ads");
        rvPropertyListing = view.findViewById(R.id.rvPropertyListing);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPropertyListing.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPropertyListing.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recyclerview));
        ((HomeActivity)getActivity()).hideSearchView();
//        rvPropertyListing.addItemDecoration(dividerItemDecoration);


//        setAdapter();
        userPropertyViewModel = ViewModelProviders.of(this).get(UserPropertyViewModel.class);
        observeCurrencyData();

    }

    private void setAdapter() {
        UserPropertyListingAdapter listingAdapter = new UserPropertyListingAdapter(getActivity(), mListener, (ArrayList<PropertyModel>) propertyListing);
        rvPropertyListing.setAdapter(listingAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        void onFragmentInteraction(PropertyModel property, boolean b);
    }

    private void observeCurrencyData() {

        ((HomeActivity) getActivity()).showLoading("Getting user ads", "Please wait...");

        userPropertyViewModel.getUserProperty(Integer.valueOf(PreferenceManager.getInstance().getId(getContext()))).observe(this, properties -> {

            if (properties!= null && properties.getStats() == true && properties.getData() != null) {
                ((HomeActivity) getContext()).hideLoading();

                propertyListing = new ArrayList<>();
                propertyListing = properties.getData();
                setAdapter();
            } else if (properties.getStats() == false) {
                Toast.makeText(getContext(), properties.getMessage(), Toast.LENGTH_SHORT).show();
                ((HomeActivity) getContext()).hideLoading();
            }

        });
    }
}
