package com.example.nike.Views.Shop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.Model.ProductReview;
import com.example.nike.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReviewRecycleViewAdapter extends RecyclerView.Adapter<ReviewRecycleViewAdapter.MyViewHolder> {

    private ArrayList<ProductReview> productReviews = new ArrayList<>();

    public ReviewRecycleViewAdapter(ArrayList<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reviews,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductReview p = productReviews.get(position);
        holder.tvReviewTitle.setText(p.getReviewTitle());
        holder.tvUserName.setText(p.getUserEmail());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate = LocalDate.of(p.getReviewTime().getYear(),p.getReviewTime().getMonth(),p.getReviewTime().getDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
            String formattedDate = localDate.format(formatter);
            holder.tvDateTime.setText(formattedDate);
        }
        holder.ratingBar.setRating((float) p.getReviewRate());
        holder.tvContent.setText(p.getReviewContent());

    }

    @Override
    public int getItemCount() {
        return productReviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvReviewTitle,tvUserName,tvDateTime,tvContent;
        private RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewTitle = itemView.findViewById(R.id.reviewsTitle);
            tvUserName = itemView.findViewById(R.id.tvUsername);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
