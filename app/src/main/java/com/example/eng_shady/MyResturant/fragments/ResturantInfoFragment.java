package com.example.eng_shady.MyResturant.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.icu.text.Replaceable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.ResturantD.ResturantDetails;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantStatus.ChangeStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class ResturantInfoFragment extends Fragment {

    ImageView imgResturantInfo;
    TextView txtResturantInfoName, txtResturantInfostatus, txtResturantInfoType, txtResturantInfocity,
            txtResturantInfoarea, txtResturantInfominimum, txtResturantInfoareaprice;

    Switch Resturantswitch1;

    Button ResturantInfoSave;

    public String ResturantName, ApiToken;
    public int ResturantId;


    String catger = "";

    private ProgressDialog mProgress;

    public ResturantInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resturant_info, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("مـــرحبا");

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        imgResturantInfo = v.findViewById(R.id.imgResturantInfo);
        txtResturantInfoName = v.findViewById(R.id.txtResturantInfoName);
        txtResturantInfostatus = v.findViewById(R.id.txtResturantInfostatus);
        txtResturantInfoType = v.findViewById(R.id.txtResturantInfoType);
        txtResturantInfocity = v.findViewById(R.id.txtResturantInfocity);
        txtResturantInfoarea = v.findViewById(R.id.txtResturantInfoarea);
        txtResturantInfominimum = v.findViewById(R.id.txtResturantInfominimum);
        txtResturantInfoareaprice = v.findViewById(R.id.txtResturantInfoareaprice);
        Resturantswitch1 = v.findViewById(R.id.Resturantswitch1);

        mProgress = new ProgressDialog(getContext());

        mProgress.setMessage("جارى تحميل البيانات.....");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        ShowDetails();

        ResturantInfoSave = v.findViewById(R.id.ResturantInfoSave);
        ResturantInfoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusChange();
            }
        });


        SharedPreferences.Editor editor = getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE).edit();
        editor.putString("ResturantName", ResturantName);
        editor.putInt("idResturant", ResturantId);
        editor.putString("Apitoken", ApiToken);

        editor.apply();

        return v;
    }

    private void StatusChange() {
        String Status;
        if (Resturantswitch1.isChecked()) {
            Status = "open";
            Call<ChangeStatus> call = RetrofitClient.getInstance().getApi().getChange(Status,
                    ApiToken);
            call.enqueue(new Callback<ChangeStatus>() {
                @Override
                public void onResponse(Call<ChangeStatus> call, Response<ChangeStatus> response) {
                    ChangeStatus body = response.body();
                    body.getData().setAvailability("open");
                }

                @Override
                public void onFailure(Call<ChangeStatus> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Status = "closed";
            Call<ChangeStatus> call = RetrofitClient.getInstance().getApi().getChange(Status,
                    ApiToken);
            call.enqueue(new Callback<ChangeStatus>() {
                @Override
                public void onResponse(Call<ChangeStatus> call, Response<ChangeStatus> response) {
                    ChangeStatus body = response.body();
                    body.getData().setAvailability("closed");
                }

                @Override
                public void onFailure(Call<ChangeStatus> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        LoginFragment login = new LoginFragment();
        login.Form = "Resturant";
        Replace(login, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());

    }


    public void ShowDetails() {
        mProgress.show();


        Call<ResturantDetails> call = RetrofitClient.getInstance().getApi().GetResDetails(ResturantId);
        call.enqueue(new Callback<ResturantDetails>() {
            @Override
            public void onResponse(Call<ResturantDetails> call, Response<ResturantDetails> response) {
                mProgress.dismiss();
                ResturantDetails body = response.body();


                String web = "http://ipda3.com/sofra/" + body.getData().getPhoto();
                web = web.replace(" ", "%20");

                Glide.with(getContext()).load(web)

                        .into(imgResturantInfo);

                txtResturantInfoName.setText("إسم المطعم : " + body.getData().getName());


                for (int i = 0; i < body.getData().getCategories().size(); i++) {

                    catger = catger + body.getData().getCategories().get(i).getName() + "-";

                }
                txtResturantInfoType.setText(catger);

                if (body.getData().getAvailability().equals("open")) {
                    txtResturantInfostatus.setText("مفتوح");
                    Resturantswitch1.setChecked(true);

                } else {
                    txtResturantInfostatus.setText("مقفول");
                    Resturantswitch1.setChecked(false);
                }


                txtResturantInfocity.setText("المدينة : " + body.getData().getRegion().getCity().getName());
                txtResturantInfoarea.setText("الحى : " + body.getData().getRegion().getName());
                txtResturantInfominimum.setText("الحد الادنى للطلب : " + body.getData().getMinimumCharger() + " ج ");
                txtResturantInfoareaprice.setText(" رسوم التوصيل : " + body.getData().getDeliveryCost() + " ج ");

            }

            @Override
            public void onFailure(Call<ResturantDetails> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
