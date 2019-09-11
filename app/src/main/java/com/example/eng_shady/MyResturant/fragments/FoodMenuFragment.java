package com.example.eng_shady.MyResturant.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.FoodMenuTabAdapter;

import java.text.Normalizer;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class FoodMenuFragment extends Fragment {


    public int id;
    public String ResName;
    public String From;


    public FoodMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food_menu, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("قايمة الطعام");

        ViewPager viewPager = v.findViewById(R.id.FoodMenuViewPagger);
        TabLayout tabLayout = v.findViewById(R.id.FoodMenutabs);
        FoodMenuTabAdapter tabPagerAdapter = new FoodMenuTabAdapter(getFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("myRef", MODE_PRIVATE).edit();
        editor.putInt("idRes", id);
        editor.putString("ResName", ResName);
        editor.putString("From", From);
        editor.apply();


        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                   // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                   MainClientFragment main = new MainClientFragment();
                   Replace(main,R.id.FragmentLoad,getActivity().getSupportFragmentManager().beginTransaction());
                    return true;
                }
                return false;
            }
        });
        return v;
    }


}
