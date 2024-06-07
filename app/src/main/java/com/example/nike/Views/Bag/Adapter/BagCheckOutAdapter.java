package com.example.nike.Views.Bag.Adapter;

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

import com.example.nike.Model.Bag;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class BagCheckOutAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Bag> bags;
    private int layout;

    public BagCheckOutAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Bag> objects) {
        super(context, resource, objects);
        this.context = context;
        this.bags = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Bag bag = bags.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        ImageView thumbnail = convertView.findViewById(R.id.thumbnailProduct);
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(convertView.getContext(),bag.getProduct().getImg());
        thumbnail.setImageBitmap(bitmap);
        TextView tvNameProduct = convertView.findViewById(R.id.tvNameProduct);
        tvNameProduct.setText(bag.getProduct().getName());
        TextView tvObjects = convertView.findViewById(R.id.tvObjectName);
        tvObjects.setText(bag.getProduct().getObjectName()+"'s "+bag.getProduct().getCategoryName());
        TextView tvQty = convertView.findViewById(R.id.tvQty);
        tvQty.setText("Qty "+bag.getQuantity());
        TextView tvSize = convertView.findViewById(R.id.tvSize);
        tvSize.setText("Size "+bag.getSizeName());
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        tvPrice.setText(Util.formatCurrency(bag.getTotalPrice())+",000đ");

        return convertView;
    }
}
