package com.example.eng_shady.MyResturant.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.TextView;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;

import com.example.eng_shady.MyResturant.adapters.OnEndless;

import com.example.eng_shady.MyResturant.adapters.ResturantOffersAdapter;
import com.example.eng_shady.MyResturant.adapters.SwipeController;
import com.example.eng_shady.MyResturant.adapters.SwipeControllerActions;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.DeleteOffer.OffersDelete;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.NewOffer.Datum;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.NewOffer.ResturantOffers;


import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantOfferFragment extends Fragment {
    List<Datum> OffersList;
    RecyclerView recyclerView;
    ResturantOffersAdapter recyclerAdapter;
    int max_page;
    SwipeController swipeController = null;
    private ProgressDialog mProgress;

    public ResturantOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resturant_offer, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" عروضى");
        SharedPreferences prefs = getActivity().getSharedPreferences("UserProfile", MODE_PRIVATE);
        Boolean check = prefs.getBoolean("check", false);
        if (check == false) {
            LoginFragment login = new LoginFragment();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" تسجيل الدخول");
            login.Form = "Resturant";
            Replace(login, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());
        } else {


            FloatingActionButton fab = v.findViewById(R.id.ResturantOffersfab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ResOfferAddFragment ResOffer = new ResOfferAddFragment();
                    Replace(ResOffer, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());

                }
            });


            recyclerView = v.findViewById(R.id.ResturantOffersRec);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            OffersList = new ArrayList<>();

            OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
                @Override
                public void onLoadMore(int current_page) {
                    if (current_page < max_page) {
                        getRecyclerView(current_page);
                    } else {
                        Toast.makeText(getActivity(), "لا يوجد عروض جديدة", Toast.LENGTH_SHORT).show();
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
        String apitoken = prefs.getString("Apitoken", "");

        Call<ResturantOffers> call = RetrofitClient.getInstance().getApi().GetOffers(apitoken);
        call.enqueue(new Callback<ResturantOffers>() {
            @Override
            public void onResponse(Call<ResturantOffers> call, Response<ResturantOffers> response) {
                ResturantOffers body = response.body();
                viewResponse(body, 1);
                max_page = response.body().getData().getLastPage();
            }

            @Override
            public void onFailure(Call<ResturantOffers> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void viewResponse(ResturantOffers body, int page) {
        if (page == 1) {

            OffersList = body.getData().getData();
            recyclerAdapter = new ResturantOffersAdapter(getContext(), OffersList);
            recyclerView.setAdapter(recyclerAdapter);


            swipeController = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onLeftClicked(int position) {
                    ResOfferAddFragment OfferAdd = new ResOfferAddFragment();
                    OfferAdd.From = "update";
                    OfferAdd.name = OffersList.get(position).getName();
                    OfferAdd.description = OffersList.get(position).getDescription();
                    OfferAdd.price = OffersList.get(position).getPrice();
                    OfferAdd.startat = OffersList.get(position).getStartingAt();
                    OfferAdd.endAt = OffersList.get(position).getEndingAt();
                    OfferAdd.imgpath = OffersList.get(position).getPhoto();
                    OfferAdd.OfferId = OffersList.get(position).getId();
                    Replace(OfferAdd, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());


                }

                @Override
                public void onRightClicked(final int position) {
                    mProgress.show();
                    SharedPreferences prefs = getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);
                    String apitoken = prefs.getString("Apitoken", "");
                    int id = OffersList.get(position).getId();
                    Call<OffersDelete> call = RetrofitClient.getInstance().getApi()
                            .getDelete(id, apitoken);
                    call.enqueue(new Callback<OffersDelete>() {
                        @Override
                        public void onResponse(Call<OffersDelete> call, Response<OffersDelete> response) {
                            mProgress.dismiss();
                            OffersDelete OD = response.body();
                            if (OD.getStatus() == 1) {
                                Toast.makeText(getContext(), OD.getMsg(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), OD.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                            OffersList.remove(OffersList.get(position));
                            recyclerView.removeViewAt(position);
                            recyclerAdapter.notifyItemRemoved(position);
                            recyclerAdapter.notifyItemRangeChanged(position, OffersList.size());
                            recyclerAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(Call<OffersDelete> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

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
            OffersList.addAll(body.getData().getData());
            recyclerAdapter.notifyDataSetChanged();
        }
    }


}
