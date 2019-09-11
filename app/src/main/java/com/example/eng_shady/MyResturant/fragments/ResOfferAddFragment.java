package com.example.eng_shady.MyResturant.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eng_shady.MyResturant.R;

import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.AddOffer.OfferAdd;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.UpdateOffer.OffersUpdate;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class ResOfferAddFragment extends Fragment {


    @BindView(R.id.txtResturantOfferAddName)
    TextView txtResturantOfferAddName;
    @BindView(R.id.txtResturantOfferAddDescription)
    EditText txtResturantOfferAddDescription;
    @BindView(R.id.txtResturantOfferAddPrice)
    EditText txtResturantOfferAddPrice;
    @BindView(R.id.txtResturantOfferAddStart)
    TextView txtResturantOfferAddStart;
    @BindView(R.id.txtResturantOfferAddEnd)
    TextView txtResturantOfferAddEnd;
    @BindView(R.id.txtResturantOfferAddImage)
    TextView txtResturantOfferAddImage;
    @BindView(R.id.ResturantOfferAddImage)
    ImageView ResturantOfferAddImage;
    @BindView(R.id.btnResturantOfferAdd)
    Button btnResturantOfferAdd;
    Unbinder unbinder;
    private Album album;
    private String path;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();

    Calendar CalendarStart, CalendarEnd;
    DatePickerDialog.OnDateSetListener dateStart, dateEnd;

    String name, description, price, startat, endAt, imgpath;
    int OfferId;
    String From = "add";


    public ResOfferAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_res_offer_add, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("إضافة عرض");
        unbinder = ButterKnife.bind(this, v);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);
        String ResName = prefs.getString("ResturantName", "");
        txtResturantOfferAddName.setText(ResName);
        CalendarCreate();
        if (From.equals("update")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("تعديل عرض");
            ShowDetails();
        }

        return v;
    }

    public static RequestBody convertToRequestBody(String part) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
        return requestBody;
    }

    public static MultipartBody.Part getImageToUpload(String pathImageFile, String Key) {

        File file = new File(pathImageFile);

        RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);

        return Imagebody;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    private void addImage() {
        album = new Album();
        Album.initialize(AlbumConfig.newBuilder(getActivity())
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.ENGLISH).build());
        album.image(this)// Image and video mix options.
                .multipleChoice()// Multi-Mode, Single-Mode: singleChoice().
                .columnCount(3) // The number of columns in the page list.
                .selectCount(1)  // Choose up to a few images.
                .camera(true) // Whether the camera appears in the Item.
                .checkedList(ImagesFiles) // To reverse the list.
                .widget(Widget.newLightBuilder(getActivity())
                        .title("")
                        .statusBarColor(Color.WHITE) // StatusBar color.
                        .toolBarColor(Color.WHITE) // Toolbar color.
                        .navigationBarColor(Color.WHITE) // Virtual NavigationBar color of Android5.0+.
                        .mediaItemCheckSelector(Color.BLUE, Color.GREEN) // Image or video selection box.
                        .bucketItemCheckSelector(Color.RED, Color.YELLOW) // Select the folder selection box.
                        .build())
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        // TODO accept the result.
                        ImagesFiles = new ArrayList<>();
                        ImagesFiles.addAll(result);
                        path = result.get(0).getPath();
                        Glide.with(getActivity())
                                .load(path)

                                .into(ResturantOfferAddImage);

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        // The user canceled the operation.
                    }
                })
                .start();

    }

    public class MediaLoader implements AlbumLoader {

        @Override
        public void load(ImageView imageView, AlbumFile albumFile) {
            load(imageView, albumFile.getPath());
        }

        @Override
        public void load(ImageView imageView, String url) {
            Glide.with(imageView.getContext())
                    .load(url)

                    .into(imageView);
        }
    }





    public void CalendarCreate() {
        CalendarStart = Calendar.getInstance();
        dateStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                CalendarStart.set(Calendar.YEAR, year);
                CalendarStart.set(Calendar.MONTH, monthOfYear);
                CalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }


        };


        txtResturantOfferAddStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateStart, CalendarStart
                        .get(Calendar.YEAR), CalendarStart.get(Calendar.MONTH),
                        CalendarStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        CalendarEnd = Calendar.getInstance();

        dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                CalendarEnd.set(Calendar.YEAR, year);
                CalendarEnd.set(Calendar.MONTH, month);
                CalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelLast();
            }
        };


        txtResturantOfferAddEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(getActivity(), dateEnd, CalendarEnd
                        .get(Calendar.YEAR), CalendarEnd.get(Calendar.MONTH),
                        CalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateLabelLast() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtResturantOfferAddEnd.setText(sdf.format(CalendarEnd.getTime()));
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtResturantOfferAddStart.setText(sdf.format(CalendarStart.getTime()));
    }

    @OnClick({R.id.txtResturantOfferAddImage, R.id.btnResturantOfferAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtResturantOfferAddImage:
                addImage();


                break;
            case R.id.btnResturantOfferAdd:
                AddOffer();
                break;
        }
    }

    private void AddOffer() {

        if (From.equals("add")) {
            SharedPreferences prefs = getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);

            String Apitoken = prefs.getString("Apitoken", "");

            RequestBody name = convertToRequestBody(txtResturantOfferAddName.getText().toString());
            RequestBody Description = convertToRequestBody(txtResturantOfferAddDescription.getText().toString());
            RequestBody Start = convertToRequestBody(txtResturantOfferAddStart.getText().toString());
            RequestBody End = convertToRequestBody(txtResturantOfferAddEnd.getText().toString());
            RequestBody Price = convertToRequestBody(txtResturantOfferAddPrice.getText().toString());
            MultipartBody.Part File = getImageToUpload(path, "photo");

            Call<OfferAdd> call = RetrofitClient.getInstance().getApi().
                    AddOffer(Description, Price, Start, name, File, End, convertToRequestBody(Apitoken));
            call.enqueue(new Callback<OfferAdd>() {
                @Override
                public void onResponse(Call<OfferAdd> call, Response<OfferAdd> response) {
                    OfferAdd NI = response.body();
                    if (NI.getStatus() == 1) {
                        ResturantOfferFragment Offer = new ResturantOfferFragment();
                        Replace(Offer, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());

                    } else

                    {
                        Toast.makeText(getContext(), NI.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OfferAdd> call, Throwable t) {

                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {

            SharedPreferences prefs = this.getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);

            String Apitoken = prefs.getString("Apitoken", "");

            RequestBody name = convertToRequestBody(txtResturantOfferAddName.getText().toString());
            RequestBody Description = convertToRequestBody(txtResturantOfferAddDescription.getText().toString());
            RequestBody Start = convertToRequestBody(txtResturantOfferAddStart.getText().toString());
            RequestBody End = convertToRequestBody(txtResturantOfferAddEnd.getText().toString());
            RequestBody Price = convertToRequestBody(txtResturantOfferAddPrice.getText().toString());
            MultipartBody.Part File = getImageToUpload(path, "photo");

            Call<OffersUpdate> call = RetrofitClient.getInstance().getApi().
                    getUpadteOffer(Description, Price, Start, name, File, End, convertToRequestBody(String.valueOf(OfferId)), convertToRequestBody(Apitoken));
            call.enqueue(new Callback<OffersUpdate>() {
                @Override
                public void onResponse(Call<OffersUpdate> call, Response<OffersUpdate> response) {
                    OffersUpdate NI = response.body();
                    if (NI.getStatus() == 1) {
                        ResturantOfferFragment ResOffer = new ResturantOfferFragment();
                        Replace(ResOffer, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());

                    } else

                    {
                        Toast.makeText(getContext(), NI.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OffersUpdate> call, Throwable t) {

                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }


    }


    public void ShowDetails() {
        btnResturantOfferAdd.setText("تعديل");
        txtResturantOfferAddName.setText(name);
        txtResturantOfferAddDescription.setText(description);
        txtResturantOfferAddPrice.setText(price);

        String startdate = startat;
        String[] s = startdate.split(" ", 2);

        String enddate = endAt;
        String[] w = enddate.split(" ", 2);

        txtResturantOfferAddStart.setText(s[0]);
        txtResturantOfferAddEnd.setText(w[0]);


        Glide.with(getActivity()).load("http://ipda3.com/sofra/" + imgpath)


                .into(ResturantOfferAddImage);


    }



}
