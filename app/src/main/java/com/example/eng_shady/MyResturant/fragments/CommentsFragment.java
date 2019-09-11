package com.example.eng_shady.MyResturant.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eng_shady.MyResturant.R;
import com.example.eng_shady.MyResturant.activities.ClientActivity;
import com.example.eng_shady.MyResturant.adapters.OnEndless;
import com.example.eng_shady.MyResturant.adapters.ResturantReviewAdapter;
import com.example.eng_shady.MyResturant.api.RetrofitClient;
import com.example.eng_shady.MyResturant.models.Client.ReviewAdd.CommentAdd;
import com.example.eng_shady.MyResturant.models.General.ResturantReview.Comments;
import com.example.eng_shady.MyResturant.models.General.ResturantReview.Datum;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eng_shady.MyResturant.adapters.Helper.Replace;


public class CommentsFragment extends Fragment {


    TextView txtResReviewName, txtResReviewdate, txtResReviewdetails;
    RatingBar ResReviewRating;
    ImageButton ImgDialogClose;

    List<Datum> ResturantReviewList;
    RecyclerView recyclerView;
    ResturantReviewAdapter recyclerAdapter;
    int max_page;

    AlertDialog alertDialog;


    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comments, container, false);

        txtResReviewName = v.findViewById(R.id.txtResReviewName);
        txtResReviewdate = v.findViewById(R.id.txtResReviewdate);
        txtResReviewdetails = v.findViewById(R.id.txtResReviewdetails);
        ResReviewRating = v.findViewById(R.id.ResReviewRating);


        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getContext().getSharedPreferences("myRef", MODE_PRIVATE);
                final int idName = prefs.getInt("idRes", 0);
                String ResName = prefs.getString("ResName", "");

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.reviewadddialog, null);
                dialogBuilder.setView(dialogView);

                final TextView txtResCommentName = dialogView.findViewById(R.id.txtResCommentName);
                final EditText txtCliCommentName = dialogView.findViewById(R.id.txtCliCommentName);
                final EditText txtResCommentdiscription = dialogView.findViewById(R.id.txtResCommentdiscription);
                final RatingBar CommentRating = dialogView.findViewById(R.id.CommentRating);
                ImgDialogClose = dialogView.findViewById(R.id.ImgDialogClose);
                ImgDialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                txtResCommentName.setText(ResName);
                Button btnCommentAdd = dialogView.findViewById(R.id.btnCommentAdd);
                btnCommentAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clientName = txtCliCommentName.getText().toString().trim();
                        String ResRate = String.valueOf(CommentRating.getRating());

                        String Discription = txtResCommentdiscription.getText().toString().trim();

                        if (clientName.isEmpty()) {
                            txtCliCommentName.setError(getString(R.string.ValidationReqire));
                            txtCliCommentName.requestFocus();
                            return;
                        }

                        if (Discription.isEmpty()) {
                            txtResCommentdiscription.setError(getString(R.string.ValidationReqire));
                            txtResCommentdiscription.requestFocus();
                            return;
                        }
                        if (CommentRating.getRating() == 0) {
                            Toast.makeText(getContext(), "يرجى إختيار تقييم", Toast.LENGTH_SHORT).show();
                        }

                        Call<CommentAdd> call = RetrofitClient.getInstance().getApi().
                                ReviewAdd(ResRate, Discription, idName, "HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB");
                        call.enqueue(new Callback<CommentAdd>() {
                            @Override
                            public void onResponse(Call<CommentAdd> call, Response<CommentAdd> response) {
                                CommentAdd CA = response.body();

                                if (CA.getStatus() != 0) {
                                    Toast.makeText(getContext(), CA.getMsg(), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), CA.getMsg(), Toast.LENGTH_SHORT).show();


                                }
                            }

                            @Override
                            public void onFailure(Call<CommentAdd> call, Throwable t) {

                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });
                alertDialog = dialogBuilder.create();
                alertDialog.show();

            }
        });

        ResturantReviewList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.RecResReview);
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
        return v;
    }

    private void CommentAdd() {

    }

    private void getRecyclerView(int current_page) {

        SharedPreferences prefs = this.getActivity().getSharedPreferences("myRef", MODE_PRIVATE);

        int idName = prefs.getInt("idRes", 0); //0 is the default value.

        Call<Comments> call = RetrofitClient.getInstance().getApi().GetReview("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", idName);
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
