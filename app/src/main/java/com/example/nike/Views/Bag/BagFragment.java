package com.example.nike.Views.Bag;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Controller.BagHandler;
import com.example.nike.Controller.ProductHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Model.Bag;
import com.example.nike.Model.ProductSize;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagAdapter;
import com.example.nike.Views.Shop.Product.DetailProduct;
import com.example.nike.Views.Shop.ShopFragment;
import com.example.nike.Views.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;

public class BagFragment extends Fragment implements BagAdapter.ItemClickListener {
    private LinearLayout nonData;
    private LinearLayout haveData;
    private View hr;
    private RecyclerView recyclerBag;
    private ArrayList<Bag> bags = new ArrayList<>();
    private TextView tvSubtotal,tvShipping,tvTotal;
    private Button btnCheckout;
    private Button btnShoppingNow;
    private BagAdapter bagAdapter;
    private Button btnDone;
    private NumberPicker numberPicker;
    private Dialog dialog;
    private int Subtotal;
    private int TotalPrice;
    private int ShippingFee;
    String[] displayedValues = {"Remove", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);
        addControl(view);
        data();
        addEvent();
        bindData();
        return view;
    }
    private void addControl(View view){

        recyclerBag = view.findViewById(R.id.recycleBag);
        tvSubtotal = view.findViewById(R.id.tvSubtotal);
        tvShipping = view.findViewById(R.id.tvShipping);
        tvTotal = view.findViewById(R.id.tvTotal);

        btnCheckout = view.findViewById(R.id.btnCheckout);
        btnShoppingNow = view.findViewById(R.id.btnShoppingNow);

        nonData = view.findViewById(R.id.nonData);
        haveData = view.findViewById(R.id.haveData);
        hr = view.findViewById(R.id.hr);

    }
    private void LoadFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void addEvent(){
        btnShoppingNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadFragment(new ShopFragment());
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                bottomNavigationView.setSelectedItemId(R.id.itemShop);
            }
        });
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
    private int calculateTotalPrice(){
        return  bags.stream().mapToInt(Bag::getTotalPrice).sum();
    }
    private int calculateTotalQuantity(){
        return bags.stream().mapToInt(Bag::getQuantity).sum();
    }
    private void bindData(){
        int TotalQuantity = calculateTotalQuantity();
        if(TotalQuantity == 0){
            nonData.setVisibility(View.VISIBLE);
            btnShoppingNow.setVisibility(View.VISIBLE);
            btnCheckout.setVisibility(View.GONE);
            haveData.setVisibility(View.GONE);
            hr.setVisibility(View.GONE);
        }else{
            nonData.setVisibility(View.GONE);
            btnShoppingNow.setVisibility(View.GONE);
            btnCheckout.setVisibility(View.VISIBLE);
            haveData.setVisibility(View.VISIBLE);
            hr.setVisibility(View.VISIBLE);
            Subtotal = calculateTotalPrice();
            tvSubtotal.setText("đ"+Util.formatCurrency(Subtotal)+",000");
            if(TotalQuantity == 1){
                ShippingFee = 250;
                tvShipping.setText("đ"+ShippingFee+",000");

            } else{
                ShippingFee = 0;
                tvShipping.setText("Standard - Free");
            }
            TotalPrice = Subtotal + ShippingFee;
            tvTotal.setText("đ"+Util.formatCurrency(TotalPrice)+",000");
        }

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

    private void ShowPopup(Bag bag){
        dialog = new Dialog(getContext());
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_size_number_picker,null);
        addControlOfPopup(convertView,bag);
        addEventOfPopup(bag);

        dialog.setContentView(convertView);
        //dialog.setCancelable(true); // Cho phép đóng Dialog khi chạm ra ngoài


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true); // Cho phép đóng Dialog khi chạm ra ngoài
        dialog.show();
    }
    private void addControlOfPopup(View view,Bag bag){
        btnDone = view.findViewById(R.id.btnDone);
        numberPicker = view.findViewById(R.id.numberPickerSize);
        numberPicker.setMinValue(0);


        numberPicker.setMaxValue(displayedValues.length - 1);
        numberPicker.setDisplayedValues(displayedValues);
        numberPicker.setValue(bag.getQuantity());
    }
    private void addEventOfPopup(Bag bag){

        btnDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                int value = numberPicker.getValue();
                String selectedValue = displayedValues[value];
                ProductSize productSize = ProductSizeHandler.getDetailProductSize(bag.getProductSizeID());
                if( !selectedValue.equals("Remove")){
                    int newQuantity = Integer.parseInt(selectedValue);
                    int inventoryQuantity = productSize.getSoluong();
                    if(bag.getQuantity() != newQuantity && newQuantity <= inventoryQuantity)
                    BagHandler.updateQuantity(Util.getUserID(getContext()),bag.getBagID(),newQuantity);
                    else if(bag.getQuantity() != newQuantity && newQuantity > inventoryQuantity && inventoryQuantity != 0){
                        BagHandler.updateQuantity(Util.getUserID(getContext()),bag.getBagID(),inventoryQuantity);
                        Toast.makeText(getContext(),"The remaining quantity of the product is "+inventoryQuantity,Toast.LENGTH_LONG).show();
                    }else {
                        BagHandler.deleteProduct(bag.getBagID());
                        Toast.makeText(getContext(),"The product is out of stock",Toast.LENGTH_LONG).show();
                    }
                }else{
                    BagHandler.deleteProduct(bag.getBagID());
                }
                updateRecycleView();
                dialog.dismiss();
            }
        });
    }
    private void updateRecycleView(){
        bags = BagHandler.getBag(Util.getUserID(getContext()));
        bagAdapter.updateData(bags);
        bindData();
    }
    private void data(){
        bags = BagHandler.getBag(Util.getUserID(getContext()));
        bags.stream()
                .map(bag -> {
                    ProductSize productSize = ProductSizeHandler.getDetailProductSize(bag.getProductSizeID());
                    return new AbstractMap.SimpleEntry<>(bag, productSize);
                })
                .filter(entry -> entry.getValue() != null && entry.getValue().getSoluong() == 0)
                .forEach(entry -> BagHandler.deleteProduct(entry.getKey().getBagID()));
        bags = BagHandler.getBag(Util.getUserID(getContext()));
        bagAdapter = new BagAdapter(bags,this);
        bagAdapter.setItemClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerBag.setLayoutManager(layoutManager);
        recyclerBag.setAdapter(bagAdapter);



    }
    @Override
    public void onItemClickListener(Bag bag) {
        ShowPopup(bag);
    }
}
