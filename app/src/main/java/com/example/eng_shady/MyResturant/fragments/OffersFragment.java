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
import com.example.eng_shady.MyResturant.adapters.OffersAdapter;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.Offers.DatumOffers;
import com.example.eng_shady.MyResturant.models.General.Offers.Offers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OffersFragment extends Fragment {

    List<DatumOffers> OffersList;
    RecyclerView recyclerView;
    OffersAdapter recyclerAdapter;
    int max_page;


    public OffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_offers, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" جديد العروض");

        recyclerView = v.findViewById(R.id.RecOffers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        OffersList = new ArrayList<>();

        OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {
                    Toast.makeText(getActivity(), "لا يوجد عروض جديدة", Toast.LENGTH_SHORT).show();
                }
            }
        };

        recyclerView.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);


        return v;
    }


    private void getRecyclerView(final int page) {
//        String url = "http://ipda3.com/sofra/api/v1/offers";
//        String urlWithPag = url + "?page=" + page;

        final Api getDataService = RetrofitClient.getInstance().getApi();
        Call<Offers> call = getDataService.Getoffer();
        call.enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {

                Offers body = response.body();

                viewResponse(body, page);
                max_page = response.body().getData().getLastPage();


            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {
              Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void viewResponse(Offers body, int page) {

        if (page == 1) {
            OffersList = body.getData().getData();
            recyclerAdapter= new OffersAdapter(getContext(),OffersList);
            recyclerView.setAdapter(recyclerAdapter);

        }
        else {
            OffersList.addAll(body.getData().getData());
            recyclerAdapter.notifyDataSetChanged();

        }
    }

}
