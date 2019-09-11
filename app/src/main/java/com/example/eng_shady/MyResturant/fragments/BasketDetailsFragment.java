package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.Room.RoomDao;
import com.example.eng_shady.MyResturant.adapters.Room.RoomManger;
import com.example.eng_shady.MyResturant.adapters.UserBasketAdabter;
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class BasketDetailsFragment extends Fragment {


    @BindView(R.id.user_basket_rv)
    RecyclerView userBasketRv;
    @BindView(R.id.user_basket_cost)
    TextView userBasketCost;
    @BindView(R.id.user_basket_addMore_btn)
    Button userBasketAddMoreBtn;
    @BindView(R.id.user_basket_doRequest_btn)
    Button userBasketDoRequestBtn;

    @BindView(R.id.ImgRecPlus)
    ImageView ImgRecPlus;

    @BindView(R.id.ImgRecMinus)
    ImageView ImgRecMinus;

    @BindView(R.id.txtBasketNum)
    TextView txtBasketNum;

    Unbinder unbinder;

    public int OrderNum = 1;

    UserBasketAdabter userBasketAdabter;
    private RoomDao roomDao;
    private List<ItemFoodData> All;

    public BasketDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_basket_details, container, false);
        unbinder = ButterKnife.bind(this, v);

        RoomManger roomManger = RoomManger.getInstance(getActivity());
        roomDao = roomManger.roomDao();

        ShowFoodItems();
        return v;
    }

    private void ShowFoodItems() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                All = roomDao.getAllItem();

                userBasketAdabter = new UserBasketAdabter(getContext(), All);


                userBasketRv.setLayoutManager(new LinearLayoutManager(getActivity()));

                userBasketRv.setAdapter(userBasketAdabter);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_basket_addMore_btn, R.id.user_basket_doRequest_btn, R.id.ImgRecPlus, R.id.ImgRecMinus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_basket_addMore_btn:
                break;
            case R.id.user_basket_doRequest_btn:
                break;
            case R.id.ImgRecPlus:
                OrderNum++;
                txtBasketNum.setText(String.valueOf(OrderNum));
                break;
            case R.id.ImgRecMinus:

                if (OrderNum == 1) {
                    txtBasketNum.setText("1");
                } else {
                    OrderNum--;
                    txtBasketNum.setText(String.valueOf(OrderNum));
                }
                break;
        }
    }
}
