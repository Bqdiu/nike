package com.example.nike.Views.Shop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.strictmode.UntaggedSocketViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductSize;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class PhotoRecycleViewAdapter extends RecyclerView.Adapter<PhotoRecycleViewAdapter.MyViewHolder> {

    ArrayList<Product> products = new ArrayList<>();
    ArrayList<ProductSize> listSize = new ArrayList<>();
    Context context;
    ItemClickListener itemClickListener;
    private int selected= 0;

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
    private int totalQuantityProduct(){
        return listSize.stream().mapToInt(ProductSize::getSoluong).sum();
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product =  products.get(position);
        listSize = ProductSizeHandler.getDataByProductID(product.getProductID());
        String itemSrc = product.getImg();
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(context,itemSrc);
        if(totalQuantityProduct() == 0 ){
            holder.tvOutOfStock.setVisibility(View.VISIBLE);
        }else{
            holder.tvOutOfStock.setVisibility(View.GONE);
        }
        if (selected == position) {
            // Apply blur effect if this is the selected item
            holder.thumbnail.setImageBitmap(Util.blur(context, bitmap));
        } else {
            holder.thumbnail.setImageBitmap(bitmap);
        }
        holder.photoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = position;
                notifyDataSetChanged();
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
        TextView tvOutOfStock;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.photoThumbnail);
            photoCardView = itemView.findViewById(R.id.photoCardView);
            tvOutOfStock = itemView.findViewById(R.id.tvOutOfStock);
        }
    }
    public interface ItemClickListener{
        void onPhotoClick(Product product);
    }
}
