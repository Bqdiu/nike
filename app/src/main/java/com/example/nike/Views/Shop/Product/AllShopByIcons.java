package com.example.nike.Views.Shop.Product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nike.FragmentUtils;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.ShopByIcons;
import com.example.nike.R;
import com.example.nike.Views.Home.AllProductParent.AllProductParent;
import com.example.nike.Views.Shop.Adapter.IconsItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;

import java.util.ArrayList;

public class AllShopByIcons extends Fragment implements IconsItemRecycleViewAdapter.IconItemClickListener{

    private static final String ARG_TEXTVIEW = "text_view";
    private static final String ARG_LIST = "list";

    private static final String ARG_OBJECT_ID = "object_id";


    private String getTextView;
    private ArrayList<Integer> getList;
    private int getObjectID;
    private TextView tv_ShopByIcon;
    private ImageView btn_back;
    private RecyclerView rcv_ShopByIcon;
    private IconsItemRecycleViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txt_empty;
    private void addControls(View view)
    {
        tv_ShopByIcon = view.findViewById(R.id.tv_ShopByIcon);
        btn_back = view.findViewById(R.id.btn_back);
        rcv_ShopByIcon = view.findViewById(R.id.rcv_ShopByIcon);
        tv_ShopByIcon.setText(getTextView);
        txt_empty = view.findViewById(R.id.txt_empty);
    }
    private void rcv_loadData()
    {
        if(getList.size() > 0)
        {
            adapter = new IconsItemRecycleViewAdapter(getList,getObjectID,getContext());
            adapter.setIconItemClickListener(this);
            layoutManager = new GridLayoutManager(getContext(), 2);
            rcv_ShopByIcon.setLayoutManager(layoutManager);
            rcv_ShopByIcon.setLayoutManager(layoutManager);
            rcv_ShopByIcon.setAdapter(adapter);
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

    public AllShopByIcons() {
        // Required empty public constructor
    }

    public static AllShopByIcons newInstance(String textview, ArrayList<Integer> list,int objectID) {
        AllShopByIcons fragment = new AllShopByIcons();
        Bundle args = new Bundle();
        args.putString(ARG_TEXTVIEW, textview);
        args.putSerializable(ARG_LIST, list);
        args.putInt(ARG_OBJECT_ID, objectID);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getTextView = getArguments().getString(ARG_TEXTVIEW);
            getList = (ArrayList<Integer>) getArguments().getSerializable(ARG_LIST);
            getObjectID = getArguments().getInt(ARG_OBJECT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_shop_by_icons, container, false);
        addControls(view);
        rcv_loadData();
        addEvents();
        return view;
    }

    @Override
    public void onIconItemClick(String name, ArrayList<ProductParent> list) {
        AllProductParent allProductParent = AllProductParent.newInstance(name,list);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentUtils.addFragment(fm,allProductParent,R.id.frameLayout);
    }
}