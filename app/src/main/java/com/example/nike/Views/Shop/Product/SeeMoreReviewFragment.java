package com.example.nike.Views.Shop.Product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nike.R;


public class SeeMoreReviewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int getProductID;

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
        System.out.println("!!!!!!!!!!!!!!!!!!!!!! get product id: " + getProductID);
        return view;
    }
}