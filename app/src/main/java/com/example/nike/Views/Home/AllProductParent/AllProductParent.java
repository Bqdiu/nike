package com.example.nike.Views.Home.AllProductParent;

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
import android.widget.TextView;

import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Product.DetailProduct;

import java.util.ArrayList;

public class AllProductParent extends Fragment implements ItemRecycleViewAdapter.ItemClickListener{

    private static final String ARG_TEXTVIEW = "text_view";
    private static final String ARG_LIST = "list";

    private String getTextView;
    private ArrayList<ProductParent> getList;

    private TextView tv_productParent;
    private ImageView btn_back;
    private RecyclerView rcv_productParent;
    private ItemRecycleViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txt_empty;

    private void addControls(View view)
    {
        tv_productParent = view.findViewById(R.id.tv_productParent);
        btn_back = view.findViewById(R.id.btn_back);
        rcv_productParent = view.findViewById(R.id.rcv_productParent);
        tv_productParent.setText(getTextView);
        txt_empty = view.findViewById(R.id.txt_empty);
    }

    private void rcv_loadData()
    {
        if(getList.size() > 0)
        {
            adapter = new ItemRecycleViewAdapter(getContext(),getList,this);
            layoutManager = new GridLayoutManager(getContext(), 2);
            rcv_productParent.setLayoutManager(layoutManager);
            rcv_productParent.setLayoutManager(layoutManager);
            rcv_productParent.setAdapter(adapter);
        }
        else
        {
            txt_empty.setVisibility(View.VISIBLE);
        }

    }

    private void addEvents()
    {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }

    public AllProductParent() {
        // Required empty public constructor
    }
    public static AllProductParent newInstance(String textview, ArrayList<ProductParent> list) {
        AllProductParent fragment = new AllProductParent();
        Bundle args = new Bundle();
        args.putString(ARG_TEXTVIEW, textview);
        args.putSerializable(ARG_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getTextView = getArguments().getString(ARG_TEXTVIEW);
            getList = (ArrayList<ProductParent>) getArguments().getSerializable(ARG_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_product_parent, container, false);
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