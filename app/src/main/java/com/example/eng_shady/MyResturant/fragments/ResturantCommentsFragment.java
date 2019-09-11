package com.example.eng_shady.MyResturant.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.adapters.ResturantReviewAdapter;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.General.ResturantReview.Comments;
import com.example.eng_shady.MyResturant.models.General.ResturantReview.Datum;

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
public class ResturantCommentsFragment extends Fragment {

    TextView txtResReviewName, txtResReviewdate, txtResReviewdetails;
    RatingBar ResReviewRating;
    ImageButton ImgDialogClose;

    List<Datum> ResturantReviewList;
    RecyclerView recyclerView;
    ResturantReviewAdapter recyclerAdapter;
    int max_page;


    public ResturantCommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_resturant_comments, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("التعليقات ");

        SharedPreferences prefs = getActivity().getSharedPreferences("UserProfile", MODE_PRIVATE);
        Boolean check = prefs.getBoolean("check", false);
        if (check == false) {
            LoginFragment login = new LoginFragment();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" تسجيل الدخول");
            login.Form = "Resturant";
            Replace(login, R.id.FragmentLoadRes, getActivity().getSupportFragmentManager().beginTransaction());
        }
        else
        {


        txtResReviewName = v.findViewById(R.id.txtResReviewName);
        txtResReviewdate = v.findViewById(R.id.txtResReviewdate);
        txtResReviewdetails = v.findViewById(R.id.txtResReviewdetails);
        ResReviewRating = v.findViewById(R.id.ResReviewRating);

        ResturantReviewList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.ResturantRecComment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {

                }
            }
        };

        recyclerView.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);
        }
        return v;
    }

    private void getRecyclerView(int page) {

        SharedPreferences prefs = this.getActivity().getSharedPreferences("myResturantRef", MODE_PRIVATE);

        int idName = prefs.getInt("idResturant", 0); //0 is the default value.
        String Apitoken = prefs.getString("Apitoken", ""); //0 is the default value.

        Call<Comments> call = RetrofitClient.getInstance().getApi().GetReview(Apitoken, idName);
        call.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {
                Comments body = response.body();
                viewResponse(body, 1);
                max_page = response.body().getData().getLastPage();
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewResponse(Comments body, int page) {

        if (page == 1) {

            ResturantReviewList = body.getData().getData();
            recyclerAdapter = new ResturantReviewAdapter(getContext(), ResturantReviewList);
            recyclerView.setAdapter(recyclerAdapter);
        } else

        {
            ResturantReviewList.addAll(body.getData().getData());
            recyclerAdapter.notifyDataSetChanged();
        }
    }

}
