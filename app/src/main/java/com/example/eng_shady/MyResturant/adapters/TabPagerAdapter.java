package com.example.eng_shady.MyResturant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.eng_shady.MyResturant.fragments.ComplaintFragment;

import com.example.eng_shady.MyResturant.fragments.InquiryFragment;
import com.example.eng_shady.MyResturant.fragments.SuggestionFragment;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

    String[] TabArray = new String[]{"شكوى", " إقتراح","إستعلام" };
    Integer num = 3;


    public TabPagerAdapter(FragmentManager fm) {super(fm);}

    public CharSequence getPageTitle(int position) {
        return TabArray[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InquiryFragment one = new InquiryFragment();

                return one;

            case 1:
                SuggestionFragment Two = new SuggestionFragment();
               // SuggestionFragment Two = new SuggestionFragment();
                return Two;

            case 2:
                ComplaintFragment three = new ComplaintFragment();
               // ComplaintFragment three = new ComplaintFragment();
                return three;




        }
        return null;
    }

    @Override
    public int getCount() {
        return num;
    }
}
