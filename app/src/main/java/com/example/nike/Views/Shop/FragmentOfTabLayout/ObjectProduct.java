package com.example.nike.Views.Shop.FragmentOfTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Controller.IconsHandler;
import com.example.nike.Controller.ProductHandler;
import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.FragmentUtils;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.ShopByIcons;
import com.example.nike.R;

import com.example.nike.Views.Home.AllProductParent.AllProductParent;
import com.example.nike.Views.Shop.Adapter.IconsItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Product.AllShopByIcons;
import com.example.nike.Views.Shop.Product.DetailProduct;

import java.util.ArrayList;

public class ObjectProduct extends Fragment implements ItemRecycleViewAdapter.ItemClickListener,IconsItemRecycleViewAdapter.IconItemClickListener {
    private RecyclerView recyclerViewNewRelease,clothingRecycleView;
    private ItemRecycleViewAdapter adapter,adapterClothing;
    private ArrayList<ProductParent> productParentArrayList = new ArrayList<>();
    private ArrayList<ProductParent> limitProductParentArrayList = new ArrayList<>();

    // clothing
    private ArrayList<ProductParent> clothingList = new ArrayList<>();
    private ArrayList<ProductParent> limitClothingList = new ArrayList<>();

    private TextView tvNewRelease,tvClothing;
    //Shop By Icons
    private RecyclerView shopByIconsRecycleView;
    private IconsItemRecycleViewAdapter shopByIconsAdapter;
    ArrayList<Integer> shopByIconsList;
    ArrayList<Integer> limitShopByIconsList;
    private TextView tv_viewall_NewRelase,tv_viewall_ShopByIcon,tv_viewall_clothing;
    private FragmentManager fm;
    private int objectID;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ObjectProduct(int objectID) {
        this.objectID = objectID;
    }
    public ObjectProduct(){

    }
    public static ObjectProduct newInstance(String param1, String param2) {
        ObjectProduct fragment = new ObjectProduct();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_object_product, container, false);
        addControl(view);
        data();
        addEvents();
        return view;
    }

    private void addControl(View view){
        recyclerViewNewRelease = view.findViewById(R.id.newReleaseRecycleView);
        tvNewRelease = view.findViewById(R.id.tvNewRelease);
        shopByIconsRecycleView = view.findViewById(R.id.shopByIconsRecycleView);
        tv_viewall_NewRelase = view.findViewById(R.id.tv_viewall_NewRelase);
        tv_viewall_ShopByIcon = view.findViewById(R.id.tv_viewall_ShopByIcon);
        tv_viewall_clothing = view.findViewById(R.id.tv_viewall_clothing);
        tvClothing = view.findViewById(R.id.tvClothing);
        clothingRecycleView = view.findViewById(R.id.clothingRecycleView);
    }
    private void data(){
        //New Release
        productParentArrayList = ProductParentHandler.getDataNewReleaseByObjectID(objectID);
        if(productParentArrayList.isEmpty()){
            tvNewRelease.setVisibility(View.GONE);
            tv_viewall_NewRelase.setVisibility(View.GONE);
        }
        else {
            tvNewRelease.setVisibility(View.VISIBLE);
            limitProductParentArrayList.clear();
            for(ProductParent productParent : productParentArrayList)
            {
                if(limitProductParentArrayList.size() < 3)
                    limitProductParentArrayList.add(productParent);
            }
            adapter = new ItemRecycleViewAdapter(getContext(),limitProductParentArrayList,this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
            recyclerViewNewRelease.setLayoutManager(layoutManager);
            recyclerViewNewRelease.setAdapter(adapter);
        }
        //Shop By Icons

        shopByIconsList = IconsHandler.getDataByObjectIDDistinct(objectID);
        limitShopByIconsList = new ArrayList<>();
        for (Integer shopByIcons : shopByIconsList)
        {
            if(limitShopByIconsList.size() < 5)
                limitShopByIconsList.add(shopByIcons);
        }
        shopByIconsAdapter = new IconsItemRecycleViewAdapter(limitShopByIconsList,objectID, getContext());
        shopByIconsAdapter.setIconItemClickListener(this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        shopByIconsRecycleView.setLayoutManager(layoutManager1);
        shopByIconsRecycleView.setAdapter(shopByIconsAdapter);
        // clothing
        clothingList = ProductParentHandler.getAllClothingByObjectID(objectID);
        if(clothingList.isEmpty()){
            tvClothing.setVisibility(View.GONE);
            tv_viewall_clothing.setVisibility(View.GONE);
        }
        else {
            tvClothing.setVisibility(View.VISIBLE);
            limitClothingList.clear();
            for(ProductParent productParent : clothingList)
            {
                if(limitClothingList.size() < 3)
                    limitClothingList.add(productParent);
            }
            adapterClothing = new ItemRecycleViewAdapter(getContext(),limitClothingList,this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
            clothingRecycleView.setLayoutManager(layoutManager);
            clothingRecycleView.setAdapter(adapterClothing);
        }
    }

    private void addEvents() {
        tv_viewall_NewRelase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_TextView = "New Release";
                ArrayList<ProductParent> list = new ArrayList<>();
                list = ProductParentHandler.getDataNewReleaseByObjectID(objectID);
                AllProductParent allProductParent = AllProductParent.newInstance(txt_TextView,list);
                fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,allProductParent,R.id.frameLayout);
            }
        });
        tv_viewall_ShopByIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_TextView = "Shop By Icons";
                AllShopByIcons allShopByIcons = AllShopByIcons.newInstance(txt_TextView,shopByIconsList,objectID);
                fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,allShopByIcons,R.id.frameLayout);
            }
        });

        tv_viewall_clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_TextView = "Clothing";
                ArrayList<ProductParent> list = new ArrayList<>();
                list = ProductParentHandler.getAllClothingByObjectID(objectID);
                AllProductParent allProductParent = AllProductParent.newInstance(txt_TextView,list);
                fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,allProductParent,R.id.frameLayout);
            }
        });

    }
    @Override
    public void onItemClick(ArrayList<Product> list) {
        Fragment fragment = DetailProduct.newInstance(list);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack("TabLayoutOfShop");
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


    @Override
    public void onIconItemClick(String name, ArrayList<ProductParent> list) {
        AllProductParent allProductParent = AllProductParent.newInstance(name,list);
        fm = getActivity().getSupportFragmentManager();
        FragmentUtils.addFragment(fm,allProductParent,R.id.frameLayout);
    }
}


