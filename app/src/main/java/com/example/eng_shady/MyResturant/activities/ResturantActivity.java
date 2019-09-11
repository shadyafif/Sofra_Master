package com.example.eng_shady.MyResturant.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.fragments.CommissionFragment;
import com.example.eng_shady.MyResturant.fragments.ContactUsFragment;
import com.example.eng_shady.MyResturant.fragments.LoginFragment;
import com.example.eng_shady.MyResturant.fragments.ResturantCommentsFragment;
import com.example.eng_shady.MyResturant.fragments.ResturantInfoFragment;
import com.example.eng_shady.MyResturant.fragments.ResturantItemsFragment;
import com.example.eng_shady.MyResturant.fragments.ResturantOfferFragment;
import com.example.eng_shady.MyResturant.fragments.TermsFragment;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;

public class ResturantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("الرئيسية");
        setSupportActionBar(toolbar);
        SharedPreferences prefs = ResturantActivity.this.getSharedPreferences("myResturantRef", MODE_PRIVATE);

        String ResName = prefs.getString("ResturantName", "");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.txtPersonName);
        navUsername.setText(ResName);
        navigationView.setNavigationItemSelectedListener(this);

        LoginFragment login = new LoginFragment();
        login.Form = "Resturant";
        Replace(login, R.id.FragmentLoadRes, getSupportFragmentManager().beginTransaction());


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
        getMenuInflater().inflate(R.menu.resturant, menu);
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(ResturantActivity.this);
//            builder.setTitle("تسجيل الخروج");
            builder.setMessage("هل تريد الخروج ؟");
            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
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

        if (id == R.id.nav_ResHome) {
            // Handle the camera action
        } else if (id == R.id.nav_ResProducts) {

            ResturantItemsFragment ResItem = new ResturantItemsFragment();
            Replace(ResItem, R.id.FragmentLoadRes, getSupportFragmentManager().beginTransaction());


        } else if (id == R.id.nav_ResOrders) {

        } else if (id == R.id.nav_ResComments) {
            ResturantCommentsFragment ResturantComment = new ResturantCommentsFragment();
            Replace(ResturantComment, R.id.FragmentLoadRes, getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.nav_ResCommission) {
            CommissionFragment commission = new CommissionFragment();
            Replace(commission, R.id.FragmentLoadRes, getSupportFragmentManager().beginTransaction());


        } else if (id == R.id.nav_ResOffers) {

            ResturantOfferFragment OffersFragment = new ResturantOfferFragment();
            Replace(OffersFragment, R.id.FragmentLoadRes, getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.nav_ResTerms) {

            TermsFragment TermsFragment = new TermsFragment();
            Replace(TermsFragment, R.id.FragmentLoadRes, getSupportFragmentManager().beginTransaction());

        } else if (id == R.id.nav_ResShare) {

        } else if (id == R.id.nav_ResContact) {
            ContactUsFragment ContactUsFragment = new ContactUsFragment();
            Replace(ContactUsFragment, R.id.FragmentLoadRes, getSupportFragmentManager().beginTransaction());

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
