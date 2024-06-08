package com.example.nike.Views.Bag;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nike.BuildConfig;
import com.example.nike.Controller.BagHandler;
import com.example.nike.Controller.FavoriteProductHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Controller.UserOrderHandler;
import com.example.nike.Controller.UserOrderProductsHandler;
import com.example.nike.MainActivity;
import com.example.nike.Model.Bag;
import com.example.nike.Model.UserOrderProducts;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagCheckOutAdapter;
import com.example.nike.Views.Shop.ShopFragment;
import com.example.nike.Views.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.config.UIConfig;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    private Dialog dialog;
    private ImageButton btnHome, btnBag;
    private TextView tvTotalPriceAndItem;
    private ArrayList<Bag> bags;
    private ListView lvBagItem;
    private BagCheckOutAdapter adapter;
    private TextView tvSubtotal, tvShipping, tvTotal;
    private TextInputLayout layoutFirstName, layoutLastName, layoutAddress, layoutEmail, layoutNumberPhone;
    private PaymentButtonContainer paymentButtonContainer;
    private Button btnResume, btnLeave;
    private int TotalPrice;
    private String formattedResult;
    private LottieAnimationView paymentSuccessful;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_checkout);
        addControl();
        data();
        bindData();
        addEvent();

    }

    private int calculateTotalPrice() {
        return bags.stream().mapToInt(Bag::getTotalPrice).sum();
    }

    private int calculateTotalQuantity() {
        return bags.stream().mapToInt(Bag::getQuantity).sum();
    }

    private void addControl() {
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
        paymentButtonContainer = findViewById(R.id.payment_button_container);

    }

    private void bindData() {
        tvTotalPriceAndItem.setText(Util.formatCurrency(calculateTotalPrice()) + ",000đ ( " + calculateTotalQuantity() + " items )");
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
        bags = BagHandler.getBag(Util.getUserID(getApplicationContext()));
        adapter = new BagCheckOutAdapter(getApplicationContext(), R.layout.row_checkout_bag_item, bags);
        lvBagItem.setAdapter(adapter);
    }
    private boolean validateInput(){
        if(layoutFirstName.getEditText().getText().length() ==0){
            layoutFirstName.setError("Please enter your first name");
            return false;
        }else{
            layoutFirstName.setError(null);
        }
        if(layoutLastName.getEditText().getText().length() ==0){
            layoutLastName.setError("Please enter your last name");
            return false;
        }else{
            layoutLastName.setError(null);
        }
        if(layoutAddress.getEditText().getText().length()==0){
            layoutAddress.setError("Please enter your address");
            return false;
        }else{
            layoutAddress.setError(null);
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(layoutEmail.getEditText().getText().toString()).matches()){
            layoutEmail.setError("Invalid email format");
            return false;
        }else if(layoutEmail.getEditText().getText().length() ==0){
            layoutEmail.setError("Please enter your email");
            return false;
        }else {
            layoutEmail.setError(null);
        }
        if(layoutNumberPhone.getEditText().getText().length()==0){
            layoutNumberPhone.setError("Please enter your phone number");
            return false;
        }else if(layoutNumberPhone.getEditText().getText().length()>0 && layoutNumberPhone.getEditText().getText().length() < 10){
            layoutNumberPhone.setError("Please enter all 10 digits");
            return false;
        }else {
            layoutNumberPhone.setError(null);
        }

        return true;
    }
    private void addEvent() {
        layoutFirstName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()==0){
                    layoutFirstName.setError("Please enter your first name");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    layoutFirstName.setError("Please enter your first name");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    layoutFirstName.setError(null);
                }
            }
        });
        layoutLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()==0){
                    layoutLastName.setError("Please enter your last name");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    layoutLastName.setError("Please enter your last name");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    layoutLastName.setError(null);
                }
            }
        });
        layoutAddress.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()==0){
                    layoutAddress.setError("Please enter your address");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    layoutAddress.setError("Please enter your address");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    layoutAddress.setError(null);
                }
            }
        });
        layoutNumberPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()==0){
                    layoutNumberPhone.setError("Please enter your phone number");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    layoutNumberPhone.setError("Please enter your phone number");
                }
                if(s.length()>0 && s.length()<10 ){
                    layoutNumberPhone.setError("Please enter all 10 digits");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==10){
                    layoutNumberPhone.setError(null);
                }
            }
        });
        layoutEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()==0) {
                    layoutEmail.setError("Please enter your email");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0) {
                    layoutEmail.setError("Please enter your email");
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    layoutEmail.setError("Invalid email format");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    layoutEmail.setError(null);
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(1);
            }
        });
        btnBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(2);
            }
        });

        //Paypal button
        paymentButtonContainer.setup(
                createOrderActions -> {
                    if (!validateInput()) {
                        return;
                    }
                    ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value(formattedResult)
                                                    .build()
                                    ).build()
                    );
                    OrderRequest order = new OrderRequest(
                            OrderIntent.CAPTURE,
                            new AppContext.Builder()
                                    .userAction(UserAction.PAY_NOW)
                                    .build(),
                            purchaseUnits
                    );
                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                }
                , new OnApprove() {
                    @Override
                    public void onApprove(@NonNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NonNull CaptureOrderResult captureOrderResult) {
                                int user_id = Util.getUserID(getApplicationContext());
                                int primaryKey = UserOrderHandler.addOrder(user_id,layoutFirstName.getEditText().getText().toString(),layoutLastName.getEditText().getText().toString(),layoutAddress.getEditText().getText().toString(),layoutEmail.getEditText().getText().toString(),layoutNumberPhone.getEditText().getText().toString(),TotalPrice);
                                if(primaryKey!=-1){
                                    for(int i=0;i<bags.size();i++){
                                        Bag bag = bags.get(i);
                                        UserOrderProductsHandler.insertOrder(primaryKey,bag.getProductSizeID(),bag.getQuantity());
                                        ProductSizeHandler.updateTotalQuantity(bag.getProductSizeID(),bag.getQuantity());
                                    }
                                    BagHandler.removeAll(user_id);
                                }
                                showPopupSuccessful();


                            }
                        });
                    }
                }
        );


    }
    private void addControlPopupSuccessful(View view){
        paymentSuccessful = view.findViewById(R.id.lottiePaymentSuccessful);
    }
    private void showPopupSuccessful(){
        dialog = new Dialog(CheckoutActivity.this); // Use activity context here
        View convertView = LayoutInflater.from(CheckoutActivity.this).inflate(R.layout.payment_succesful_popup, null);
        addControlPopupSuccessful(convertView);
        dialog.setContentView(convertView);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        paymentSuccessful.animate().setDuration(3000).setStartDelay(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    intent.putExtra("navigateTo", "ShopFragment");
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }
    private void addControlPopup(View view) {
        btnLeave = view.findViewById(R.id.btnLeaveCheckout);
        btnResume = view.findViewById(R.id.btnResumeCheckout);
    }

    private void addEventPopup(int type) {
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (type == 1) {
            btnLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    intent.putExtra("navigateTo", "ShopFragment");
                    startActivity(intent);
                }
            });
        } else {
            btnLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    intent.putExtra("navigateTo", "BagFragment");
                    startActivity(intent);
                }
            });
        }
    }

    private void ShowPopup(int type) {
        dialog = new Dialog(CheckoutActivity.this); // Use activity context here
        View convertView = LayoutInflater.from(CheckoutActivity.this).inflate(R.layout.custom_dialog_leaving_popup, null);
        addControlPopup(convertView);
        addEventPopup(type);
        dialog.setContentView(convertView);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.TOP);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = window.getAttributes();
            params.x = 0;
            params.y = 50;
            window.setAttributes(params);
        }

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
