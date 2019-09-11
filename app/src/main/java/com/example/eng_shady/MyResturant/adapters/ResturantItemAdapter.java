package com.example.eng_shady.MyResturant.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;

import com.example.eng_shady.MyResturant.fragments.ItemDetailsFragment;




import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;


import java.util.List;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;

/**
 * Created by ENG-SHADY on 1/13/2019.
 */

public class ResturantItemAdapter extends RecyclerView.Adapter<ResturantItemAdapter.ViewHolder> {
    Context context;
    List<ItemFoodData> ResturantsItemList;

    public ResturantItemAdapter(Context context, List<ItemFoodData> resturantsItemList) {
        this.context = context;
        ResturantsItemList = resturantsItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resturantitem, null, false);
        return new ResturantItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ItemFoodData body = ResturantsItemList.get(position);

        String web = "http://ipda3.com/sofra/" + body.getPhoto();
        web = web.replace(" ", "%20");
        Glide.with(context).load(web)

                .into(holder.ImgResItem);

        holder.txtResItemName.setText(body.getName());
        holder.txtResItemDescription.setText(body.getDescription());
        holder.txtResItemPrice.setText(body.getPrice());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientActivity ResActivity = (ClientActivity) context;
                ItemDetailsFragment itemDetails = new ItemDetailsFragment();
                itemDetails.itemId= body.getId();
                itemDetails.ItemName= body.getName();
                itemDetails.ItemDescription= body.getDescription();
                itemDetails.ItemPrice= body.getPrice();
                itemDetails.ItemPreperingTime= body.getPreparingTime();
                itemDetails.ItemImage= body.getPhotoUrl();

                Replace(itemDetails,R.id.FragmentLoad,ResActivity.getSupportFragmentManager().beginTransaction());

            }
        });



    }

    @Override
    public int getItemCount() {
        return ResturantsItemList != null ? ResturantsItemList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ImgResItem;
        TextView txtResItemName, txtResItemDescription, txtResItemPrice, TxtItemUpdate;
        View v;

        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            ImgResItem = itemView.findViewById(R.id.ImgResItem);
            txtResItemName = itemView.findViewById(R.id.txtResItemName);
            txtResItemDescription = itemView.findViewById(R.id.txtResItemDescription);
            txtResItemPrice = itemView.findViewById(R.id.txtResItemPrice);
            TxtItemUpdate = itemView.findViewById(R.id.TxtItemUpdate);
        }
    }
}
