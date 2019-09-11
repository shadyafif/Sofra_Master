package com.example.eng_shady.MyResturant.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.ContactUs;
import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Settings;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryFragment extends Fragment {

    CircleImageView ImgComplaintFace, ImgComplaintTwitter,ImgComplaintInsta;
    String face,Twitter, Insta;
    Intent intent;

    Button btnComplaintSend;
    EditText txtComplaintName,txtComplaintEmail,txtComplaintPhone,txtComplaintSms;
    public InquiryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_inquiry, container, false);
        txtComplaintName=v.findViewById(R.id.txtComplaintName);
        txtComplaintEmail=v.findViewById(R.id.txtComplaintEmail);
        txtComplaintPhone=v.findViewById(R.id.txtComplaintPhone);
        txtComplaintSms=v.findViewById(R.id.txtComplaintSms);
        btnComplaintSend=v.findViewById(R.id.btnComplaintSend);
        btnComplaintSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendContent();
            }
        });

        GetUrl();
        ImgComplaintFace=v.findViewById(R.id.ImgComplaintFace);
        ImgComplaintTwitter=v.findViewById(R.id.ImgComplaintTwitter);
        ImgComplaintInsta=v.findViewById(R.id.ImgComplaintInsta);

        ImgComplaintFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(face));
                startActivity(browserIntent);

            }
        });

        ImgComplaintTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Twitter));
                startActivity(browserIntent);

            }
        });

        ImgComplaintInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Insta));
                startActivity(browserIntent);

            }
        });
        return v;
    }

    private void SendContent() {
        String ContactName = txtComplaintName.getText().toString().trim();
        String ContactEmail = txtComplaintEmail.getText().toString().trim();
        String ContactPhone = txtComplaintPhone.getText().toString().trim();
        String type = "inquiry";
        String ContactContent = txtComplaintSms.getText().toString().trim();

        if (ContactName.isEmpty()) {
            txtComplaintName.setError("هذا الحقل مطلوب");
            txtComplaintName.requestFocus();
            return;
        }
        if (ContactEmail.isEmpty()) {
            txtComplaintEmail.setError("هذا الحقل مطلوب");
            txtComplaintEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(ContactEmail).matches()) {
            txtComplaintEmail.setError("يرجى إدخال عنوان بريد إلكترونى صحيح");
            txtComplaintEmail.requestFocus();
            return;
        }
        if (ContactPhone.isEmpty()) {
            txtComplaintPhone.setError("هذا الحقل مطلوب");
            txtComplaintPhone.requestFocus();
            return;
        }


        if (ContactContent.isEmpty()) {
            txtComplaintSms.setError("هذا الحقل مطلوب");
            txtComplaintSms.requestFocus();
            return;
        }

        final Api dataservices2 = RetrofitClient.getInstance().getApi();
        Call<ContactUs> call = dataservices2.contact(ContactName,ContactEmail,ContactPhone,type,ContactContent);
        call.enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                ContactUs CU = response.body();

                Toast.makeText(getContext(), "" + CU.getMsg(), Toast.LENGTH_SHORT).show();

                txtComplaintName.getText().clear();
                txtComplaintEmail.getText().clear();
                txtComplaintPhone.getText().clear();
                txtComplaintSms.getText().clear();

            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

            }
        });
    }

    public void GetUrl()
    {
        final Api dataservices2 = RetrofitClient.getInstance().getApi();
        Call<Settings> call = dataservices2.getSetting();
        call.enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings setting = response.body();
                face=setting.getData().getFacebook();
                Twitter = setting.getData().getTwitter();
                Insta = setting.getData().getInstagram();
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

}
