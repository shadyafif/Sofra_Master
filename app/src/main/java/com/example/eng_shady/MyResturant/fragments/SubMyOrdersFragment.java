package com.example.eng_shady.MyResturant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.MyOrdersRecAdapter;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Client.Orders.Datum;
import com.example.eng_shady.MyResturant.models.Client.Orders.MyOrders;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubMyOrdersFragment extends Fragment {

    public String From;
    List<Datum> MyOrderList;
    RecyclerView MyOrdersRec;
    MyOrdersRecAdapter myOrdersRecAdapter;
    Button btnMyOrdersDileverd,btnMyOrdersRefusal;
    int max_page;

    public SubMyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_my_orders, container, false);

        MyOrderList = new ArrayList<>();
        MyOrdersRec = v.findViewById(R.id.MyOrdersRec);
        btnMyOrdersDileverd=v.findViewById(R.id.btnMyOrdersDileverd);
        btnMyOrdersRefusal=v.findViewById(R.id.btnMyOrdersRefusal);

//        if(From=="completed")
//        {
//            btnMyOrdersDileverd.setVisibility(View.GONE);
//            btnMyOrdersRefusal.setVisibility(View.GONE);
//        }
//        else {
//            btnMyOrdersDileverd.setVisibility(View.VISIBLE);
//            btnMyOrdersRefusal.setVisibility(View.VISIBLE);
//        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        MyOrdersRec.setLayoutManager(layoutManager);

        OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {
                    Toast.makeText(getActivity(), "لا يوجد طلبات جديدة", Toast.LENGTH_SHORT).show();
                }
            }
        };

        MyOrdersRec.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);
        return v;
    }

    private void getRecyclerView(final int page) {
        Call<MyOrders> call = RetrofitClient.getInstance().getApi().getOrders(
                "HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB",From, page);
        call.enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {

                MyOrders body = response.body();
                viewResponse(body, page);
                max_page = response.body().getData().getLastPage();


            }

            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewResponse(MyOrders body, int page) {
        if (page == 1) {

            MyOrderList = body.getData().getData();
            myOrdersRecAdapter = new MyOrdersRecAdapter(getContext(), MyOrderList);
            MyOrdersRec.setAdapter(myOrdersRecAdapter);
        } else {
            MyOrderList.addAll(body.getData().getData());
            myOrdersRecAdapter.notifyDataSetChanged();
        }

    }

}
