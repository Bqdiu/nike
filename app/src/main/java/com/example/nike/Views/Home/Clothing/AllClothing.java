package com.example.nike.Views.Home.Clothing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Product.DetailProduct;

import java.util.ArrayList;

public class AllClothing extends Fragment implements ItemRecycleViewAdapter.ItemClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView btn_back;
    private RecyclerView rcv_clothingFull;
    private ArrayList<ProductParent> productParentList = new ArrayList<>();
    private ItemRecycleViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private void addControls(View view) {
        btn_back = view.findViewById(R.id.btn_back);
        rcv_clothingFull = view.findViewById(R.id.rcv_clothingFull);
    }
    private void rcv_loadData() {
        productParentList = ProductParentHandler.getAllClothing();
        adapter = new ItemRecycleViewAdapter(getContext(),productParentList,this);
        layoutManager = new GridLayoutManager(getContext(), 2);
        rcv_clothingFull.setLayoutManager(layoutManager);
        rcv_clothingFull.setLayoutManager(layoutManager);
        rcv_clothingFull.setAdapter(adapter);
    }
    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }
    public AllClothing() {
        // Required empty public constructor
    }

    public static AllClothing newInstance(String param1, String param2) {
        AllClothing fragment = new AllClothing();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_clothing, container, false);
        addControls(view);
        rcv_loadData();
        addEvents();
        return view;
    }
    @Override
    public void onItemClick(ArrayList<Product> list) {
        Fragment fragment = DetailProduct.newInstance(list);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}