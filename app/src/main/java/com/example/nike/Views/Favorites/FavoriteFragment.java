package com.example.nike.Views.Favorites;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.nike.Controller.FavoriteProductHandler;
import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.ProductSize;
import com.example.nike.Model.UserAccount;
import com.example.nike.Model.UserFavoriteProducts;
import com.example.nike.R;
import com.example.nike.Views.Bag.Adapter.BagClass;
import com.example.nike.Views.Favorites.Adapter.FavAdapter;
import com.example.nike.Views.Favorites.Adapter.FavSizeAdapter;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.SizeItemAdapter;
import com.example.nike.Views.Shop.ShopFragment;
import com.example.nike.Views.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavAdapter.ItemClickListener {

    private RecyclerView recycleFav;
    private ArrayList<UserFavoriteProducts> list;
    private SharedPreferences sharedPreferences;

    private FavAdapter adapter;
    private ToggleButton toggleBtnFavorite;
    private Button btnShopNow;

    //Popup Control
    private RelativeLayout relativeNonData;
    private Dialog dialog;
    private FavSizeAdapter sizeAdapter;
    private ArrayList<ProductSize> listSize;
    private RecyclerView recycleSize;

    private CardView cardView;
    private ImageView imgProduct;
    private TextView tvNameProduct;
    private TextView tvNameOfObject;
    private TextView tvStyle;
    private TextView tvPrice;
    private Button btnAddToBag;


    private void addControls(View view)
    {

        recycleFav = view.findViewById(R.id.recycleFav);
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        toggleBtnFavorite = view.findViewById(R.id.toggleBtnFavorite);
        btnShopNow = view.findViewById(R.id.btnShopingNow);
        relativeNonData = view.findViewById(R.id.relativeNonData);

        dialog = new Dialog(getContext());
    }

    private void addEvent(){
        toggleBtnFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFavoriteIconsVisibility(isChecked);
                if(isChecked == false){
                    Toast.makeText(getContext(),"Update Successful",Toast.LENGTH_LONG).show();
                    LoadRecycleView();
                }

            }
        });
        btnShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadFragment(new ShopFragment());
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                bottomNavigationView.setSelectedItemId(R.id.itemShop);
            }
        });
    }
    private void updateFavoriteIconsVisibility(boolean showFavoritesOnly) {
        for (int i = 0; i < recycleFav.getChildCount(); i++) {
            RecyclerView.ViewHolder holder = recycleFav.getChildViewHolder(recycleFav.getChildAt(i));
            if (holder instanceof FavAdapter.FavViewHolder) {
                FavAdapter.FavViewHolder favViewHolder = (FavAdapter.FavViewHolder) holder;
                favViewHolder.btnFavorite.setVisibility(showFavoritesOnly ? View.VISIBLE : View.GONE);
            }
        }

    }
    private void LoadFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    private void LoadRecycleView(){
        list = new ArrayList<>();
        list = FavoriteProductHandler.getData(Util.getUserID(getContext()));
        if(list.size()>0){
          //  relativeNonData.setVisibility(View.GONE);
          //  btnShopNow.setVisibility(View.GONE);
            adapter = new FavAdapter(list,this::onItemClick);
            recycleFav.setAdapter(adapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recycleFav.setLayoutManager(gridLayoutManager);
        }
        else{
           relativeNonData.setVisibility(View.VISIBLE);
           btnShopNow.setVisibility(View.VISIBLE);
            adapter = new FavAdapter(list,this::onItemClick);
            recycleFav.setAdapter(adapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recycleFav.setLayoutManager(gridLayoutManager);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        addControls(view);

        LoadRecycleView();
        addEvent();
        return view;
    }
    private void addEventPopup(Product product){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Selected " + product.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void BindDataOfPopup(Product product){
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(getContext(),product.getImg());
        imgProduct.setImageBitmap(bitmap);

        tvNameProduct.setText(product.getName());
        tvStyle.setText(product.getStyleCode());
       tvPrice.setText("đ"+Util.formatCurrency(product.getPrice())+",000");
       tvNameOfObject.setText(product.getObjectName()+"'s "+product.getCategoryName());
    }
    private void ShowPopup(Product product){
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_favorite_popup,null);
        addControlOfPopupMenu(convertView,product);
        BindDataOfPopup(product);
        addEventPopup(product);
        dialog.setContentView(convertView);
        //dialog.setCancelable(true); // Cho phép đóng Dialog khi chạm ra ngoài


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true); // Cho phép đóng Dialog khi chạm ra ngoài
        dialog.show();
    }
    private void addControlOfPopupMenu(View view, Product product){
        recycleSize = view.findViewById(R.id.recycleSize);
        //Set Data ListView of Size
        listSize = ProductSizeHandler.getDataByProductID(product.getProductID());
        sizeAdapter = new FavSizeAdapter(listSize);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false);
        recycleSize.setLayoutManager(layoutManager);
        recycleSize.setAdapter(sizeAdapter);
        //Control of Popup
        cardView = view.findViewById(R.id.cardViewProduct);
        imgProduct = view.findViewById(R.id.imgProduct);
        tvNameProduct = view.findViewById(R.id.tvNameProduct);
        tvNameOfObject = view.findViewById(R.id.tvObject);
        tvStyle = view.findViewById(R.id.tvStyle);
        tvPrice = view.findViewById(R.id.tvPrice);

        btnAddToBag = view.findViewById(R.id.btnAddToBag);




    }

    @Override
    public void onItemClick(Product product) {
        ShowPopup(product);
    }

}