package com.example.nike.Views.Profile.OrderFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Controller.UserOrderHandler;
import com.example.nike.Controller.UserOrderProductsHandler;
import com.example.nike.Model.UserAccount;
import com.example.nike.Model.UserOrder;
import com.example.nike.Model.UserOrderProducts;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private UserAccount user = new UserAccount();
    private ArrayList<UserOrder> listUserOrder = new ArrayList<>();
    private ImageButton btn_back;
    private SharedPreferences sharedPreferences;
    private LinearLayout empty_layout,haveData;

    private RecyclerView recyclerUserOrderProducts;
    private ArrayList<UserOrderProducts> listOrder = new ArrayList<>();
    private TextView tvSubtotal,tvShipping,tvTotal;
    private int TotalPrice;
    private String formattedResult;
    private UserOrderAdapter adapter;
    private ListView lvUserOrderProduct;
    private  ArrayList<Integer> listTotalPrice;
    private void addControls(View view)
    {
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        btn_back = view.findViewById(R.id.btn_back);
        empty_layout = view.findViewById(R.id.empty_layout);
        user = UserAccountHandler.getUserByEmail(sharedPreferences.getString("email",null));
        if(user != null)
        {
            listUserOrder = UserOrderHandler.getOrdersByUserID(user.getId());
        }
        if(listUserOrder.size() == 0)
        {
            empty_layout.setVisibility(View.VISIBLE);
        }
        recyclerUserOrderProducts = view.findViewById(R.id.recycleBag);
        tvSubtotal = view.findViewById(R.id.tvSubtotal);
        tvShipping = view.findViewById(R.id.tvShipping);
        tvTotal = view.findViewById(R.id.tvTotal);
        haveData = view.findViewById(R.id.haveData);
        lvUserOrderProduct = view.findViewById(R.id.lvUserOrderProduct);
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



    public OrderFragment() {
    }

    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void data() {
        listOrder = UserOrderProductsHandler.getUserOrderProducts(Util.getUserID(getContext()));
        Set<Integer> seenOrderIds = new HashSet<>();
        ArrayList<UserOrderProducts> filteredList = new ArrayList<>();
        for (UserOrderProducts order : listOrder) {
            int orderId = order.getUser_order_id();
            if (!seenOrderIds.contains(orderId)) {
                seenOrderIds.add(orderId);
                filteredList.add(order);
            }
        }
        // get list total price
        listTotalPrice = new ArrayList<>();
        for(int i = 0; i < filteredList.size(); i++)
        {
            int totalPrice = 0;
            UserOrderProducts orderFilted = filteredList.get(i);
            for(int j = 0; j < listOrder.size(); j ++)
            {
                UserOrderProducts orderProducts = listOrder.get(j);
                if(orderFilted.getUser_order_id() == orderProducts.getUser_order_id())
                    totalPrice += orderProducts.getTotalPrice();
            }
            listTotalPrice.add(totalPrice);
        }
        adapter = new UserOrderAdapter(getContext(), R.layout.layout_item_order, filteredList,listTotalPrice);
        lvUserOrderProduct.setAdapter(adapter);
    }

    private int calculateTotalPrice(){
        return  listOrder.stream().mapToInt(UserOrderProducts::getTotalPrice).sum();
    }
    private int calculateTotalQuantity(){
        return listOrder.stream().mapToInt(UserOrderProducts::getQuantity).sum();
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
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        addControls(view);
        data();
        addEvents();
        return view;
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