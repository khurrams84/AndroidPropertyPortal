package com.mhaseeb.property.property.listing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhaseeb.property.R;
import com.mhaseeb.property.common.config.IAPIConstants;
import com.mhaseeb.property.common.preferences.PreferenceManager;
import com.mhaseeb.property.common.utils.ImageUtil;
import com.mhaseeb.property.userproperty.PropertyListingFragment;
import com.mhaseeb.property.property.model.FavoritesModel;
import com.mhaseeb.property.property.model.PropertyModel;

import java.util.ArrayList;


/**
 * Created By Mohammad Haseeb
 */
public class PropertyListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<PropertyModel> propertyListing;
    private PropertyListingFragment.OnFragmentInteractionListener mListener;
    private PropertyListingFragment propertyListingFragment;

    public PropertyListingAdapter(Context context, PropertyListingFragment propertyListingFragment, PropertyListingFragment.OnFragmentInteractionListener mListener, ArrayList<PropertyModel> propertyListing) {
        this.mContext = context;
        this.propertyListing = propertyListing;
        this.mListener = mListener;
        this.propertyListingFragment = propertyListingFragment;
    }

    public void updatePrices(ArrayList<PropertyModel> propertyListing) {
        this.propertyListing = propertyListing;
        notifyDataSetChanged();

    }

    @Override
    public PropertyListingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_property_listing, viewGroup, false);

        return new PropertyListingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof PropertyListingAdapter.ViewHolder) {
            final PropertyModel model = getItem(position);
            ((ViewHolder) holder).tvPrice.setText(R.string.code_dollar + model.getPrice());
            ((ViewHolder) holder).tvTitle.setText(model.getTitle());
            ((ViewHolder) holder).tvDescription.setText(model.getAddress());
            ((ViewHolder) holder).tvType.setText(model.getType());
            ((ViewHolder) holder).tvCategory.setText(model.getCategory());

            ((ViewHolder) holder).cbFavorites.setOnCheckedChangeListener(null);
            if (model.isFavourite()) {
                ((ViewHolder) holder).cbFavorites.setChecked(true);
            } else {
                ((ViewHolder) holder).cbFavorites.setChecked(false);
            }

            if (model.getImages() != null && model.getImages().size() > 0) {
                ImageUtil.showPropertyImage(((ViewHolder) holder).ivMainImage, mContext, IAPIConstants.BASE_URL + model.getImages().get(0).getImagePath());
            }

            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentInteraction(model);
                }
            });


            ((ViewHolder) holder).cbFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (PreferenceManager.getInstance().getIsLoggedIn()) {
                        model.setFavourite(b);
//                        updatePrices(propertyListing);
                        FavoritesModel favoritesModel = new FavoritesModel();
                        favoritesModel.setPropertyId(model.getId());
                        favoritesModel.setStatus(b);
                        favoritesModel.setUserId(Integer.valueOf(PreferenceManager.getInstance().getId(mContext)));
                        propertyListingFragment.onFavoritesButtonPressed(favoritesModel);
                    } else
                        Toast.makeText(mContext, "Please login from user account to add property", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return propertyListing.size();
    }

    private PropertyModel getItem(int position) {
        return propertyListing.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMainImage;
        private CheckBox cbFavorites;
        private TextView tvPrice, tvTitle, tvDescription, tvType, tvCategory;

        public ViewHolder(final View itemView) {
            super(itemView);

            ivMainImage = itemView.findViewById(R.id.ivMainImage);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvType = itemView.findViewById(R.id.tvType);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            cbFavorites = itemView.findViewById(R.id.cbFavorites);

        }
    }

}

