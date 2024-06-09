package com.example.nike.Views.Profile.OrderFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nike.Model.UserOrder;
import com.example.nike.Model.UserOrderProducts;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

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
        return convertView;
    }
}
