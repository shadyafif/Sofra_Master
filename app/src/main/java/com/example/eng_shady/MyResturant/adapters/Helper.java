package com.example.eng_shady.MyResturant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ENG-SHADY on 12/31/2018.
 */

public class Helper {

    public static void Replace(Fragment fragment, int id, FragmentTransaction fragmentTransaction) {
        FragmentTransaction transaction = fragmentTransaction;
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
