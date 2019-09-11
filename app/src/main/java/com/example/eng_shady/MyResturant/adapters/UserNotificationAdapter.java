package com.example.eng_shady.MyResturant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.models.Client.UserNotification.Datum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserNotificationAdapter extends RecyclerView.Adapter<UserNotificationAdapter.ViewHolader> {

    Context context;
    List<Datum> NotificationList;

    public UserNotificationAdapter(Context context, List<Datum> notificationList) {
        this.context = context;
        NotificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolader onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recnotification, null, false);
        return new UserNotificationAdapter.ViewHolader(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolader viewHolader, int postion) {

        Datum model = NotificationList.get(postion);

        String date = model.getCreatedAt();
        String[] s = date.split(" ", 2);
        String Time = model.getCreatedAt();
        String time = Time.substring(11);


        viewHolader.txtTitleNotification.setText(model.getContent());
        viewHolader.txtDateNotification.setText(s[0]);
        viewHolader.txtTimeNotification.setText(time);

    }

    @Override
    public int getItemCount() {
        return NotificationList != null ? NotificationList.size() : 0;
    }

    public class ViewHolader extends RecyclerView.ViewHolder {

        TextView txtTitleNotification, txtTimeNotification, txtDateNotification;
        ImageView imgNotification;

        public ViewHolader(@NonNull View itemView) {
            super(itemView);

            txtTitleNotification = itemView.findViewById(R.id.txtTitleNotification);
            txtTimeNotification = itemView.findViewById(R.id.txtTimeNotification);
            txtDateNotification = itemView.findViewById(R.id.txtDateNotification);
            imgNotification = itemView.findViewById(R.id.imgNotification);

        }
    }
}
