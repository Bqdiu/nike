package com.example.nike.Views.Favorites.Adapter;

import android.graphics.Color;
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
import com.example.nike.Views.Favorites.FavoriteFragment;

import java.util.ArrayList;

public class FavSizeAdapter extends RecyclerView.Adapter<FavSizeAdapter.MyViewHolder> {

    private ArrayList<ProductSize> listSize = new ArrayList<>();
    public FavSizeAdapter(ArrayList<ProductSize> listSize) {
        this.listSize = listSize;
    }
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorite_item_size,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductSize productSize = listSize.get(position);
        holder.tvSize.setText(productSize.getSize().getName());
        if(productSize.getSoluong() == 0){
            holder.tvSize.setBackgroundResource(R.drawable.custom_btn_size_favorite_sold_out);
            holder.tvSize.setTextColor(Color.parseColor("#777272"));
            holder.tvSize.setClickable(true);
        }else{
            if(productSize.isSelect() == false){
            holder.tvSize.setBackgroundResource(R.drawable.custom_btn_size_favorite_non_select);
            }
            if(productSize.isSelect() == true){
                holder.tvSize.setBackgroundResource(R.drawable.custom_btn_size_favorite_selected);
            }
            holder.tvSize.setTextColor(Color.BLACK);
            holder.tvSize.setClickable(false);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.onSizeSelected(productSize);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSize.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public interface ItemClickListener{
        void onSizeSelected(ProductSize productSize);
    }
    public void UpdateSelectedSize(ArrayList<ProductSize> productSizes){
        listSize = productSizes;
        notifyDataSetChanged();
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
