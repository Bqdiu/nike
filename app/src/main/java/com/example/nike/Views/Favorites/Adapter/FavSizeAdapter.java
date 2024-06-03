package com.example.nike.Views.Favorites.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.Product;
import com.example.nike.Model.ProductSize;
import com.example.nike.R;

import java.util.ArrayList;

public class FavSizeAdapter extends RecyclerView.Adapter<FavSizeAdapter.MyViewHolder> {

    private ArrayList<ProductSize> listSize = new ArrayList<>();
    public FavSizeAdapter(ArrayList<ProductSize> listSize) {
        this.listSize = listSize;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorite_item_size,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSize.setText(listSize.get(position).getSize().getName());
    }

    @Override
    public int getItemCount() {
        return listSize.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout btnSize;
        private TextView tvSize;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSize = itemView.findViewById(R.id.BtnSize);
            tvSize = itemView.findViewById(R.id.tvSize);
        }
    }
}
