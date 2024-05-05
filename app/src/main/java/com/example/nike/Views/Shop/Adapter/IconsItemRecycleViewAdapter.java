package com.example.nike.Views.Shop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.ShopByIcons;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class IconsItemRecycleViewAdapter extends RecyclerView.Adapter<IconsItemRecycleViewAdapter.MyViewHolder> {

    ArrayList<ShopByIcons> shopByIcons = new ArrayList<>();
    Context context;


    public IconsItemRecycleViewAdapter(ArrayList<ShopByIcons> shopByIcons, Context context) {
        this.shopByIcons = shopByIcons;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_icons_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShopByIcons icons = shopByIcons.get(position);
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(holder.itemView.getContext(),icons.getThumbnail());
        holder.thumbnail.setImageBitmap(bitmap);
        holder.tvNameIcons.setText(icons.getName());
    }

    @Override
    public int getItemCount() {
        return shopByIcons.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameIcons;
        ImageView thumbnail;
        CardView cardViewItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail= itemView.findViewById(R.id.imgIcons);
            tvNameIcons = itemView.findViewById(R.id.nameIcons);
            cardViewItem = itemView.findViewById(R.id.cardViewIcon);
        }
    }
    public interface ItemClickListener {
        void onItemClick(ArrayList<ProductParent> product);

    }
}
