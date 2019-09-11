package com.example.eng_shady.MyResturant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.eng_shady.MyResturant.fragments.CommentsFragment;
import com.example.eng_shady.MyResturant.fragments.FoodFragment;
import com.example.eng_shady.MyResturant.fragments.ResInfoFragment;

/**
 * Created by ENG-SHADY on 1/12/2019.
 */

public class FoodMenuTabAdapter extends FragmentStatePagerAdapter {

    String[] TabArray = new String[]{"قائمة الطعام", " التعليقات والتقييمات","معلومات المتجر" };
    Integer num = 3;
    public FoodMenuTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public CharSequence getPageTitle(int position) {
        return TabArray[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                FoodFragment one = new FoodFragment();

                return one;

            case 1:
                CommentsFragment Two = new CommentsFragment();
                // SuggestionFragment Two = new SuggestionFragment();
                return Two;

            case 2:
                ResInfoFragment  three = new ResInfoFragment();
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
