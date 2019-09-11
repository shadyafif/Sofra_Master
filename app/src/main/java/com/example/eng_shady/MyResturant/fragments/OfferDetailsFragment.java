package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eng_shady.MyResturant.R;


public class OfferDetailsFragment extends Fragment {

  public   String OfferName,OfferDescription,OfferStart,OfferEnd,OfferPath,OfferPrice;
  public  boolean OfferAvaibale;

  ImageView imgOfferDetails;
  TextView txtOfferDetailsName,txtOfferDetailsDescription,txtOfferDetailsStart,
          txtOfferDetailsEnd, txtOfferDetailsAvaiable,txtOfferDetailsPrice;


    public OfferDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_offer_details, container, false);
        imgOfferDetails=v.findViewById(R.id.imgOfferDetails);
        txtOfferDetailsName=v.findViewById(R.id.txtOfferDetailsName);
        txtOfferDetailsDescription=v.findViewById(R.id.txtOfferDetailsDescription);
        txtOfferDetailsStart=v.findViewById(R.id.txtOfferDetailsStart);
        txtOfferDetailsEnd=v.findViewById(R.id.txtOfferDetailsEnd);
        txtOfferDetailsAvaiable=v.findViewById(R.id.txtOfferDetailsAvaiable);
        txtOfferDetailsPrice=v.findViewById(R.id.txtOfferDetailsPrice);
        GetOfferDetails();
        return v;
    }

    public  void GetOfferDetails()
    {
        txtOfferDetailsName.setText(" إسم المطعم : "+OfferName);
        txtOfferDetailsDescription.setText(" تفاصيل العرض : "+OfferDescription);

        String startdate = OfferStart;
        String[] s = startdate.split(" ", 2);

        String enddate =OfferEnd;
        String[] w = enddate.split(" ", 2);

        txtOfferDetailsStart.setText("بداية العرض :" +s[0]);
        txtOfferDetailsEnd.setText("نهاية  العرض :" + w[0]);

        if(OfferAvaibale==true)
        {
            txtOfferDetailsAvaiable.setText("العرض مستمر");
        }
        else
        {
            txtOfferDetailsAvaiable.setText("العرض إنتهى");
        }

        txtOfferDetailsPrice.setText(" السعر :"+OfferPrice + " ج") ;

        Glide.with(getActivity()).load(OfferPath)

                .into(imgOfferDetails);

    }

}
