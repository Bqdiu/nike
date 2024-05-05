package com.example.nike.Views.Shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nike.Model.ProductSize;
import com.example.nike.Model.Size;
import com.example.nike.R;

import java.util.ArrayList;

public class SizeItemAdapter extends ArrayAdapter {
    private ArrayList<ProductSize> productSizeArrayList = new ArrayList<>();
    private Context context;
    private int layout;
    private int selectedItem=1;

    public SizeItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ProductSize> objects) {
        super(context, resource, objects);
        this.productSizeArrayList = objects;
        this.context = context;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductSize productSize = productSizeArrayList.get(position);
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        TextView nameSize = convertView.findViewById(R.id.sizeName);
        nameSize.setText(productSize.getSize().getName());
        ImageView checked = convertView.findViewById(R.id.itemCheck);
        if(productSize.isSelect() == false){
            checked.setVisibility(View.GONE);
        }
        else {
            checked.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

}
