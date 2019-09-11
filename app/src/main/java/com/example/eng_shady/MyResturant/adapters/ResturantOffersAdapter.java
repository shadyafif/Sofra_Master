package com.example.eng_shady.MyResturant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.NewOffer.Datum;


import java.util.List;

/**
 * Created by ENG-SHADY on 1/29/2019.
 */

public class ResturantOffersAdapter extends RecyclerView.Adapter<ResturantOffersAdapter.ViewHolder> {
    Context context;
    List<Datum> OfferLists;

    public ResturantOffersAdapter(Context context, List<Datum> offerLists) {
        this.context = context;
        OfferLists = offerLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_offer_recycler_content, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Datum datum = OfferLists.get(position);
        String startdate = datum.getStartingAt();
        String[] s = startdate.split(" ", 2);

        String enddate = datum.getEndingAt();
        String[] w = enddate.split(" ", 2);

        holder.ResOfferstitle.setText(datum.getDescription());
        holder.ResOffersName.setText(datum.getRestaurant().getName());
        holder.ResOfferStart.setText(s[0]);
        holder.ResOffersEnd.setText(w[0]);
        holder.ResOffersprice.setText(datum.getPrice());

        String web = "http://ipda3.com/sofra/" + datum.getPhoto();
        web = web.replace(" ", "%20");

        Glide.with(context).load(web)

                .into(holder.ResOffersImage);

    }

    @Override
    public int getItemCount() {
        return OfferLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ResOffersImage;
        TextView ResOfferstitle, ResOffersName, ResOfferStart, ResOffersEnd, ResOffersprice;
        View v;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            v = itemView;

            ResOffersImage = itemView.findViewById(R.id.ResOffersImage);
            ResOfferstitle = itemView.findViewById(R.id.ResOfferstitle);
            ResOffersName = itemView.findViewById(R.id.ResOffersName);
            ResOfferStart = itemView.findViewById(R.id.ResOfferStart);
            ResOffersEnd = itemView.findViewById(R.id.ResOffersEnd);
            ResOffersprice = itemView.findViewById(R.id.ResOffersprice);
        }
    }
}
