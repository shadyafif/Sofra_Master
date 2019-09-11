package com.example.eng_shady.MyResturant.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.adapters.ResturantItemAdapter;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ResturantItems;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {


    List<ItemFoodData> ResturantsItemList;
    RecyclerView recyclerView;
    ResturantItemAdapter recyclerAdapter;
    int max_page;

    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food, container, false);

        ResturantsItemList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.RecItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {
                    Toast.makeText(getActivity(), "إنتهت القائمة ", Toast.LENGTH_SHORT).show();
                }
            }
        };

        recyclerView.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);

        return v;
    }

    private void getRecyclerView(int current_page) {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("myRef", MODE_PRIVATE);
        int idName = prefs.getInt("idRes", 0); //0 is the default value.

        final Api getDataService = RetrofitClient.getInstance().getApi();
        Call<ResturantItems> call = getDataService.GetResItems(idName);
        call.enqueue(new Callback<ResturantItems>() {
            @Override
            public void onResponse(Call<ResturantItems> call, Response<ResturantItems> response) {
                ResturantItems resturantItems = response.body();
                viewResponse(resturantItems, 1);
                max_page = response.body().getData().getLastPage();

            }

            @Override
            public void onFailure(Call<ResturantItems> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void viewResponse(ResturantItems resturantItems, int page) {

        if (page == 1) {

            ResturantsItemList = resturantItems.getData().getData();
            recyclerAdapter = new ResturantItemAdapter(getContext(), ResturantsItemList);
            recyclerView.setAdapter(recyclerAdapter);


        } else {

            ResturantsItemList.addAll(resturantItems.getData().getData());
            recyclerAdapter.notifyDataSetChanged();

        }
    }
}



