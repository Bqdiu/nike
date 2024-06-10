package com.example.nike.Views.Shop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Controller.IconsHandler;
import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.ShopByIcons;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

public class IconsItemRecycleViewAdapter extends RecyclerView.Adapter<IconsItemRecycleViewAdapter.MyViewHolder> {

    int object_id;
    ArrayList<Integer> shopByIcons = new ArrayList<>();
    Context context;

    private IconItemClickListener itemClickListener;

    public IconsItemRecycleViewAdapter(ArrayList<Integer> shopByIcons, int object_id, Context context) {
        this.shopByIcons = shopByIcons;
        this.context = context;
        this.object_id = object_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_icons_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int icon_id_position = shopByIcons.get(position);
        ShopByIcons icons = IconsHandler.getDetailByIconId(icon_id_position);
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(holder.itemView.getContext(),icons.getThumbnail());
        holder.thumbnail.setImageBitmap(bitmap);
        holder.tvNameIcons.setText(icons.getName());
        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ProductParent> list = ProductParentHandler.getAllProductParentByIconAndObjectID(icons.getId(),object_id);
                if (itemClickListener != null) {
                    itemClickListener.onIconItemClick(icons.getName(),list);
                } else {
                    Toast.makeText(context, "ItemClickListener is not set", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public interface IconItemClickListener {
        void onIconItemClick(String name, ArrayList<ProductParent> list);
    }

    public void setIconItemClickListener(IconItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
