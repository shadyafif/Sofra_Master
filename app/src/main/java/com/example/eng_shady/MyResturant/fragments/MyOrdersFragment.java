package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.MyOrdersAdapter;
import com.example.eng_shady.MyResturant.adapters.TabPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_orders, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("طلباتى");
        ViewPager viewPager = v.findViewById(R.id.MyOrderviewpager);
        TabLayout tabLayout = v.findViewById(R.id.MyOrdertabs);
        MyOrdersAdapter tabPagerAdapter = new MyOrdersAdapter(getFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager) ;
        return v;
    }

}
