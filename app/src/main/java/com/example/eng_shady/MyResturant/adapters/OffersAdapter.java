package com.example.eng_shady.MyResturant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.fragments.OfferDetailsFragment;
import com.example.eng_shady.MyResturant.models.General.Offers.DatumOffers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    Context context;
    List<DatumOffers> OfferLists;

    public OffersAdapter(Context context, List<DatumOffers> offerLists) {
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
        final DatumOffers datumOffers = OfferLists.get(position);

        String startdate = datumOffers.getStartingAt();
        String[] s = startdate.split(" ", 2);

        String enddate = datumOffers.getEndingAt();
        String[] w = enddate.split(" ", 2);

        holder.ResOfferstitle.setText(datumOffers.getDescription());
        holder.ResOffersName.setText(datumOffers.getRestaurant().getName());
        holder.ResOfferStart.setText(s[0]);
        holder.ResOffersEnd.setText(w[0]);
        holder.ResOffersprice.setText(datumOffers.getPrice());

        String web = "http://ipda3.com/sofra/" + datumOffers.getPhoto();
        web = web.replace(" ", "%20");

//        Glide.with(context).load(web)
//
//                .apply(new RequestOptions())
//                .into(holder.ResOffersImage);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ClientActivity clientActivity = (ClientActivity) context;
                OfferDetailsFragment offerDetailsFragment = new OfferDetailsFragment();
                offerDetailsFragment.OfferName = datumOffers.getName();
                offerDetailsFragment.OfferDescription = datumOffers.getDescription();
                offerDetailsFragment.OfferStart = datumOffers.getStartingAt();
                offerDetailsFragment.OfferEnd = datumOffers.getEndingAt();
                offerDetailsFragment.OfferAvaibale = datumOffers.isAvailable();
                offerDetailsFragment.OfferPath ="http://ipda3.com/sofra/" + datumOffers.getPhoto();
                offerDetailsFragment.OfferPrice = datumOffers.getPrice();
                Replace(offerDetailsFragment, R.id.FragmentLoad, clientActivity.getSupportFragmentManager().beginTransaction());
            }
        });

    }

    @Override
    public int getItemCount() {
        return OfferLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ResOffersImage;
        TextView ResOfferstitle, ResOffersName, ResOfferStart, ResOffersEnd, ResOffersprice;
        View v;

        public ViewHolder(View itemView) {
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
