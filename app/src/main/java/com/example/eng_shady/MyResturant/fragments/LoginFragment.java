package com.example.eng_shady.MyResturant.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.activities.ResturantActivity;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Client.Login.ClientLogin;
import com.example.eng_shady.MyResturant.models.Resturant.Login.ResturantLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    Button btncreateAccount, BtnLogin;
    TextView txtforget, txtEmailerror, txtPasserror;
    EditText txtLoginemail, txtLoginpass;
    public SharedPreferences.Editor editor;

    public ClientActivity clientActivity;
    private SignUpFragment SignUpFragment;
    private ProgressDialog mProgress;
    public int x;
    public String Form;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        txtLoginemail = v.findViewById(R.id.txtLoginemail);
        txtLoginpass = v.findViewById(R.id.txtLoginpass);
        txtforget = v.findViewById(R.id.txtforget);
        txtPasserror = v.findViewById(R.id.txtPasserror);
        txtEmailerror = v.findViewById(R.id.txtEmailerror);
        txtforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientActivity ss = (ClientActivity) getActivity();
                ResetPassFragment Fragment = new ResetPassFragment();
                Replace(Fragment, R.id.FragmentLoad, ss.getSupportFragmentManager().beginTransaction());

            }
        });
        mProgress = new ProgressDialog(getContext());

        mProgress.setMessage("برجاء الإنتظار.....");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        BtnLogin = v.findViewById(R.id.BtnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userlogin();

            }
        });

        btncreateAccount = v.findViewById(R.id.btncreateAccount);
        btncreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Form == "Resturant") {
                    ResturantActivity mm = (ResturantActivity) getActivity();
                    ResturantSignUpForm1Fragment Fragment = new ResturantSignUpForm1Fragment();
                    Replace(Fragment, R.id.FragmentLoadRes, mm.getSupportFragmentManager().beginTransaction());
                } else {
                    ClientActivity ss = (ClientActivity) getActivity();
                    SignUpFragment = new SignUpFragment();
                    Replace(SignUpFragment, R.id.FragmentLoad, ss.getSupportFragmentManager().beginTransaction());
                }

            }
        });


        return v;
    }

    public void userlogin() {
        mProgress.show();
        String Email = txtLoginemail.getText().toString().trim();
        String Pass = txtLoginpass.getText().toString().trim();

        if (Email.isEmpty()) {
            txtEmailerror.setText("يرجى إدخال عنوان بريد إلكترونى");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            txtEmailerror.setText("الرجاء إدخال عنوان بريد إلكترونى صحيح");
            txtLoginemail.requestFocus();
            return;
        }


        if (Pass.isEmpty()) {
            txtPasserror.setText("يرجى إدخال كلمة المرور");

            return;

        }

        if (Form == "Resturant") {

            Call<ResturantLogin> call = RetrofitClient.getInstance().getApi().getResLogin(Email, Pass);
            call.enqueue(new Callback<ResturantLogin>() {
                @Override
                public void onResponse(Call<ResturantLogin> call, Response<ResturantLogin> response) {
                    mProgress.dismiss();
                    ResturantLogin RL = response.body();
                    if (RL.getStatus() != 0) {
                        SharedPreferences.Editor editor =getActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE).edit();
                        editor.putBoolean("check", true);
                        editor.apply();

                        ResturantInfoFragment ResturantInfo = new ResturantInfoFragment();
                        ResturantInfo.ResturantId = RL.getData().getUser().getId();
                        ResturantInfo.ResturantName = RL.getData().getUser().getName();
                        ResturantInfo.ApiToken = RL.getData().getApiToken();

                        Replace(ResturantInfo, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());


                    } else {
                        Toast.makeText(getContext(), RL.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<ResturantLogin> call, Throwable t) {

                }
            });

        } else {
            Call<ClientLogin> call = RetrofitClient.getInstance().getApi().userLogin(Email, Pass);
            call.enqueue(new Callback<ClientLogin>() {
                @Override
                public void onResponse(Call<ClientLogin> call, Response<ClientLogin> response) {

                    mProgress.dismiss();
                    ClientLogin CL = response.body();
                    if (CL.getStatus() != 0) {
                        Intent intent = new Intent(getContext(), ClientActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "الإسم أو كلمة المرور غير صحيحة", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<ClientLogin> call, Throwable t) {

                }
            });
        }


    }

}
