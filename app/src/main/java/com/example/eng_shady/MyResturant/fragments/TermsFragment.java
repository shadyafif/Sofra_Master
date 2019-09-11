package com.example.eng_shady.MyResturant.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Settings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class TermsFragment extends Fragment {

    TextView txtTerms;

    public TermsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_terms, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("الشوط و الأحكام");

        SharedPreferences prefs = getActivity().getSharedPreferences("UserProfile", MODE_PRIVATE);
        Boolean check = prefs.getBoolean("check", false);
        if (check == false) {
            LoginFragment login = new LoginFragment();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" تسجيل الدخول");
            login.Form = "Resturant";
            Replace(login, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());
        } else {
            txtTerms = v.findViewById(R.id.txtTerms);
            GetTerms();
        }

        return v;
    }

    public void GetTerms() {
        final Api dataservices2 = RetrofitClient.getInstance().getApi();
        Call<Settings> call = dataservices2.getSetting();
        call.enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings settings = response.body();

                txtTerms.setText(settings.getData().getTerms());
            }

            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}

