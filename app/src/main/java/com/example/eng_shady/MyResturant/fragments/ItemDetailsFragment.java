package com.example.eng_shady.MyResturant.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
import com.example.eng_shady.MyResturant.adapters.Room.RoomDao;
import com.example.eng_shady.MyResturant.adapters.Room.RoomManger;
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class ItemDetailsFragment extends Fragment {

    public int itemId;
    public int OrderNum = 1;
    public String ItemImage, ItemName, ItemDescription, ItemPrice, ItemPreperingTime;
    @BindView(R.id.ItemDetailsImage)
    ImageView ItemDetailsImage;
    @BindView(R.id.txtItemDetailsTitle)
    TextView txtItemDetailsTitle;
    @BindView(R.id.txtItemDetailsDescription)
    TextView txtItemDetailsDescription;
    @BindView(R.id.txtItemDetailsPrepering)
    TextView txtItemDetailsPrepering;
    @BindView(R.id.txtItemDetailsDescriptionorder)
    TextView txtItemDetailsDescriptionorder;
    @BindView(R.id.txtItemDetailspriceNum)
    TextView txtItemDetailspriceNum;
    @BindView(R.id.txtDetailsSpecialOrder)
    EditText txtDetailsSpecialOrder;
    @BindView(R.id.tztDetailsNum)
    EditText tztDetailsNum;
    @BindView(R.id.addItemDetais)
    Button addItemDetais;
    @BindView(R.id.ImgDetailsPlus)
    CircleImageView ImgDetailsPlus;
    @BindView(R.id.ImgDetailsMinus)
    CircleImageView ImgDetailsMinus;
    Unbinder unbinder;
    private String tag;
    private RoomDao roomDao;
    public ItemFoodData itemFoodData;
    private List<ItemFoodData> All;

    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" تفاصيل المنتج");
        unbinder = ButterKnife.bind(this, view);
        RoomManger roomManger = RoomManger.getInstance(getContext());
        roomDao = roomManger.roomDao();
        GetDetails();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    SharedPreferences prefs = getContext().getSharedPreferences("myRef", MODE_PRIVATE);
                    int idName = prefs.getInt("idRes", 0);
                    FoodMenuFragment food = new FoodMenuFragment();
                    food.id = idName;
                    Replace(food, R.id.FragmentLoad, getActivity().getSupportFragmentManager().beginTransaction());
                    return true;
                }
                return false;
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void GetDetails() {
        txtItemDetailsTitle.setText(ItemName);
        txtItemDetailsDescription.setText(ItemDescription);
        txtItemDetailspriceNum.setText(ItemPrice);
        txtItemDetailsPrepering.setText("مدة تجهيز الطلب :" + ItemPreperingTime + " دقيقة ");
        Glide.with(getActivity()).load(ItemImage)

                .into(ItemDetailsImage);
    }


    @OnClick({R.id.addItemDetais, R.id.ImgDetailsPlus, R.id.ImgDetailsMinus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addItemDetais:
                add(itemFoodData);
                break;
            case R.id.ImgDetailsPlus:
                OrderNum++;
                tztDetailsNum.setText(String.valueOf(OrderNum));
                break;
            case R.id.ImgDetailsMinus:
                if (OrderNum == 1) {
                    tztDetailsNum.setText("1");
                } else {
                    OrderNum--;
                    tztDetailsNum.setText(String.valueOf(OrderNum));
                }
                break;
        }
    }


    private void add(final ItemFoodData foodItem) {

        Executors.newSingleThreadExecutor().execute(new Runnable() {

            @Override

            public void run() {

                ItemFoodData newfoodItem = foodItem;

                String count = tztDetailsNum.getText().toString();
//                newfoodItem.setCounter(Integer.parseInt(count));
//
                roomDao.insertItemToCar(foodItem);

                All=roomDao.getAllItem();
                int size = All.size();
                Toast.makeText(getContext(), ""+size, Toast.LENGTH_SHORT).show();

//                BasketDetailsFragment userBasketFragment = new BasketDetailsFragment();
//
//
//                Replace(userBasketFragment,R.id.FragmentLoad,getActivity().getSupportFragmentManager().beginTransaction());

            }

        });

    }

}

