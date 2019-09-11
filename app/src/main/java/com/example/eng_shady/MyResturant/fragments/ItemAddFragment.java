package com.example.eng_shady.MyResturant.fragments;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ResturantItems;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantItem.Newitem.UpdateItem.ItemsUpdate;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class ItemAddFragment extends Fragment {
    EditText txtResturantItemAddName, txtResturantItemAddDescription,
            txtResturantItemAddPreperingTime, txtResturantItemAddPrice;
    TextView txtResturantItemAddImage;
    ImageView ResturantItemAddImage;
    Button btnResturantItemAdd;

    private Album album;
    private String path;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    DatePickerDialog.OnDateSetListener date, dateLast;

    String name, description, price, PreperingDate, imgpath;

    int ItemId;
    String From = "add";

    public ItemAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_add, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" إضافة منتج ");


        txtResturantItemAddName = v.findViewById(R.id.txtResturantItemAddName);
        txtResturantItemAddDescription = v.findViewById(R.id.txtResturantItemAddDescription);
        txtResturantItemAddPreperingTime = v.findViewById(R.id.txtResturantItemAddPreperingTime);
        txtResturantItemAddPrice = v.findViewById(R.id.txtResturantItemAddPrice);

        ResturantItemAddImage = v.findViewById(R.id.ResturantItemAddImage);
        txtResturantItemAddImage = v.findViewById(R.id.txtResturantItemAddImage);
        txtResturantItemAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });


        btnResturantItemAdd = v.findViewById(R.id.btnResturantItemAdd);
        btnResturantItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem();
            }
        });

        if (From.equals("update")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("تعديل عرض");
            ShowDetails();
        }

        return v;
    }

    private void AddItem() {

        if (From.equals("add")) {
            SharedPreferences prefs = this.getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);

            String Apitoken = prefs.getString("Apitoken", ""); //0 is the default value.


            RequestBody name = convertToRequestBody(txtResturantItemAddName.getText().toString());
            RequestBody Description = convertToRequestBody(txtResturantItemAddDescription.getText().toString());
            RequestBody Time = convertToRequestBody(txtResturantItemAddPreperingTime.getText().toString());
            RequestBody Price = convertToRequestBody(txtResturantItemAddPrice.getText().toString());
            MultipartBody.Part File = getImageToUpload(path, "photo");

            Call<ResturantItems> call = RetrofitClient.getInstance().getApi().
                    AddItem(name, Description, Price, Time, File,
                            convertToRequestBody(Apitoken));
            call.enqueue(new Callback<ResturantItems>() {
                @Override
                public void onResponse(Call<ResturantItems> call, Response<ResturantItems> response) {
                    ResturantItems NI = response.body();
                    if (NI.getStatus() == 1) {
                        Toast.makeText(getContext(), NI.getMsg(), Toast.LENGTH_SHORT).show();
                    } else

                    {
                        Toast.makeText(getContext(), NI.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResturantItems> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            SharedPreferences prefs = this.getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);

            String Apitoken = prefs.getString("Apitoken", ""); //0 is the default value.


            RequestBody name = convertToRequestBody(txtResturantItemAddName.getText().toString());
            RequestBody Description = convertToRequestBody(txtResturantItemAddDescription.getText().toString());
            RequestBody Time = convertToRequestBody(txtResturantItemAddPreperingTime.getText().toString());
            RequestBody Price = convertToRequestBody(txtResturantItemAddPrice.getText().toString());
//            ItemId--> int
            RequestBody Id = convertToRequestBody(String.valueOf(ItemId));
            MultipartBody.Part File = getImageToUpload(path, "photo");

            Call<ItemsUpdate> call = RetrofitClient.getInstance().getApi().
                    getItemUpdate(name,Description,Price,Time,File,convertToRequestBody(Apitoken),ItemId);
            call.enqueue(new Callback<ItemsUpdate>() {
                @Override
                public void onResponse(Call<ItemsUpdate> call, Response<ItemsUpdate> response) {
                    ItemsUpdate IU = response.body();
                    if(IU.getStatus()==1)
                    {
                        ResturantItemsFragment Resitem = new ResturantItemsFragment();
                        Replace(Resitem, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());
                    }
                    else
                    {
                        Toast.makeText(getContext(), IU.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ItemsUpdate> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
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
                                .into(ResturantItemAddImage);

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

    public void ShowDetails() {
        btnResturantItemAdd.setText("تعديل");
        txtResturantItemAddName.setText(name);
        txtResturantItemAddDescription.setText(description);
        txtResturantItemAddPrice.setText(price);
        txtResturantItemAddPreperingTime.setText(PreperingDate);
        Glide.with(getActivity()).load("http://ipda3.com/sofra/" + imgpath)
                .into(ResturantItemAddImage);
    }
}
