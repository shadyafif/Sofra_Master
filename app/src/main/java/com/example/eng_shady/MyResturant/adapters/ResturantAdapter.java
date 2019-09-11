package com.example.eng_shady.MyResturant.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.fragments.CommentsFragment;
import com.example.eng_shady.MyResturant.fragments.FoodMenuFragment;

import com.example.eng_shady.MyResturant.models.Resturant.DatumResturant;


import java.util.List;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> {
    Context context;
    List<DatumResturant> ResturantsList;


    public ResturantAdapter(Context context, List<DatumResturant> resturantsList) {
        this.context = context;
        ResturantsList = resturantsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resturantist_rec, null, false);
        return new ResturantAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final DatumResturant resturantDtails = ResturantsList.get(position);
        String catger = "";

        for (int i = 0; i < resturantDtails.getCategories().size(); i++) {

            catger = catger + resturantDtails.getCategories().get(i).getName() + "-";

        }
        holder.txtResturantListCatagory.setText(catger);


        String web = "http://ipda3.com/sofra/" + resturantDtails.getPhoto();
        web = web.replace(" ", "%20");

        Glide.with(context).load(web)
                .into(holder.ResturantListimg);

        final int rate = resturantDtails.getRate();

        holder.ResturantListRating.setRating(rate);
        holder.txtResturantListName.setText(resturantDtails.getName());


        if (resturantDtails.getavailability().equals("open")) {
            holder.txtResturantListStatus.setText("مفتوح");
        } else {
            holder.txtResturantListStatus.setText("مقفول");
        }

        holder.txtResturantListMini.setText(resturantDtails.getMinimumCharger() + " ج");
        holder.txtResturantListDelivary.setText(resturantDtails.getDeliveryCost() + " ج");

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClientActivity clientActivity = (ClientActivity) context;
                FoodMenuFragment foodMenuFragment = new FoodMenuFragment();

                foodMenuFragment.id = resturantDtails.getId();
                foodMenuFragment.ResName = resturantDtails.getName();


                Replace(foodMenuFragment, R.id.FragmentLoad, clientActivity.getSupportFragmentManager().beginTransaction());
            }
        });
    }


    @Override
    public int getItemCount() {
        return ResturantsList != null ? ResturantsList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ResturantListimg;
        RatingBar ResturantListRating;
        TextView txtResturantListName, txtResturantListCatagory,
                txtResturantListStatus, txtResturantListMini, txtResturantListDelivary;

        View v;

        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            ResturantListimg = itemView.findViewById(R.id.ResturantListimg);
            ResturantListRating = itemView.findViewById(R.id.ResturantListRating);
            txtResturantListName = itemView.findViewById(R.id.txtResturantListName);
            txtResturantListCatagory = itemView.findViewById(R.id.txtResturantListCatagory);
            txtResturantListStatus = itemView.findViewById(R.id.txtResturantListStatus);
            txtResturantListMini = itemView.findViewById(R.id.txtResturantListMini);
            txtResturantListDelivary = itemView.findViewById(R.id.txtResturantListDelivary);


        }
    }
}
