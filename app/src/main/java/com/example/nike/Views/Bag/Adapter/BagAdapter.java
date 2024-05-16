package com.example.nike.Views.Bag.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nike.R;

import java.util.List;

public class BagAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BagClass> arraylist;


    public BagAdapter(Context context, int layout, List<BagClass> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }



    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        BagClass bag = arraylist.get(position);
        //ÁNH XẠ BÊN LAYOUT
        TextView ten = convertView.findViewById(R.id.namePro);
        TextView mota = convertView.findViewById(R.id.desPro);
        TextView mau = convertView.findViewById(R.id.colorPro);
        TextView size = convertView.findViewById(R.id.sizePro);
        TextView sluong = convertView.findViewById(R.id.Quantity);
        TextView gia = convertView.findViewById(R.id.pricePro);
        ImageView anh = convertView.findViewById(R.id.imgPro);

        //ÁNH XẠ VÀO CLASS
        ten.setText(bag.getName());
        mota.setText(bag.getDes());
        mau.setText(bag.getColor());
        size.setText(String.valueOf(bag.getSize()));
        sluong.setText(String.valueOf(bag.getQuantity()));
        gia.setText(String.valueOf(bag.getPrice()));
        anh.setImageResource(bag.getIdImg());

        return convertView;
    }
}
