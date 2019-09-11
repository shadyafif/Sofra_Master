package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.Cities;
import com.example.eng_shady.MyResturant.models.General.DatumCity;
import com.example.eng_shady.MyResturant.models.General.DatumReqion;
import com.example.eng_shady.MyResturant.models.General.Region;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;

public class ResturantSignUpForm1Fragment extends Fragment {

    EditText TxtResturantSignForm1Name,TxtResturantSignForm1Email,TxtResturantSignForm1Home, TxtResturantSignForm1Pass,TxtResturantSignForm1Confirm;

    Spinner spResturantSignForm1Country,spResturantSignForm1town;
    Button btnResturantSignForm1;

   int txtid;


    public ResturantSignUpForm1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_resturant_sign_up_form1, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" إنشاء حساب");

        TxtResturantSignForm1Name=v.findViewById(R.id.TxtResturantSignForm1Name);
        TxtResturantSignForm1Email=v.findViewById(R.id.TxtResturantSignForm1Email);
        TxtResturantSignForm1Home=v.findViewById(R.id.TxtResturantSignForm1Home);
        TxtResturantSignForm1Pass=v.findViewById(R.id.TxtResturantSignForm1Pass);
        TxtResturantSignForm1Confirm=v.findViewById(R.id.TxtResturantSignForm1Confirm);

        spResturantSignForm1Country=v.findViewById(R.id.spResturantSignForm1Country);
        spResturantSignForm1town=v.findViewById(R.id.spResturantSignForm1town);

        btnResturantSignForm1=v.findViewById(R.id.btnResturantSignForm1);
        btnResturantSignForm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResturantSignUpForm2Fragment Form2 =new ResturantSignUpForm2Fragment();
                Form2.RegionId=txtid;
                Form2.ResturantName=TxtResturantSignForm1Name.getText().toString().trim();
                Form2.ResturantEmail=TxtResturantSignForm1Email.getText().toString().trim();
                Form2.ResturantHome=TxtResturantSignForm1Home.getText().toString().trim();
                Form2.ResturantPass=TxtResturantSignForm1Pass.getText().toString().trim();
                Form2.ResturantConfirm=TxtResturantSignForm1Confirm.getText().toString().trim();

                Replace(Form2,R.id.FragmentLoadRes,getActivity().getSupportFragmentManager().beginTransaction());

            }
        });

        GetCities();

        return  v;
    }


    private void GetCities() {
        final Api dataservices = RetrofitClient.getInstance().getApi();
        Call<Cities> call = dataservices.GetCities();
        call.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                Cities CT = response.body();
                List<DatumCity> LisOfCities = CT.getData().getData();

                List<String> listSpinner = new ArrayList <String>();
                listSpinner.add("اختار المدينة");
                final List<Integer> listIds = new ArrayList<>();

                for (int i = 0; i < LisOfCities.size(); i++) {
                    listSpinner.add(LisOfCities.get(i).getName());
                    listIds.add(LisOfCities.get(i).getId());
                }

                final int[] id = {-1};

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spResturantSignForm1Country.setAdapter(adapter);
                spResturantSignForm1Country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {

                        if (position != 0) {
                            id[0] = listIds.get(position - 1);
                            getListRegions(id[0]);

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

    private void getListRegions(int city_id) {
        final Api dataservices2 = RetrofitClient.getInstance().getApi();
        Call<Region> call = dataservices2.getListRegion(city_id);
        call.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                Region re = response.body();
                final List<DatumReqion> listofcity = re.getData().getData();
                final List<String> listSpinner = new ArrayList<String>();
                final List<Integer> listSpinnerIds = new ArrayList<>();

                for (int i = 0; i < listofcity.size(); i++) {
                    listSpinner.add(listofcity.get(i).getName());
                    listSpinnerIds.add(listofcity.get(i).getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spResturantSignForm1town.setAdapter(adapter);
                spResturantSignForm1town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long L) {
                        Log.i("selectedItem", listSpinner.get(position));

                        int id = listSpinnerIds.get(position);
                        txtid = id;


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });

    }

}
