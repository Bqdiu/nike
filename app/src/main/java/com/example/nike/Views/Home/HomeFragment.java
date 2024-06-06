package com.example.nike.Views.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Product.DetailProduct;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ItemRecycleViewAdapter.ItemClickListener{

    TextView tvGreeting;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences sharedPreferences;
    private String mParam1;
    private String mParam2;
    private RecyclerView rcv_newRelease;
    private ArrayList<ProductParent> productParentList = new ArrayList<>();
    private ArrayList<ProductParent> limitProductParentList = new ArrayList<>();
    private ItemRecycleViewAdapter adapter;
    private String user_name;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        addControl(view);
        rcv_loadData();
        addEvent();
        return view;
    }
    private void addControl(View view){
        tvGreeting = view.findViewById(R.id.greeting);
        rcv_newRelease = view.findViewById(R.id.rcv_newRelease);
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user_name = sharedPreferences.getString("first_name",null);
        tvGreeting.setText(checkHourGreeting());
    }
    private void rcv_loadData()
    {
        productParentList = ProductParentHandler.getAllNewRelease();
        for(ProductParent productParent : productParentList)
        {
            if(limitProductParentList.size() < 3)
               limitProductParentList.add(productParent);
        }
        adapter = new ItemRecycleViewAdapter(getContext(),limitProductParentList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv_newRelease.setLayoutManager(layoutManager);
        rcv_newRelease.setAdapter(adapter);
    }

    @Override
    public void onItemClick(ArrayList<Product> list) {
        Fragment fragment = DetailProduct.newInstance(list);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack("TabLayoutOfShop");
        ft.commit();
    }

    private void addEvent(){

    }
    private String checkHourGreeting(){
        String msg="";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime time = LocalTime.now();
            int hour = time.getHour();
            if(hour>=6 && hour<12){
                msg = "Good Morning, " + user_name;
            }
            else if(hour>=12 && hour<18){
                msg = "Good Afternoon, " + user_name;
            }
            else
            {
                msg = "Good Evening, " + user_name;
            }
        }
        return msg;
    }
}