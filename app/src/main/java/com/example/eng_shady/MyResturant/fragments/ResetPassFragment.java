package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Client.Pass.NewPass;
import com.example.eng_shady.MyResturant.models.Client.Pass.ResetPass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class ResetPassFragment extends Fragment {

    EditText txtResetEmail, txtResetCode, txtResetNewPass, txtResetConfirmPass;
    Button BtnCodeSend, btnPsswordChange;

    public ResetPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reset_pass, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("تغيير كلمة المرور");
        txtResetEmail = v.findViewById(R.id.txtResetEmail);
        txtResetCode = v.findViewById(R.id.txtResetCode);
        txtResetNewPass = v.findViewById(R.id.txtResetNewPass);
        txtResetConfirmPass = v.findViewById(R.id.txtResetConfirmPass);


        BtnCodeSend = v.findViewById(R.id.BtnCodeSend);
        btnPsswordChange = v.findViewById(R.id.btnPsswordChange);
        BtnCodeSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeSend();
            }
        });

        btnPsswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPasswordChange();
            }
        });


        return v;
    }


    private void CodeSend() {
        String email = txtResetEmail.getText().toString().trim();
        if (email.isEmpty()) {
            txtResetEmail.setError("هذا الحقل مطلوب");
            txtResetEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtResetEmail.setError("الرجاء إدخال عنوان بريد إلكترونى صحيح");
            txtResetEmail.requestFocus();
            return;
        }


        Call<ResetPass> call = RetrofitClient.getInstance().getApi().resetpass(email);
        call.enqueue(new Callback<ResetPass>() {
            @Override
            public void onResponse(Call<ResetPass> call, Response<ResetPass> response) {
                ResetPass reset = response.body();
                if (reset.getStatus() != 0) {
                    Toast.makeText(getContext(), reset.getMsg(), Toast.LENGTH_SHORT).show();
                    txtResetCode.setEnabled(true);
                    txtResetConfirmPass.setEnabled(true);
                    txtResetNewPass.setEnabled(true);
                    btnPsswordChange.setEnabled(true);

                } else {
                    Toast.makeText(getContext(), "لا يوجد أي حساب مرتبط بهذا البريد الالكتروني", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResetPass> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetPasswordChange() {
        String code = (txtResetCode.getText().toString().trim());
        String newPassword = (txtResetNewPass.getText().toString().trim());
        String ConfirmnewPassword = (txtResetConfirmPass.getText().toString().trim());

        if (code.isEmpty()) {
            txtResetCode.setError("هذا الحقل مطلوب");
            txtResetCode.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            txtResetNewPass.setError("هذا الحقل مطلوب");
            txtResetNewPass.requestFocus();
            return;
        }
        if (ConfirmnewPassword.isEmpty()) {
            txtResetConfirmPass.setError("هذا الحقل مطلوب");
            txtResetConfirmPass.requestFocus();
            return;
        }

        if (!newPassword.equals(ConfirmnewPassword)) {
            txtResetConfirmPass.setError("التأكيد غير متطابق ");
            txtResetConfirmPass.requestFocus();
            return;

        }

        Call<NewPass> call = RetrofitClient.getInstance().getApi().
                newPass(newPassword, ConfirmnewPassword, code);

        call.enqueue(new Callback<NewPass>() {
            @Override
            public void onResponse(Call<NewPass> call, Response<NewPass> response) {

                NewPass newPass = response.body();
                if(newPass.getStatus()!=0) {
                    ClientActivity Client = (ClientActivity) getActivity();
                    LoginFragment login = new LoginFragment();
                    Replace(login,R.id.FragmentLoad,Client.getSupportFragmentManager().beginTransaction());
                }
                else
                {
                    Toast.makeText(getContext(), "هذا الكود غير صالح", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewPass> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}