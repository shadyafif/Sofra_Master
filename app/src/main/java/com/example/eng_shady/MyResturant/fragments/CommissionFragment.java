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
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantCommission.Commission;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class CommissionFragment extends Fragment {

    TextView txtCommissionSelling, txtCommissionApp, txtCommissionPaid, txtCommissionRemainder, txtCommissionValue;

    public CommissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_commission, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("UserProfile", MODE_PRIVATE);
        Boolean check = prefs.getBoolean("check", false);
        if (check == false) {
            LoginFragment login = new LoginFragment();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" تسجيل الدخول");
            login.Form = "Resturant";
            Replace(login, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());
        } else {


            txtCommissionSelling = v.findViewById(R.id.txtCommissionSelling);
            txtCommissionApp = v.findViewById(R.id.txtCommissionApp);
            txtCommissionPaid = v.findViewById(R.id.txtCommissionPaid);
            txtCommissionRemainder = v.findViewById(R.id.txtCommissionRemainder);
            txtCommissionRemainder = v.findViewById(R.id.txtCommissionRemainder);
            txtCommissionValue = v.findViewById(R.id.txtCommissionValue);
            getcommissions();
        }
        return v;
    }

    public void getcommissions() {
        Call<Commission> call = RetrofitClient.getInstance().
                getApi().GetCommission("EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv");
        call.enqueue(new Callback<Commission>() {
            @Override
            public void onResponse(Call<Commission> call, Response<Commission> response) {
                Commission body = response.body();

                txtCommissionSelling.setText(String.valueOf(body.getData().getTotal()));
                txtCommissionApp.setText(String.valueOf(body.getData().getCommissions()));
                txtCommissionPaid.setText(String.valueOf(body.getData().getPayments()));
                txtCommissionRemainder.setText(String.valueOf(body.getData().getNetCommissions()));
                txtCommissionValue.setText(body.getData().getCommission());

            }


            @Override
            public void onFailure(Call<Commission> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT);

            }
        });
    }

}
