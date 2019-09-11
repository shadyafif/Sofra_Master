package com.example.eng_shady.MyResturant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.models.Client.Orders.Datum;

import java.util.List;

public class MyOrdersRecAdapter extends RecyclerView.Adapter<MyOrdersRecAdapter.ViewHolder> {

    Context context;
    List<Datum> MyOrdersList;

    public MyOrdersRecAdapter(Context context, List<Datum> myOrdersList) {
        this.context = context;
        MyOrdersList = myOrdersList;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.myordersrc, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Datum body = MyOrdersList.get(position);
        viewHolder.txtMyOrdersTitle.setText("إسم المطعم :"+body.getRestaurant().getName());
        viewHolder.txtMyOrdersPrice.setText("قيمة الطلب :"+body.getCost());
        viewHolder.txtMyOrdersDilevery.setText("قيمة التوصيل :"+body.getDeliveryCost());
        viewHolder.txtMyOrdersTotal.setText("الإجمالى :"+body.getTotal());


        String web =  body.getRestaurant().getPhotoUrl();
        web = web.replace(" ", "%20");

        Glide.with(context).load(web)

                .into(viewHolder.imgMyOrders);

    }

    @Override
    public int getItemCount() {
        return MyOrdersList != null ? MyOrdersList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMyOrders;
        TextView txtMyOrdersTitle, txtMyOrdersNum, txtMyOrdersPrice, txtMyOrdersDilevery,
                txtMyOrdersTotal;
        Button btnMyOrdersDileverd, btnMyOrdersRefusal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMyOrders = itemView.findViewById(R.id.imgMyOrders);
            txtMyOrdersTitle = itemView.findViewById(R.id.txtMyOrdersTitle);
            txtMyOrdersNum = itemView.findViewById(R.id.txtMyOrdersNum);
            txtMyOrdersPrice = itemView.findViewById(R.id.txtMyOrdersPrice);
            txtMyOrdersDilevery = itemView.findViewById(R.id.txtMyOrdersDilevery);
            txtMyOrdersTotal = itemView.findViewById(R.id.txtMyOrdersTotal);
            btnMyOrdersDileverd = itemView.findViewById(R.id.btnMyOrdersDileverd);
            btnMyOrdersRefusal = itemView.findViewById(R.id.btnMyOrdersRefusal);

        }
    }
}
