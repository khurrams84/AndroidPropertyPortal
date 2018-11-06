package com.mhaseeb.property.ui.property.listing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.common.utils.ImageUtil;
import com.mhaseeb.property.ui.property.listing.PropertyListingFragment;
import com.mhaseeb.property.ui.property.listing.UsersPropertyListingFragment;
import com.mhaseeb.property.ui.property.model.PropertyModel;

import java.util.ArrayList;


/**
 * Created By Mohammad Haseeb
 */
public class UserPropertyListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<PropertyModel> propertyListing;
    private UsersPropertyListingFragment.OnFragmentInteractionListener mListener;

    public UserPropertyListingAdapter(Context context, UsersPropertyListingFragment.OnFragmentInteractionListener mListener, ArrayList<PropertyModel> propertyListing) {
        this.mContext = context;
        this.propertyListing = propertyListing;
        this.mListener = mListener;
    }

    public void updatePrices(ArrayList<PropertyModel> prices) {
        this.propertyListing = propertyListing;
        notifyDataSetChanged();

    }

    @Override
    public UserPropertyListingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_property_listing, viewGroup, false);

        return new UserPropertyListingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof UserPropertyListingAdapter.ViewHolder) {
            final PropertyModel model = getItem(position);
            ((ViewHolder) holder).tvPrice.setText("$" + model.getPrice());
            ((ViewHolder) holder).tvTitle.setText(model.getTitle());
            ((ViewHolder) holder).tvDescription.setText(model.getAddress());
            ((ViewHolder) holder).tvType.setText(model.getType());
            ((ViewHolder) holder).tvCategory.setText(model.getCategory());

            if (model.getImages() != null && model.getImages().size() > 0) {
                ImageUtil.showPropertyImage(((ViewHolder) holder).ivMainImage, mContext, IAPIConstants.BASE_URL + model.getImages().get(0).getImagePath());
            }

            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentInteraction(model, true);
                }
            });

        }
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
        private TextView tvPrice, tvTitle, tvDescription, tvType, tvCategory;

        public ViewHolder(final View itemView) {
            super(itemView);

            ivMainImage = itemView.findViewById(R.id.ivMainImage);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvType = itemView.findViewById(R.id.tvType);
            tvCategory = itemView.findViewById(R.id.tvCategory);

        }
    }

}

