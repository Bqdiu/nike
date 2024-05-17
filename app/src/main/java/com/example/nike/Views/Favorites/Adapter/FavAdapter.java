package com.example.nike.Views.Favorites.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagClass;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder>{
    private List<BagClass> listFavBag;

    public FavAdapter(List<BagClass> listFavBag) {
        this.listFavBag = listFavBag;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorite, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        BagClass favBag = listFavBag.get(position);
        if(favBag == null){
            return;
        }
        holder.imgProFav.setImageResource(favBag.getIdImg());
        holder.nameProFav.setText(favBag.getName());
        holder.priceProFav.setText(String.valueOf(favBag.getPrice()));
    }

    @Override
    public int getItemCount() {
        if(listFavBag != null)
            return listFavBag.size();
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
