package com.mhaseeb.property.ui.property.favorites.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.common.utils.ImageUtil;
import com.mhaseeb.property.ui.property.favorites.FavoritesPropertyFragment;
import com.mhaseeb.property.ui.property.listing.PropertyListingFragment;
import com.mhaseeb.property.ui.property.listing.adapter.PropertyListingAdapter;
import com.mhaseeb.property.ui.property.model.Datum;
import com.mhaseeb.property.ui.property.model.PropertyModel;

import java.util.ArrayList;


/**
 * Created By Mohammad Haseeb
 */
public class FavoritesPropertyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Datum> favoritesPropertyListing;
    private FavoritesPropertyFragment.OnFragmentInteractionListener mListener;

    public FavoritesPropertyAdapter(Context context, FavoritesPropertyFragment.OnFragmentInteractionListener mListener, ArrayList<Datum> favoritesPropertyListing) {
        this.mContext = context;
        this.favoritesPropertyListing = favoritesPropertyListing;
        this.mListener = mListener;
    }

    public void updatePrices(ArrayList<Datum> favoritesPropertyListing) {
        this.favoritesPropertyListing = favoritesPropertyListing;
        notifyDataSetChanged();

    }

    @Override
    public FavoritesPropertyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_property_listing, viewGroup, false);

        return new FavoritesPropertyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof FavoritesPropertyAdapter.ViewHolder) {
            final Datum model = getItem(position);
            ((ViewHolder) holder).tvPrice.setText(R.string.code_dollar + model.getProperty().getPrice());
            ((ViewHolder) holder).tvTitle.setText(model.getProperty().getTitle());
            ((ViewHolder) holder).tvDescription.setText(model.getProperty().getAddress());
            ((ViewHolder) holder).tvType.setText(model.getProperty().getType());
            ((ViewHolder) holder).tvCategory.setText(model.getProperty().getCategory());
            ((ViewHolder) holder).cbFavorites.setChecked(model.getProperty().isFavourite());

            if (model.getProperty().getImages() != null && model.getProperty().getImages().size() > 0) {
                ImageUtil.showPropertyImage(((ViewHolder) holder).ivMainImage, mContext, IAPIConstants.BASE_URL + model.getProperty().getImages().get(0).getImagePath());
            }

            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFavoritesFragmentInteraction(model);
                }
            });

        }
    }


    @Override
    public int getItemCount() {

        return favoritesPropertyListing.size();
    }

    private Datum getItem(int position) {
        return favoritesPropertyListing.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMainImage;
        private TextView tvPrice, tvTitle, tvDescription, tvType, tvCategory;
        private CheckBox cbFavorites;

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

