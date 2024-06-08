package com.example.nike.Views.Shop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private SizeSelectedListener sizeSelectedListener;



    public SizeItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ProductSize> objects) {
        super(context, resource, objects);
        this.productSizeArrayList = objects;
        this.context = context;
        this.layout = resource;
        this.sizeSelectedListener = sizeSelectedListener;
    }
    public interface SizeSelectedListener{
        void onSizeSelected(ProductSize productSize);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductSize productSize = productSizeArrayList.get(position);
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        RelativeLayout relativeSize = convertView.findViewById(R.id.relativeSize);
        TextView nameSize = convertView.findViewById(R.id.sizeName);
        nameSize.setText(productSize.getSize().getName());
        ImageView checked = convertView.findViewById(R.id.itemCheck);
        if(productSize.getSoluong() == 0){
            relativeSize.setBackgroundColor(Color.parseColor("#EAEAEA"));
            nameSize.setTextColor(Color.parseColor("#777272"));
            relativeSize.setClickable(true);
        }else {
            relativeSize.setBackgroundColor(Color.TRANSPARENT);
            nameSize.setTextColor(Color.BLACK);
            relativeSize.setClickable(false);
        }
        if(productSize.isSelect() == false){

            checked.setVisibility(View.GONE);
        }
        else {
            checked.setVisibility(View.VISIBLE);

        }
        return convertView;
    }

}
