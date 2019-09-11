package com.example.eng_shady.MyResturant.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.eng_shady.MyResturant.R;


import com.example.eng_shady.MyResturant.adapters.MultiSelectionSpinner;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.Category.Categories;
import com.example.eng_shady.MyResturant.models.General.Category.DatumCatagory;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantRegister.ResRegister;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;


import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResturantSignUpForm2Fragment extends Fragment {

    int RegionId;
    private static int RESULT_LOAD_IMAGE = 1;
    String ResturantName, ResturantEmail, ResturantHome, ResturantPass, ResturantConfirm;

    MultiSelectionSpinner spResturantSignForm2Catagory;

    TextView restaurantImage;
    ImageView ResturantSignForm2Image;
    EditText txtResturantSignForm2Mini, txtResturantSignForm2Dilvery, txtResturantSignForm2DilveryTime,
            txtResturantSignForm2whatsApp, txtResturantSignForm2mobile;
    Button btnResturantSignForm2;

    private Album album;
    private String path;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private List<DatumCatagory> CategoriesResponseData = new ArrayList<>();
    final ArrayList<Integer> mUserItems = new ArrayList<>();

    public ResturantSignUpForm2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resturant_sign_up_form2, container, false);

        spResturantSignForm2Catagory = v.findViewById(R.id.spResturantSignForm2Catagory);

        ResturantSignForm2Image = v.findViewById(R.id.ResturantSignForm2Image);

        txtResturantSignForm2Mini = v.findViewById(R.id.txtResturantSignForm2Mini);
        txtResturantSignForm2Dilvery = v.findViewById(R.id.txtResturantSignForm2Dilvery);
        txtResturantSignForm2DilveryTime = v.findViewById(R.id.txtResturantSignForm2DilveryTime);
        txtResturantSignForm2whatsApp = v.findViewById(R.id.txtResturantSignForm2whatsApp);
        txtResturantSignForm2mobile = v.findViewById(R.id.txtResturantSignForm2mobile);


        restaurantImage = v.findViewById(R.id.restaurantImage);
        restaurantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                addImage();


            }
        });

        btnResturantSignForm2=v.findViewById(R.id.btnResturantSignForm2);
        btnResturantSignForm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResturantRegister();
            }
        });


        GetCatagory();
        return v;
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
                                .into(ResturantSignForm2Image);

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
//                  .error(R.drawable.placeholder)
//                  .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }
    }


    public void GetCatagory() {
        Call<Categories> call = RetrofitClient.getInstance().getApi().getCatagory();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                Categories CT = response.body();
                List<DatumCatagory> LisOfCities = CT.getData();
                CategoriesResponseData.addAll(response.body().getData());
                List<String> stringList = new ArrayList<>();
                stringList.add("إختار التخصص");
                final List<Integer> integerList = new ArrayList<>();
                for (int i = 0; i < LisOfCities.size(); i++) {
                    stringList.add(LisOfCities.get(i).getName());
                    integerList.add(LisOfCities.get(i).getId());
                }

                spResturantSignForm2Catagory.setItems(stringList);
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });

    }

    public static MultipartBody.Part getImageToUpload(String pathImageFile, String Key) {

        File file = new File(pathImageFile);

        RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);

        return Imagebody;
    }


    public static RequestBody convertToRequestBody(String part) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
        return requestBody;
    }

    public void ResturantRegister() {
        RequestBody name = convertToRequestBody(ResturantName);
        RequestBody email = convertToRequestBody(ResturantEmail);
        RequestBody pass = convertToRequestBody(ResturantPass);
        RequestBody pass_confirm = convertToRequestBody(ResturantConfirm);
        RequestBody address = convertToRequestBody(ResturantHome);
        final RequestBody region = convertToRequestBody(String.valueOf(RegionId));
        RequestBody phone = convertToRequestBody(txtResturantSignForm2mobile.getText().toString());
        RequestBody whatsapp = convertToRequestBody(txtResturantSignForm2whatsApp.getText().toString());
        RequestBody Mini = convertToRequestBody(txtResturantSignForm2Mini.getText().toString());
        RequestBody Pioad = convertToRequestBody(txtResturantSignForm2DilveryTime.getText().toString());
        RequestBody Delivery = convertToRequestBody(txtResturantSignForm2Dilvery.getText().toString());
        MultipartBody.Part File = getImageToUpload(path, "photo");
        RequestBody open = convertToRequestBody("open");


        ArrayList<RequestBody> category = new ArrayList<>();
        category.add(convertToRequestBody(String.valueOf(mUserItems)));

        List<RequestBody> categoriesItems = new ArrayList<>();

        for (int i = 0; i < spResturantSignForm2Catagory.getSelectedStrings().size(); i++) {
            for (int j = 0; j < CategoriesResponseData.size(); j++) {
                if (spResturantSignForm2Catagory.getSelectedStrings().get(i)
                        .equals(CategoriesResponseData.get(j).getName())) {
                    categoriesItems.add(convertToRequestBody(String.valueOf(CategoriesResponseData.get(i).getId())));
                }
            }
        }

        Call<ResRegister> call = RetrofitClient.getInstance().getApi().restaurantRegister(name, email, pass,
                pass_confirm, phone, address, whatsapp, region, categoriesItems, Pioad, Delivery, Mini, File, open);
        call.enqueue(new Callback<ResRegister>() {
            @Override
            public void onResponse(Call<ResRegister> call, Response<ResRegister> response) {

                ResRegister Res = response.body();
                if (Res.getStatus() == 1) {
                    Toast.makeText(getContext(), "تم تسجيل المطعم", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), Res.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResRegister> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}


