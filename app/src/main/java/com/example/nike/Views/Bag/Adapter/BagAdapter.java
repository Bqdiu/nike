package com.example.nike.Views.Bag.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.Product;
import com.example.nike.R;

import java.util.List;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public BagAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_bag, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.eachCartItemIV);
            nameTextView = itemView.findViewById(R.id.Name_Pro);
            descriptionTextView = itemView.findViewById(R.id.Des_Pro);
            priceTextView = itemView.findViewById(R.id.Price_Pro);
        }

        public void bind(Product product) {
            //productImageView.setImageResource(product.getImg());
            nameTextView.setText(product.getName());
            descriptionTextView.setText(product.getDescription());
            priceTextView.setText(String.valueOf(product.getPrice()));

            // Xử lý sự kiện khi người dùng nhấp vào sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện tương ứng (ví dụ: mở màn hình chi tiết sản phẩm)
                }
            });
        }
    }
}
