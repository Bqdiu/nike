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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nike.Controller.BagHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Controller.UserOrderHandler;
import com.example.nike.Controller.UserOrderProductsHandler;
import com.example.nike.Model.Bag;
import com.example.nike.Model.UserAccount;
import com.example.nike.Model.UserOrder;
import com.example.nike.Model.UserOrderProducts;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagCheckOutAdapter;
import com.example.nike.Views.Util;
import com.paypal.pyplcheckout.data.model.pojo.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    private UserOderProductAdapter adapter;
    private ListView lvUserOrderProduct;
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

    private void bindData() {
        int totalPrice = calculateTotalPrice();
        int totalItems = calculateTotalQuantity();
        tvSubtotal.setText(Util.formatCurrency(totalPrice) + ",000đ");
        int shoppingFee = 0;
        if (totalItems == 1) {
            shoppingFee = 250;
            tvShipping.setText(shoppingFee + ",000đ");
        } else {
            tvShipping.setText("Free");
        }
        TotalPrice = totalPrice + shoppingFee;
        DecimalFormat df = new DecimalFormat("#.##");
        formattedResult = df.format(TotalPrice*1000*0.00003935);

        //tvTotal.setText(String.valueOf(formattedResult));
        tvTotal.setText(Util.formatCurrency(TotalPrice) + ",000đ");
    }

    private void data() {
        listOrder = UserOrderProductsHandler.getUserOrderProducts(Util.getUserID(getContext()));
        adapter = new UserOderProductAdapter(getContext(), R.layout.row_user_order_product, listOrder);
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