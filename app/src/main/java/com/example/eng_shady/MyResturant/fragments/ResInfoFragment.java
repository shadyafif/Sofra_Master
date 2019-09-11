package com.example.eng_shady.MyResturant.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.ResturantD.ResturantDetails;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResInfoFragment extends Fragment {

    ImageView imgResInfo;
    TextView txtResInfoName, txtResInfostatus, txtResInfoType,
            txtResInfocity, txtResInfoarea, txtResInfominimum, txtResInfoareaprice;

    RatingBar ResInforatingBar;
    Switch aswitch;

    Button ResInfoSave;

    String catger = "";
    String ComeFrom;
    private ProgressDialog mProgress;

    public ResInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_res_info, container, false);
        imgResInfo = v.findViewById(R.id.imgResInfo);
        txtResInfoName = v.findViewById(R.id.txtResInfoName);
        txtResInfostatus = v.findViewById(R.id.txtResInfostatus);
        txtResInfoType = v.findViewById(R.id.txtResInfoType);
        txtResInfominimum = v.findViewById(R.id.txtResInfominimum);
        txtResInfoareaprice = v.findViewById(R.id.txtResInfoareaprice);
        txtResInfocity = v.findViewById(R.id.txtResInfocity);
        txtResInfoarea = v.findViewById(R.id.txtResInfoarea);
        ResInforatingBar = v.findViewById(R.id.ResInforatingBar);


        mProgress = new ProgressDialog(getContext());

        mProgress.setMessage("جارى تحميل البيانات.....");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        GetResInfo();
        return v;
    }

    public void GetResInfo() {
        mProgress.show();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("myRef", MODE_PRIVATE);
        int idName = prefs.getInt("idRes", 0);
        ComeFrom = prefs.getString("From", "");

        Call<ResturantDetails> call = RetrofitClient.getInstance().getApi().GetResDetails(idName);
        call.enqueue(new Callback<ResturantDetails>() {
            @Override
            public void onResponse(Call<ResturantDetails> call, Response<ResturantDetails> response) {
                mProgress.dismiss();
                ResturantDetails body = response.body();



                String web = "http://ipda3.com/sofra/" + body.getData().getPhoto();
                web = web.replace(" ", "%20");

                Glide.with(getContext()).load(web)

                        .into(imgResInfo);

                txtResInfoName.setText("إسم المطعم : " + body.getData().getName());
                int ResRate = body.getData().getRate();
                ResInforatingBar.setRating(ResRate);

                for (int i = 0; i < body.getData().getCategories().size(); i++) {

                    catger = catger + body.getData().getCategories().get(i).getName() + "-";

                }
                txtResInfoType.setText(catger);

                if (body.getData().getAvailability().equals("open")) {
                    txtResInfostatus.setText("مفتوح");

                } else {
                    txtResInfostatus.setText("مقفول");

                }


                txtResInfocity.setText("المدينة : " + body.getData().getRegion().getCity().getName());
                txtResInfoarea.setText("الحى : " + body.getData().getRegion().getName());
                txtResInfominimum.setText("الحد الادنى للطلب : " + body.getData().getMinimumCharger() + " ج ");
                txtResInfoareaprice.setText(" رسوم التوصيل : " + body.getData().getDeliveryCost() + " ج ");

            }

            @Override
            public void onFailure(Call<ResturantDetails> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
