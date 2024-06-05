package com.example.nike.Views.Shop.Adapter;

import static com.example.nike.Views.Util.formatCurrency;

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

import com.example.nike.Controller.ProductHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.ShopByIcons;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class ItemRecycleViewAdapter extends RecyclerView.Adapter<ItemRecycleViewAdapter.MyViewHolder> {

    private ArrayList<ProductParent> list = new ArrayList<>();
    private ArrayList<Product> listProduct = new ArrayList<>();
    private Context context;
    private ItemClickListener itemClickListener;
    public ItemRecycleViewAdapter(Context context, ArrayList<ProductParent> list, ItemClickListener itemClickListener) {

        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_rv,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductParent pp = list.get(position);

        Bitmap bitmap = Util.convertStringToBitmapFromAccess(holder.itemView.getContext(),pp.getThumbnail());
        holder.imgProduct.setImageBitmap(bitmap);
        holder.nameProduct.setText(pp.getName());

        holder.priceProduct.setText("đ"+formatCurrency(pp.getPrice()).replace(",", ".")+".000");
        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProduct = ProductHandler.getDataByParentID(pp.getId());
                itemClickListener.onItemClick(listProduct);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView nameProduct;
        TextView priceProduct;
        CardView cardViewItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            priceProduct = itemView.findViewById(R.id.priceProduct);
            cardViewItem = itemView.findViewById(R.id.cardViewItem);
        }
    }
    public interface ItemClickListener {
        void onItemClick(ArrayList<Product> product);

    }
}
