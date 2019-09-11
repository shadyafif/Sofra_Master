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
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;

import java.util.List;



public class UserBasketAdabter extends RecyclerView.Adapter<UserBasketAdabter.ViewHolder> {

    private Context context;
    private List<ItemFoodData> itemFoodDataList;

    public UserBasketAdabter(Context context, List<ItemFoodData> itemFoodDataList) {
        this.context = context;
        this.itemFoodDataList = itemFoodDataList;
    }

    @NonNull
    @Override
    public UserBasketAdabter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.basketrecycle, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserBasketAdabter.ViewHolder viewHolder, int position) {

        ItemFoodData model = itemFoodDataList.get(position);

        int count = model.getCounter();
        int price = Integer.parseInt(model.getPrice());
        int total = price * count;

        viewHolder.txtBasketName.setText(model.getName());
        viewHolder.txtBasketPrice.setText(model.getPrice());
        viewHolder.txtBasketNum.setText(model.getCounter());
        viewHolder.txtBasketTotal.setText(String.valueOf(total));


        String web = "http://ipda3.com/sofra/" + model.getPhoto();
        web = web.replace("", "%20");

        Glide.with(context).load(web)
                .into(viewHolder.imgBasketItem);

    }

    @Override
    public int getItemCount() {
        return itemFoodDataList != null ? itemFoodDataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBasketDelete, imgBasketItem, ImgRecPlus, ImgRecMinus;
        TextView txtBasketName, txtBasketPrice, txtBasketNum, txtBasketTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBasketDelete = itemView.findViewById(R.id.imgBasketDelete);
            imgBasketItem = itemView.findViewById(R.id.imgBasketItem);
//            ImgRecPlus = itemView.findViewById(R.id.ImgRecPlus);
//            ImgRecMinus = itemView.findViewById(R.id.ImgRecMinus);
            txtBasketName = itemView.findViewById(R.id.txtBasketName);
            txtBasketPrice = itemView.findViewById(R.id.txtBasketPrice);
            txtBasketNum = itemView.findViewById(R.id.txtBasketNum);
            txtBasketTotal = itemView.findViewById(R.id.txtBasketTotal);


        }
    }
}
