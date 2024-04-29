package com.example.nike.Views;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class Util {

    public static Bitmap convertStringToBitmapFromAccess(Context context,String filename){
        AssetManager assetManager = context.getAssets();
        try{
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static String formatCurrency(int price) {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(price);
    }
}
