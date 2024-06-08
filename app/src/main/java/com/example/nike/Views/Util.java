package com.example.nike.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;

import com.example.nike.Controller.BagHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Model.Bag;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Util {
    public static Bitmap blur(Context context, Bitmap image) {
        final float BITMAP_SCALE = 0.4f;
        final float BLUR_RADIUS = 6f;

        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        scriptIntrinsicBlur.setRadius(BLUR_RADIUS);
        scriptIntrinsicBlur.setInput(tmpIn);
        scriptIntrinsicBlur.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
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
    public static int getUserID(Context view){
        SharedPreferences sharedPreferences;
        sharedPreferences = view.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String email = sharedPreferences.getString("email",null).toString();
        int UserID = UserAccountHandler.getUserByEmail(email).getId();
        return UserID;
    }
    public static ArrayList<Bag> bags;

}
