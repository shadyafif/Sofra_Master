package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.TabPagerAdapter;


public class ContactUsFragment extends Fragment {

    TextView txtComplaintTitle;


    public ContactUsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("تواصل معنا");
        ViewPager viewPager = v.findViewById(R.id.viewpager);
        TabLayout tabLayout = v.findViewById(R.id.tabs);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;


    }


}
