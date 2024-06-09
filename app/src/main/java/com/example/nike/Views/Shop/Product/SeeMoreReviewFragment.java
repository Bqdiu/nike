package com.example.nike.Views.Shop.Product;

import static com.example.nike.Views.Util.getUserID;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Controller.ProductHandler;
import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Controller.ProductReviewHandler;
import com.example.nike.Mailer.Utils;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductReview;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.ReviewRecycleViewAdapter;
import com.example.nike.Views.Util;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SeeMoreReviewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private int getProductID;
    private ImageButton btnBack;
    private TextView product_parent_name,total_reviews,btnSpinner;
    private RatingBar ratingBarReviewsTitle;
    private RecyclerView recycleReviews;
    private ArrayList<ProductReview> productReviews;
    private ReviewRecycleViewAdapter reviewRecycleViewAdapter;
    private Button btnWriteReview;
    private Dialog dialog;
    private void addControls(View view)
    {
        btnBack = view.findViewById(R.id.btnBack);
        product_parent_name = view.findViewById(R.id.product_parent_name);
        total_reviews = view.findViewById(R.id.total_reviews);
        ratingBarReviewsTitle = view.findViewById(R.id.ratingBarReviewsTitle);
        recycleReviews = view.findViewById(R.id.recycleReviews);
        btnSpinner = view.findViewById(R.id.btnSpinner);
        btnWriteReview = view.findViewById(R.id.btnWriteReview);
        if(ProductReviewHandler.checkReviewerExist(Util.getUserID(view.getContext()),getProductID)){
            btnWriteReview.setVisibility(View.GONE);
        }
    }

    private void loadDataRCV()
    {
        product_parent_name.setText(ProductParentHandler.getNameProductParent(getProductID));

        productReviews = ProductReviewHandler.getDataByProductID(getProductID);
        reviewRecycleViewAdapter = new ReviewRecycleViewAdapter(productReviews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recycleReviews.setLayoutManager(layoutManager);
        recycleReviews.setAdapter(reviewRecycleViewAdapter);

        float avgRating = (float) productReviews.stream().mapToDouble(ProductReview::getReviewRate).average().orElse(0.0);
        ratingBarReviewsTitle.setRating(avgRating);
        total_reviews.setText(productReviews.size() + " Reviews");
    }
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.getMenuInflater().inflate(R.menu.popup_sort, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                float avgRating = (float) productReviews.stream().mapToDouble(ProductReview::getReviewRate).average().orElse(0.0);
                switch (item.getItemId()) {
                    case R.id.sort_newest:
                        btnSpinner.setText("Sort by: " +item.getTitle());
                        sortReviewsByNewest();
                        ratingBarReviewsTitle.setRating(avgRating);
                        return true;
                    case R.id.sort_oldest:
                        btnSpinner.setText("Sort by: " +item.getTitle());
                        sortReviewsByOldest();
                        ratingBarReviewsTitle.setRating(avgRating);
                    case R.id.sort_rating_ascending:
                        btnSpinner.setText("Sort by: " + item.getTitle());
                        sortReviewsByRatingAscending();
                        ratingBarReviewsTitle.setRating(avgRating);
                        return true;
                    case R.id.sort_rating_descending:
                        btnSpinner.setText("Sort by: " + item.getTitle());
                        sortReviewsByRatingDescending();
                        ratingBarReviewsTitle.setRating(avgRating);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void sortReviewsByNewest() {
        Collections.sort(productReviews, new Comparator<ProductReview>() {
            @Override
            public int compare(ProductReview r1, ProductReview r2) {
                return r2.getReviewTime().compareTo(r1.getReviewTime());
            }
        });
        reviewRecycleViewAdapter.notifyDataSetChanged();
    }

    private void sortReviewsByOldest() {
        Collections.sort(productReviews, new Comparator<ProductReview>() {
            @Override
            public int compare(ProductReview r1, ProductReview r2) {
                return r1.getReviewTime().compareTo(r2.getReviewTime());
            }
        });
        reviewRecycleViewAdapter.notifyDataSetChanged();
    }
    private void sortReviewsByRatingAscending() {
        Collections.sort(productReviews, new Comparator<ProductReview>() {
            @Override
            public int compare(ProductReview r1, ProductReview r2) {
                return Float.compare(r1.getReviewRate(), r2.getReviewRate());
            }
        });
        reviewRecycleViewAdapter.notifyDataSetChanged();
    }

    private void sortReviewsByRatingDescending() {
        Collections.sort(productReviews, new Comparator<ProductReview>() {
            @Override
            public int compare(ProductReview r1, ProductReview r2) {
                return Float.compare(r2.getReviewRate(), r1.getReviewRate());
            }
        });
        reviewRecycleViewAdapter.notifyDataSetChanged();
    }
    private void addEvent()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

            }
        });

        btnSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWriteReview();
            }
        });

    }




    public SeeMoreReviewFragment() {
        // Required empty public constructor
    }

    public static SeeMoreReviewFragment newInstance(int param1) {
        SeeMoreReviewFragment fragment = new SeeMoreReviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getProductID = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_see_more_review, container, false);
        addControls(view);
        loadDataRCV();
        addEvent();
        return view;
    }

    private void showPopupWriteReview(){
        dialog = new Dialog(getContext());
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_popup_write_review,null);
        //add Control
        TextView tvOverallRating = convertView.findViewById(R.id.titleOrverallRating);
        TextView tvYourReview = convertView.findViewById(R.id.tvYourReview);
        TextView tvRatingBarValidation = convertView.findViewById(R.id.tvErrorRatingBar);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        TextInputLayout layoutReviewTitle = convertView.findViewById(R.id.layoutReviewTitle);
        TextInputLayout layoutYourReview = convertView.findViewById(R.id.layoutYourReview);
        Button btnSubmit = convertView.findViewById(R.id.btnSubmit);
        TextView btnClose = convertView.findViewById(R.id.btnClose);
        String text = "Overall rating <font color='red'>*</font>";
        tvOverallRating.setText(Html.fromHtml(text));
        String text1 = "Your Review <font color='red'>*</font>";
        tvYourReview.setText(Html.fromHtml(text1));

        layoutReviewTitle.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    layoutReviewTitle.setError("Summarise your review in 150 characters or less.");
                }
            }

            @SuppressLint("SuspiciousIndentation")
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0)
                    layoutReviewTitle.setError(null);

            }
        });
        layoutYourReview.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<30){
                    layoutYourReview.setError("Describe what you liked, what you didn't like and other key things shoppers should know. Minimum 30 characters.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>=30)
                    layoutYourReview.setError(null);

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag= true;
                if(layoutReviewTitle.getEditText().getText().length()==0){
                    layoutReviewTitle.setError("Summarise your review in 150 characters or less.");
                    flag = false;

                }else{
                    layoutReviewTitle.setError(null);
                }
                if(layoutYourReview.getEditText().getText().length()<30){
                    flag = false;
                    layoutYourReview.setError("Describe what you liked, what you didn't like and other key things shoppers should know. Minimum 30 characters.");
                }else{
                    layoutYourReview.setError(null);
                }
                if(ratingBar.getRating() == 0){
                    flag = false;
                    tvRatingBarValidation.setVisibility(View.VISIBLE);
                }else{
                    tvRatingBarValidation.setVisibility(View.VISIBLE);
                }
                if(flag==false){
                    return;
                }else{
                    int productID = getProductID;
                    String title = layoutReviewTitle.getEditText().getText().toString();
                    String content = layoutYourReview.getEditText().getText().toString();
                    float rate = ratingBar.getRating();
                    ProductReviewHandler.submitReview(Util.getUserID(getContext()),productID,title,content,rate);
                    Toast.makeText(getContext(),"Review Successful",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    updateRecycleReview();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //Popup Show
        dialog.setContentView(convertView);


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void updateRecycleReview(){
        productReviews = ProductReviewHandler.getDataByProductID(getProductID);
        ArrayList<ProductReview> reviewsTmp = new ArrayList<>();
        for(int i = 0;i<productReviews.size();i++){
            if(i<2){
                ProductReview pr = productReviews.get(i);
                reviewsTmp.add(pr);
            }
        }

        reviewRecycleViewAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}