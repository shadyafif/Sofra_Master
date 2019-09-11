package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.adapters.UserNotificationAdapter;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Client.UserNotification.Datum;
import com.example.eng_shady.MyResturant.models.Client.UserNotification.Notifications;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserNotificationFragment extends Fragment {

    List<Datum> NotificationList;
    RecyclerView recyclerView;
    UserNotificationAdapter userNotificationAdapter;
    int max_page;

    public UserNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_notification, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" التنبيهات");
        recyclerView = v.findViewById(R.id.UserNotificationRec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NotificationList = new ArrayList<>();

        OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {
                    Toast.makeText(getActivity(), "لا يوجد تنبيهات جديدة", Toast.LENGTH_SHORT).show();
                }
            }
        };

        recyclerView.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);

        return v;
    }

    private void getRecyclerView(final int page) {

        Call<Notifications> call = RetrofitClient.getInstance().getApi().
                getNotification("K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh");
        call.enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {

                Notifications noti= response.body();
                viewResponse(noti, page);
                max_page = response.body().getData().getLastPage();
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewResponse(Notifications body, int page) {
        if (page == 1) {
            NotificationList = body.getData().getData();
            userNotificationAdapter= new UserNotificationAdapter(getContext(),NotificationList);
            recyclerView.setAdapter(userNotificationAdapter);

        }
        else {
            NotificationList.addAll(body.getData().getData());
            userNotificationAdapter.notifyDataSetChanged();

        }
    }
}
