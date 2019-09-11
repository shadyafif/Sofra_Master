package com.example.eng_shady.MyResturant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.models.General.ResturantReview.Datum;

import java.util.List;


public class ResturantReviewAdapter extends RecyclerView.Adapter<ResturantReviewAdapter.ViewHolder> {

    Context context;
    List<Datum> CommentsList;

    public ResturantReviewAdapter(Context context, List<Datum> commentsLis) {
        this.context = context;
        CommentsList = commentsLis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resturantreview, null, false);
        return new ResturantReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Datum body = CommentsList.get(position);
        holder.txtResReviewName.setText(body.getClient().getName());
        holder.txtResReviewdetails.setText(body.getComment());
        String Rate=body.getRate();
        holder.ResReviewRating.setRating(Integer.valueOf(Rate));


        String startdate = body.getCreatedAt();
        String[] s = startdate.split(" ", 2);
        holder.txtResReviewdate.setText(s[0]);


    }

    @Override
    public int getItemCount() {
        return CommentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtResReviewName,txtResReviewdate,txtResReviewdetails;
        RatingBar ResReviewRating;

        public ViewHolder(View itemView) {
            super(itemView);
            ResReviewRating = itemView.findViewById(R.id.ResReviewRating);
            txtResReviewName=itemView.findViewById(R.id.txtResReviewName);
            txtResReviewdate=itemView.findViewById(R.id.txtResReviewdate);
            txtResReviewdetails=itemView.findViewById(R.id.txtResReviewdetails);
        }
    }
}
