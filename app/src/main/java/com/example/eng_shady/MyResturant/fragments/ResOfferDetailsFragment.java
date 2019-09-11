package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eng_shady.MyResturant.R;



public class ResOfferDetailsFragment extends Fragment {

    public   String ResOfferName,ResOfferDescription,ResOfferStart,ResOfferEnd,ResOfferPath,ResOfferPrice;
    public  int ResOfferId;


    public ResOfferDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_res_offer_details, container, false);
        return  v;
    }

}
