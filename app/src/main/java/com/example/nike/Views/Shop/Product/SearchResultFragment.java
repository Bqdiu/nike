package com.example.nike.Views.Shop.Product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;

import java.util.ArrayList;


public class SearchResultFragment extends Fragment implements ItemRecycleViewAdapter.ItemClickListener {

    private static final String product_parent_name = "product_parent_name";
    private String name_search;
    private TextView tv_text_search,tv_notfound,tv_message;
    private ImageButton btn_back,btn_search;

    private RecyclerView rcv_search_result;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<ProductParent> list = new ArrayList<>();
    private ItemRecycleViewAdapter adapter;


    private void addControls(View view)
    {
        tv_text_search = view.findViewById(R.id.tv_text_search);
        tv_text_search.setText(name_search);
        rcv_search_result = view.findViewById(R.id.rcv_search_result);
        btn_search = view.findViewById(R.id.btn_search);
        btn_back = view.findViewById(R.id.btn_back);
        tv_notfound = view.findViewById(R.id.tv_notfound);
        tv_message = view.findViewById(R.id.tv_message);
    }

    private void loadData()
    {
        list = ProductParentHandler.getProductParentByName(name_search);
        if(!list.isEmpty()) {
            layoutManager = new GridLayoutManager(getContext(), 2);
            rcv_search_result.setLayoutManager(layoutManager);
            adapter = new ItemRecycleViewAdapter(getContext(), list, this);
            rcv_search_result.setAdapter(adapter);
            tv_notfound.setVisibility(View.GONE);
            tv_message.setVisibility(View.GONE);
        }
        else
        {
            list = ProductParentHandler.getData();
            layoutManager = new GridLayoutManager(getContext(), 2);
            rcv_search_result.setLayoutManager(layoutManager);
            adapter = new ItemRecycleViewAdapter(getContext(), list, this);
            rcv_search_result.setAdapter(adapter);
        }
    }

    private void addEvents()
    {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public SearchResultFragment() {
    }

    public static SearchResultFragment newInstance(String param1) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(product_parent_name, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name_search = getArguments().getString(product_parent_name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        addControls(view);
        loadData();
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