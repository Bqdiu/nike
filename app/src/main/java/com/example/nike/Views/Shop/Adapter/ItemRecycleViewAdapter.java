package com.example.nike.Views.Shop.Adapter;

import static com.example.nike.Views.Util.formatCurrency;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.Product;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class ItemRecycleViewAdapter extends RecyclerView.Adapter<ItemRecycleViewAdapter.MyViewHolder> {

    ArrayList<Product> list = new ArrayList<>();

    public ItemRecycleViewAdapter(ArrayList<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_rv,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = list.get(position);
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(holder.itemView.getContext(),product.getImg());
        holder.imgProduct.setImageBitmap(bitmap);
        holder.nameProduct.setText(product.getName());
        holder.priceProduct.setText("đ"+formatCurrency(product.getPrice()).replace(",", ".")+".000");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView nameProduct;
        TextView priceProduct;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            priceProduct = itemView.findViewById(R.id.priceProduct);
        }
    }
}
