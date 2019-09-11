package com.example.eng_shady.MyResturant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.eng_shady.MyResturant.fragments.SubMyOrdersFragment;

public class MyOrdersAdapter extends FragmentStatePagerAdapter {

    String[] TabArray = new String[]{"طلبات سابقة", " طلبات حالية"};
    Integer num = 2;
    public MyOrdersAdapter(FragmentManager fm) {
        super(fm);
    }
    public CharSequence getPageTitle(int position) {
        return TabArray[position];
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SubMyOrdersFragment one = new SubMyOrdersFragment();
                one.From="current";

                return one;

            case 1:
                SubMyOrdersFragment Two = new SubMyOrdersFragment();
                Two.From="completed";
                return Two;






        }
        return null;
    }

    @Override
    public int getCount() {
        return num;
    }
}
