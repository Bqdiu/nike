package com.example.nike.Views.Home;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.nike.Views.Home.AllReleaseFragment.AllReleaseProduct;
import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.FragmentUtils;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.R;
import com.example.nike.Views.Home.IconAirForce1.AllIconAirForce1;
import com.example.nike.Views.Home.IconAirJordan1.AllIconAirJordan1;
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
    private String user_name;
    private RecyclerView rcv_newRelease,rcv_icon_AirForce1,rcv_icon_AirJordan1;
    private ArrayList<ProductParent> productParentListNewRelease = new ArrayList<>();
    private ArrayList<ProductParent> limitProductParentListNewRelease = new ArrayList<>();
    private ItemRecycleViewAdapter adapterNewRelease;

    private ArrayList<ProductParent> productParentListIconAirForce1 = new ArrayList<>();
    private ArrayList<ProductParent> limitProductParentListIconAirForce1 = new ArrayList<>();
    private ItemRecycleViewAdapter adapterIconAirForce1;

    private ArrayList<ProductParent> productParentListIconAirJordan1 = new ArrayList<>();
    private ArrayList<ProductParent> limitProductParentListIconAirJordan1 = new ArrayList<>();
    private ItemRecycleViewAdapter adapterIconAirJordan1;
    private TextView tv_viewall_NewRelase,tv_viewall_icon_AirForce1,tv_viewall_icon_AirJordan1;

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
        tvGreeting.setText(checkHourGreeting());
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user_name = sharedPreferences.getString("first_name",null);

        rcv_newRelease = view.findViewById(R.id.rcv_newRelease);
        tv_viewall_NewRelase = view.findViewById(R.id.tv_viewall_NewRelase);

        rcv_icon_AirForce1 = view.findViewById(R.id.rcv_icon_AirForce1);
        tv_viewall_icon_AirForce1 = view.findViewById(R.id.tv_viewall_icon_AirForce1);

        rcv_icon_AirJordan1 = view.findViewById(R.id.rcv_icon_AirJordan1);
        tv_viewall_icon_AirJordan1 = view.findViewById(R.id.tv_viewall_icon_AirJordan1);
    }
    private void rcv_loadData()
    {
        // new release
        productParentListNewRelease = ProductParentHandler.getAllNewRelease();
        limitProductParentListNewRelease.clear();
        for(ProductParent productParent : productParentListNewRelease)
        {
            if(limitProductParentListNewRelease.size() < 3)
               limitProductParentListNewRelease.add(productParent);
        }
        adapterNewRelease = new ItemRecycleViewAdapter(getContext(), limitProductParentListNewRelease,this);
        RecyclerView.LayoutManager layoutManagerNewRelease = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv_newRelease.setLayoutManager(layoutManagerNewRelease);
        rcv_newRelease.setAdapter(adapterNewRelease);
        // Air force 1
        productParentListIconAirForce1 = ProductParentHandler.getAllProductParentByIcon(1);
        limitProductParentListIconAirForce1.clear();
        for(ProductParent productParent : productParentListIconAirForce1)
        {
            if(limitProductParentListIconAirForce1.size() < 3)
                limitProductParentListIconAirForce1.add(productParent);
        }
        adapterIconAirForce1 = new ItemRecycleViewAdapter(getContext(), limitProductParentListIconAirForce1,this);
        RecyclerView.LayoutManager layoutManagerIconAirForce1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv_icon_AirForce1.setLayoutManager(layoutManagerIconAirForce1);
        rcv_icon_AirForce1.setAdapter(adapterIconAirForce1);

        // Air Jordan 1
        productParentListIconAirJordan1 = ProductParentHandler.getAllProductParentByIcon(3);
        limitProductParentListIconAirJordan1.clear();
        for(ProductParent productParent : productParentListIconAirJordan1)
        {
            if(limitProductParentListIconAirJordan1.size() < 3)
                limitProductParentListIconAirJordan1.add(productParent);
        }
        adapterIconAirJordan1 = new ItemRecycleViewAdapter(getContext(), limitProductParentListIconAirJordan1,this);
        RecyclerView.LayoutManager layoutManagerIconAirJordan1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv_icon_AirJordan1.setLayoutManager(layoutManagerIconAirJordan1);
        rcv_icon_AirJordan1.setAdapter(adapterIconAirJordan1);
    }

    @Override
    public void onItemClick(ArrayList<Product> list) {
        Fragment fragment = DetailProduct.newInstance(list);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void addEvent(){
        tv_viewall_NewRelase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllReleaseProduct allReleaseProduct = new AllReleaseProduct();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,allReleaseProduct,R.id.frameLayout);
            }
        });

        tv_viewall_icon_AirForce1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllIconAirForce1 allIconAirForce1 = new AllIconAirForce1();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,allIconAirForce1,R.id.frameLayout);
            }
        });

        tv_viewall_icon_AirJordan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllIconAirJordan1 allIconAirJordan1 = new AllIconAirJordan1();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,allIconAirJordan1,R.id.frameLayout);
            }
        });
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