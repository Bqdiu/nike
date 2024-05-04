package com.example.nike.Views.Shop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.strictmode.UntaggedSocketViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.Product;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class PhotoRecycleViewAdapter extends RecyclerView.Adapter<PhotoRecycleViewAdapter.MyViewHolder> {

    ArrayList<Product> products = new ArrayList<>();
    Context context;
    ItemClickListener itemClickListener;

    public PhotoRecycleViewAdapter(ArrayList<Product> list, Context context, ItemClickListener itemClickListener) {
        this.products = list;
        this.context = context;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_recycle_photo,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String itemSrc = products.get(position).getImg();
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(context,itemSrc);
        holder.thumbnail.setImageBitmap(bitmap);
        holder.photoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClickListener.onPhotoClick(products.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;
        CardView photoCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.photoThumbnail);
            photoCardView = itemView.findViewById(R.id.photoCardView);
        }
    }
    public interface ItemClickListener{
        void onPhotoClick(Product product);
    }
}
