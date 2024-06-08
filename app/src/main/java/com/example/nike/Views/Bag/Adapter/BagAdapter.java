package com.example.nike.Views.Bag.Adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Controller.BagHandler;
import com.example.nike.Controller.ProductHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Model.Bag;
import com.example.nike.Model.ProductSize;
import com.example.nike.R;
import com.example.nike.Views.Shop.Product.DetailProduct;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.MyViewHolder> {

    private ArrayList<Bag> list;
    private ItemClickListener itemClickListener;
    private FragmentManager fragmentManager;

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BagAdapter(ArrayList<Bag> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_bag, parent, false);
        return new MyViewHolder(view);
    }
    private String extractNumericPart(String sizeName) {

        int spaceIndex = sizeName.indexOf(' ');
        if (spaceIndex != -1 && spaceIndex + 1 < sizeName.length()) {
            return sizeName.substring(spaceIndex + 1);
        }

        return sizeName;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bag bag = list.get(position);
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(holder.itemView.getContext(),bag.getProduct().getImg());
        holder.imgThumbnail.setImageBitmap(bitmap);

        holder.tvProductName.setText(bag.getProduct().getName());
        holder.tvObjectName.setText(bag.getProduct().getCategoryName()+"'s "+bag.getProduct().getObjectName());
        holder.tvSize.setText(extractNumericPart(bag.getSizeName()));
        holder.tvShown.setText(bag.getProduct().getColorShown());
        holder.tvPrice.setText("đ"+Util.formatCurrency(bag.getTotalPrice())+",000");

        holder.btnQty.setText("Qty "+String.valueOf(bag.getQuantity()));
        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) v.getContext();
                DetailProduct detailProduct = DetailProduct.newInstance(bag.getProduct(), ProductHandler.getDataByParentID(bag.getProduct().getProductParentID()));
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, detailProduct)
                        .addToBackStack("Favorites")
                        .commit();
            }
        });
        holder.btnQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClickListener(bag);
            }
        });
    }
    public void updateData(ArrayList<Bag> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onItemClickListener(Bag bag);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardViewProduct;
        private ImageView imgThumbnail;
        private TextView tvProductName,tvObjectName,tvShown,tvSize,tvPrice;
        private AppCompatButton btnQty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewProduct = itemView.findViewById(R.id.cardViewProduct);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvObjectName = itemView.findViewById(R.id.tvObjectName);
            tvShown = itemView.findViewById(R.id.tvShown);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnQty = itemView.findViewById(R.id.btnQty);
        }
    }
}
