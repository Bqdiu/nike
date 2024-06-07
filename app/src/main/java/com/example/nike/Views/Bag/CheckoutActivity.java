package com.example.nike.Views.Bag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nike.Controller.BagHandler;
import com.example.nike.Model.Bag;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagCheckOutAdapter;
import com.example.nike.Views.Util;
import com.google.android.material.textfield.TextInputLayout;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    private ImageButton btnHome,btnBag;
    private TextView tvTotalPriceAndItem;
    private ArrayList<Bag> bags;
    private ListView lvBagItem;
    private BagCheckOutAdapter adapter;
    private TextView tvSubtotal,tvShipping,tvTotal;
    private TextInputLayout layoutFirstName,layoutLastName,layoutAddress,layoutEmail,layoutNumberPhone;
    private PaymentButtonContainer paymentButtonContainer;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_checkout);
        addControl();
        data();
        bindData();
    }
    private int calculateTotalPrice(){
        return  bags.stream().mapToInt(Bag::getTotalPrice).sum();
    }
    private int calculateTotalQuantity(){
        return bags.stream().mapToInt(Bag::getQuantity).sum();
    }
    private void addControl(){
        btnHome = findViewById(R.id.btnHome);
        btnBag = findViewById(R.id.btnBag);
        tvTotalPriceAndItem = findViewById(R.id.totalPriceAndItem);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTotal = findViewById(R.id.tvTotal);
        bags = new ArrayList<>();
        lvBagItem = findViewById(R.id.lvBagItem);
        layoutFirstName = findViewById(R.id.layoutFirstName);
        layoutLastName = findViewById(R.id.layoutLastName);
        layoutAddress = findViewById(R.id.layoutAddress);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutNumberPhone = findViewById(R.id.layoutPhoneNumber);

        //paymentButtonContainer = findViewById(R.id.payment_button_container);
    }
    private void bindData(){
        tvTotalPriceAndItem.setText(Util.formatCurrency(calculateTotalPrice())+",000đ ( "+calculateTotalQuantity()+" items )");
        int totalPrice = calculateTotalPrice();
        int totalItems = calculateTotalQuantity();
        tvSubtotal.setText(Util.formatCurrency(totalPrice)+",000đ");
        int shoppingFee =0;
        if(totalItems == 1){
            shoppingFee = 250;
            tvShipping.setText(shoppingFee+",000đ");
        }else {
            tvShipping.setText("Free");
        }
        int TotalPrice = totalPrice + shoppingFee;
        tvTotal.setText(Util.formatCurrency(TotalPrice)+",000đ");
    }
    private void data(){
        bags = BagHandler.getBag(Util.getUserID(getApplicationContext()));
        adapter = new BagCheckOutAdapter(getApplicationContext(),R.layout.row_checkout_bag_item,bags);
        lvBagItem.setAdapter(adapter);
    }
}