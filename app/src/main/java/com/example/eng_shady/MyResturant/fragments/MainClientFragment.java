package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.adapters.ResturantAdapter;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.Cities;
import com.example.eng_shady.MyResturant.models.General.DatumCity;
import com.example.eng_shady.MyResturant.models.Resturant.DatumResturant;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantDtails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainClientFragment extends Fragment {

    List<DatumResturant> ResturantList;
    RecyclerView recyclerView;
    ResturantAdapter recyclerAdapter;
    int max_page;
    Spinner ResturantListspinner;

    public MainClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_client, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("الرئيسية");

       ResturantListspinner= v.findViewById(R.id.ResturantListspinner);
        ResturantList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.RecResturantList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);



        OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {
                    Toast.makeText(getActivity(), "لا يوجد مطاعم اخرى ", Toast.LENGTH_SHORT).show();
                }
            }
        };

        recyclerView.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);
        GetCities();

        return v;
    }

    private void getRecyclerView(int i) {

        final Api getDataService = RetrofitClient.getInstance().getApi();
        Call<ResturantDtails> call = getDataService.GetResturant();
        call.enqueue(new Callback<ResturantDtails>() {
            @Override
            public void onResponse(Call<ResturantDtails> call, Response<ResturantDtails> response) {

                ResturantDtails body = response.body();
                viewResponse(body, 1);
                max_page = response.body().getData().getLastPage();
            }

            @Override
            public void onFailure(Call<ResturantDtails> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewResponse(ResturantDtails body, int page) {

        if (page == 1) {
            ResturantList = body.getData().getData();
            recyclerAdapter = new ResturantAdapter(getContext(), ResturantList);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            ResturantList.addAll(body.getData().getData());
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    private void GetCities() {
        final Api dataservices = RetrofitClient.getInstance().getApi();
        Call<Cities> call = dataservices.GetCities();
        call.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                Cities CT = response.body();
                List<DatumCity> LisOfCities = CT.getData().getData();

                List<String> listSpinner = new ArrayList<String>();
                listSpinner.add("اختار المدينة");
                final List<Integer> listIds = new ArrayList<>();

                for (int i = 0; i < LisOfCities.size(); i++) {
                    listSpinner.add(LisOfCities.get(i).getName());
                    listIds.add(LisOfCities.get(i).getId());
                }

                final int[] id = {-1};

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ResturantListspinner.setAdapter(adapter);
                ResturantListspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {

                        if (position != 0) {
                            id[0] = listIds.get(position - 1);


//                            txtRegisterCountry.setText(spRegisterCountry.getSelectedItem().toString());
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });
    }

}
