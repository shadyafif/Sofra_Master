package com.example.eng_shady.MyResturant.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.eng_shady.MyResturant.R;

import com.example.eng_shady.MyResturant.fragments.AboutAppFragment;

import com.example.eng_shady.MyResturant.fragments.ContactUsFragment;
import com.example.eng_shady.MyResturant.fragments.LoginFragment;

import com.example.eng_shady.MyResturant.fragments.MainClientFragment;
import com.example.eng_shady.MyResturant.fragments.MyOrdersFragment;
import com.example.eng_shady.MyResturant.fragments.OffersFragment;

import com.example.eng_shady.MyResturant.fragments.TermsFragment;
import com.example.eng_shady.MyResturant.fragments.UserNotificationFragment;


import java.io.File;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;

public class ClientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String From = "Client";
    private AboutAppFragment AboutAppFragment;
    private LoginFragment LoginFragment;
    private ContactUsFragment ContactUsFragment;
    private TermsFragment TermsFragment;
    private OffersFragment offersFragment;
    private MainClientFragment mainClientFragment;
    private UserNotificationFragment userNotificationFragment;
    private MyOrdersFragment myOrdersFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mainClientFragment = new MainClientFragment();
        Replace(mainClientFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.micart) {
            return true;
        }
        if (id == R.id.miCompose) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            mainClientFragment = new MainClientFragment();
            Replace(mainClientFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.order) {
            myOrdersFragment = new MyOrdersFragment();
            Replace(myOrdersFragment,R.id.FragmentLoad,getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.notification) {
            userNotificationFragment = new UserNotificationFragment();
            Replace(userNotificationFragment,R.id.FragmentLoad,getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.newOffer) {
            offersFragment = new OffersFragment();
            Replace(offersFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.about) {
            AboutAppFragment = new AboutAppFragment();
            Replace(AboutAppFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.terms) {
            TermsFragment = new TermsFragment();
            Replace(TermsFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());


        } else if (id == R.id.share) {
            try {
                PackageManager pm = getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
                File srcFile = new File(ai.publicSourceDir);
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.android.package-archive");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
                startActivity(Intent.createChooser(share, "PersianCoders"));
            } catch (Exception e) {
                Log.e("ShareApp", e.getMessage());
            }

        } else if (id == R.id.contact) {
            ContactUsFragment = new ContactUsFragment();
            Replace(ContactUsFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navClick(View view) {

        LoginFragment = new LoginFragment();
        Replace(LoginFragment, R.id.FragmentLoad, getSupportFragmentManager().beginTransaction());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}
