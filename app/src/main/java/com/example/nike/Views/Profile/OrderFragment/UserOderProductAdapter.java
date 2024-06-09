package com.example.nike.Views.Profile.OrderFragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nike.Controller.BagHandler;
import com.example.nike.Model.UserOrder;
import com.example.nike.Model.UserOrderProducts;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserOderProductAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<UserOrderProducts> userOrderProducts;
    private int layout;

    public UserOderProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserOrderProducts> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userOrderProducts = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserOrderProducts userODP = userOrderProducts.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        ImageView thumbnail = convertView.findViewById(R.id.thumbnailProduct);
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(convertView.getContext(),userODP.getProduct().getImg());
        thumbnail.setImageBitmap(bitmap);
        TextView tvNameProduct = convertView.findViewById(R.id.tvNameProduct);
        tvNameProduct.setText(userODP.getProduct().getName());
        TextView tvObjects = convertView.findViewById(R.id.tvObjectName);
        tvObjects.setText(userODP.getProduct().getObjectName()+"'s "+userODP.getProduct().getCategoryName());
        TextView tvQty = convertView.findViewById(R.id.tvQty);
        tvQty.setText("Qty "+userODP.getAmount());
        TextView tvSize = convertView.findViewById(R.id.tvSize);
        tvSize.setText("Size "+userODP.getSizeName());
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        tvPrice.setText(Util.formatCurrency(userODP.getTotalPrice())+",000đ");
        TextView tvCreatedAt = convertView.findViewById(R.id.tvCreatedAt);
        String dateString = userODP.getDate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(dateString);
            String formattedDate = outputFormat.format(date);
            tvCreatedAt.setText("Date of purchase: " + formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Button btnBuyAgain = convertView.findViewById(R.id.btn_buy_again);
        btnBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToBag(userODP.getProduct_size_id());
            }
        });
        return convertView;
    }

    private void addToBag(int product_size_id) {
        int user_id = Util.getUserID(getContext());
        if (BagHandler.isExists(user_id, product_size_id)) {
            BagHandler.increaseQuantity(user_id, product_size_id);
        } else {
            BagHandler.addToBag(user_id, product_size_id, 1);
        }
        showPopupAddToBag();
    }

    private void showPopupAddToBag(){
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_to_bag_successfully_custom_popup,null);
        LottieAnimationView addToBagAnimation = convertView.findViewById(R.id.lottieAddToBag);
        TextView tvAddToBag = convertView.findViewById(R.id.tvAddToBag);

        tvAddToBag.setText("Added To Bag");
        addToBagAnimation.animate().setDuration(3000).setStartDelay(0);
        tvAddToBag.animate().setDuration(3000).setDuration(0);

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(convertView);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }

}
