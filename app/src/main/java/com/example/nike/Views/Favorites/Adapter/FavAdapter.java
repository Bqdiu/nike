package com.example.nike.Views.Favorites.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.ProductParent;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagClass;
import com.example.nike.Views.Util;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder>{
    private ArrayList<ProductParent> listBag;

    public FavAdapter(ArrayList<ProductParent> listBag) {
        this.listBag = listBag;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorite, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        ProductParent pd = listBag.get(position);
        holder.imgProFav.setImageBitmap(Util.convertStringToBitmapFromAccess(holder.itemView.getContext(),pd.getThumbnail()));
        holder.nameProFav.setText(pd.getName());
        holder.priceProFav.setText(String.valueOf(pd.getPrice()));
    }

    @Override
    public int getItemCount() {
        if(listBag != null)
            return listBag.size();
        return 0;
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProFav;
        private TextView nameProFav;
        private TextView priceProFav;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProFav = itemView.findViewById(R.id.imgProFav);
            nameProFav = itemView.findViewById(R.id.nameProFav);
            priceProFav = itemView.findViewById(R.id.priceProFav);
        }
    }
}
