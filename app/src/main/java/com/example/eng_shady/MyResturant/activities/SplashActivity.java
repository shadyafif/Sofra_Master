package com.example.eng_shady.MyResturant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.fragments.MainClientFragment;
import com.example.eng_shady.MyResturant.models.Settings;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    Button btnLoginFoodReq, btnLoginFoodSell;
    Intent intent;
    CircleImageView ImgLoginTwitter, ImgLoginInsta;
    String Twitter, Insta;
    private ConstraintLayout ConstraianLayoutSplash;

    MainClientFragment mainClientFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Code for animation splash
        ConstraianLayoutSplash = findViewById(R.id.ConstraianLayoutSplash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        ConstraianLayoutSplash.setAnimation(animation);

        GetUrls();

        btnLoginFoodReq = findViewById(R.id.btnLoginFoodReq);
        btnLoginFoodSell = findViewById(R.id.btnLoginFoodSell);
        ImgLoginTwitter = findViewById(R.id.ImgLoginTwitter);
        ImgLoginInsta = findViewById(R.id.ImgLoginInsta);


        btnLoginFoodReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent = new Intent(SplashActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });

        btnLoginFoodSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("UserProfile", Context.MODE_PRIVATE).edit();

                editor.putBoolean("check", false);
                editor.apply();
                intent = new Intent(SplashActivity.this, ResturantActivity.class);
                startActivity(intent);

            }
        });

        GetUrls();

        ImgLoginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTwitterApp = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
                startActivity(launchTwitterApp);

            }
        });

        ImgLoginInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTwitterApp = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                startActivity(launchTwitterApp);

            }
        });


    }

    public void GetUrls() {

        final Api dataservices2 = RetrofitClient.getInstance().getApi();
        Call<Settings> call = dataservices2.getSetting();
        call.enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings setting = response.body();
                Twitter = setting.getData().getTwitter();
                Insta = setting.getData().getInstagram();

            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {

            }
        });

    }
}
