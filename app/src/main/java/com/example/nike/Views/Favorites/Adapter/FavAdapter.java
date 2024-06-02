package com.example.nike.Views.Favorites.Adapter;

import static com.example.nike.Views.Util.formatCurrency;

import android.media.Image;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Controller.FavoriteProductHandler;
import com.example.nike.Controller.ProductHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.UserFavoriteProducts;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagClass;
import com.example.nike.Views.Shop.FragmentOfTabLayout.ObjectProduct;
import com.example.nike.Views.Util;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder>{
    private ArrayList<UserFavoriteProducts> listBag;
    private ArrayList<Product> list = new ArrayList<>();
    public FavAdapter(ArrayList<UserFavoriteProducts> listBag) {
        this.listBag = listBag;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_rv, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        UserFavoriteProducts pd = listBag.get(position);
        Product product = ProductHandler.getDetailProduct(pd.getProduct_id());
        holder.imgProFav.setImageBitmap(Util.convertStringToBitmapFromAccess(holder.itemView.getContext(),product.getImg()));
        holder.nameProFav.setText(product.getName());
        if(FavoriteProductHandler.CheckProductFavorite(Util.getUserID(holder.itemView.getContext()),product.getProductID())){
            product.setFavorite(true);
        }
        holder.priceProFav.setText(  "đ"+formatCurrency(product.getPrice()).replace(",", ".")+".000");
        if(product.isFavorite()==true){
            holder.btnFavorite.setBackgroundResource(R.drawable.baseline_favorite_red_24);
        }else {
            holder.btnFavorite.setBackgroundResource(R.drawable.baseline_favorite_border_24);
        }
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.loading.setVisibility(View.VISIBLE); // Show progress bar
                holder.btnFavorite.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (product.isFavorite()) {
                            product.setFavorite(false);
                            holder.btnFavorite.setBackgroundResource(R.drawable.baseline_favorite_border_24);
                            FavoriteProductHandler.removeFavoriteProduct(Util.getUserID(v.getContext()), product.getProductID());
                        } else {
                            product.setFavorite(true);
                            holder.btnFavorite.setBackgroundResource(R.drawable.baseline_favorite_red_24);
                            FavoriteProductHandler.insertFavoriteProduct(Util.getUserID(v.getContext()), product.getProductID());
                        }
                        holder.loading.setVisibility(View.GONE); // Hide progress bar
                        holder.btnFavorite.setEnabled(true);

                    }
                }, 1000); // Set delay duration in milliseconds (1000 ms = 1 second)



            }
        });
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

        public Button btnFavorite;

        private ProgressBar loading;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProFav = itemView.findViewById(R.id.imgProduct);
            nameProFav = itemView.findViewById(R.id.nameProduct);
            priceProFav = itemView.findViewById(R.id.priceProduct);
            btnFavorite = itemView.findViewById(R.id.btnFavoriteIcon);
            loading = itemView.findViewById(R.id.loading);
        }
    }

}
