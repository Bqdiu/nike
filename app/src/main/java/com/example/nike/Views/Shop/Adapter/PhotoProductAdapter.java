package com.example.nike.Views.Shop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductImage;
import com.example.nike.R;
import com.example.nike.Views.Util;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class PhotoProductAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<ProductImage> productArrayList;


    public PhotoProductAdapter(Context mContext, ArrayList<ProductImage> productArrayList) {
        this.mContext = mContext;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_slide,container,false);
        ProductImage productPhoto = productArrayList.get(position);
        ImageView photo = view.findViewById(R.id.img_product);
        if(productPhoto!=null){
            Bitmap bitmap = Util.convertStringToBitmapFromAccess(container.getContext(), productPhoto.getFileName());

            int newHeight = 1200; // Chiều cao mới

            Bitmap resizeImage = Bitmap.createScaledBitmap(bitmap, 1150, newHeight, true);
            Glide.with(mContext).load(resizeImage).into(photo);
        }
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(productArrayList!=null){
            return productArrayList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
