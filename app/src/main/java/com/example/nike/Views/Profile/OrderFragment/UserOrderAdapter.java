package com.example.nike.Views.Profile.OrderFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.nike.FragmentUtils;
import com.example.nike.Model.UserOrderProducts;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserOrderAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<UserOrderProducts> userOrderProducts;
    private ArrayList<Integer> totalPriceList;
    private int layout;
    private int totalPriceOrder;

    public UserOrderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserOrderProducts> objects,@NonNull ArrayList<Integer> listTotalPrice) {
        super(context, resource, objects);
        this.context = context;
        this.userOrderProducts = objects;
        this.layout = resource;
        this.totalPriceList = listTotalPrice;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserOrderProducts userODP = userOrderProducts.get(position);
        totalPriceOrder = totalPriceList.get(position);
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
        TextView totalPrice = convertView.findViewById(R.id.totalPrice);
        totalPrice.setText("Total order price: " + Util.formatCurrency(totalPriceOrder)+",000đ");
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

        TextView tv_detail = convertView.findViewById(R.id.tv_detail);
        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) context;
                FragmentManager fm = activity.getSupportFragmentManager();

                DetailOrderFragment detailOrderFragment = DetailOrderFragment.newInstance(userODP.getUser_order_id(),totalPriceOrder);
                FragmentUtils.addFragment(fm, detailOrderFragment, R.id.frameLayout);
            }
        });

        return convertView;
    }
}
