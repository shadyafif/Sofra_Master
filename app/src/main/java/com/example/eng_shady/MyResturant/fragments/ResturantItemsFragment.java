package com.example.eng_shady.MyResturant.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.adapters.ResturantItemAdapter;
import com.example.eng_shady.MyResturant.adapters.SwipeController;
import com.example.eng_shady.MyResturant.adapters.SwipeControllerActions;
import com.example.eng_shady.MyResturant.api.Api;
import com.example.eng_shady.MyResturant.api.RetrofitClient;

import com.example.eng_shady.MyResturant.models.General.ResturantItem.ItemFoodData;
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ResturantItems;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantItem.Newitem.DeleteItem.ItemsDelete;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class ResturantItemsFragment extends Fragment {

    List<ItemFoodData> ResturantsItemList;
    RecyclerView recyclerView;
    ResturantItemAdapter recyclerAdapter;
    int max_page;
    SwipeController swipeController = null;
    private ProgressDialog mProgress;

    public ResturantItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resturant_items, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" منتجاتى");

        SharedPreferences prefs = getActivity().getSharedPreferences("UserProfile", MODE_PRIVATE);
        Boolean check = prefs.getBoolean("check", false);
        if (check == false) {
            LoginFragment login = new LoginFragment();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" تسجيل الدخول");
            login.Form = "Resturant";
            Replace(login, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());
        } else {
            FloatingActionButton fab = v.findViewById(R.id.Resturantitemsfab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemAddFragment ItemAdd = new ItemAddFragment();
                    Replace(ItemAdd, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());
                }
            });
            ResturantsItemList = new ArrayList<>();
            recyclerView = v.findViewById(R.id.ResturantitemsRec);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layoutManager);


            OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
                @Override
                public void onLoadMore(int current_page) {
                    if (current_page < max_page) {
                        getRecyclerView(current_page);
                    } else {
                        Toast.makeText(getActivity(), "إنتهت القائمة ", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            recyclerView.addOnScrollListener(onEndlesslistener);
            getRecyclerView(1);

            mProgress = new ProgressDialog(getContext());

            mProgress.setMessage("برجاء الإنتظار.....");
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(true);
        }


        return v;
    }

    private void getRecyclerView(int current_page) {

        SharedPreferences prefs = this.getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);
        int idName = prefs.getInt("idResturant", 0); //0 is the default value.

        final Api getDataService = RetrofitClient.getInstance().getApi();
        Call<ResturantItems> call = getDataService.GetResItems(idName);
        call.enqueue(new Callback<ResturantItems>() {
            @Override
            public void onResponse(Call<ResturantItems> call, Response<ResturantItems> response) {
                ResturantItems resturantItems = response.body();
                viewResponse(resturantItems, 1);
                max_page = response.body().getData().getLastPage();

            }

            @Override
            public void onFailure(Call<ResturantItems> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void viewResponse(ResturantItems resturantItems, int page) {

        if (page == 1) {

            ResturantsItemList = resturantItems.getData().getData();
            recyclerAdapter = new ResturantItemAdapter(getContext(), ResturantsItemList);
            recyclerView.setAdapter(recyclerAdapter);

            swipeController = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onLeftClicked(final int position) {

                    ItemAddFragment ResItem = new ItemAddFragment();
                    ResItem.name = ResturantsItemList.get(position).getName();
                    ResItem.description = ResturantsItemList.get(position).getDescription();
                    ResItem.price = ResturantsItemList.get(position).getPrice();
                    ResItem.PreperingDate = ResturantsItemList.get(position).getPreparingTime();
                    ResItem.imgpath = ResturantsItemList.get(position).getPhoto();
                    ResItem.ItemId = ResturantsItemList.get(position).getId();
                    ResItem.From = "update";
                    Replace(ResItem, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());

                }

                @Override
                public void onRightClicked(final int position) {
                    mProgress.show();
                    SharedPreferences prefs = getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);
                    String apitoken = prefs.getString("Apitoken", "");
                    int id = ResturantsItemList.get(position).getId();
                    Call<ItemsDelete> call = RetrofitClient.getInstance().getApi().
                            getItemDelete(id, apitoken);
                    call.enqueue(new Callback<ItemsDelete>() {
                        @Override
                        public void onResponse(Call<ItemsDelete> call, Response<ItemsDelete> response) {
                            mProgress.dismiss();
                            ItemsDelete ID = response.body();
                            if (ID.getStatus() == 1) {
                                Toast.makeText(getContext(), ID.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), ID.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                            ResturantsItemList.remove(ResturantsItemList.get(position));
                            recyclerView.removeViewAt(position);
                            recyclerAdapter.notifyItemRemoved(position);
                            recyclerAdapter.notifyItemRangeChanged(position, ResturantsItemList.size());
                            recyclerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ItemsDelete> call, Throwable t) {

                        }
                    });
                }
            });

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
            itemTouchhelper.attachToRecyclerView(recyclerView);

            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    swipeController.onDraw(c);
                }
            });


        } else {

            ResturantsItemList.addAll(resturantItems.getData().getData());
            recyclerAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Toast.makeText(getActivity(), "للعودة للوراء يرجى الضغط على السهم فى اعلى الشاشة على اليمين", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}
