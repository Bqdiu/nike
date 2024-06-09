package com.example.nike.Views.Favorites;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import android.os.Handler;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.nike.Controller.BagHandler;
import com.example.nike.Controller.FavoriteProductHandler;
import com.example.nike.Controller.ProductHandler;
import com.example.nike.Controller.ProductParentHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.Controller.UserOrderHandler;
import com.example.nike.MainActivity;
import com.example.nike.Model.Bag;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductParent;
import com.example.nike.Model.ProductSize;
import com.example.nike.Model.UserAccount;
import com.example.nike.Model.UserFavoriteProducts;
import com.example.nike.Model.UserOrder;
import com.example.nike.R;

import com.example.nike.Views.Bag.BagFragment;
import com.example.nike.Views.Bag.CheckoutActivity;
import com.example.nike.Views.Favorites.Adapter.FavAdapter;
import com.example.nike.Views.Favorites.Adapter.FavSizeAdapter;
import com.example.nike.Views.Shop.Adapter.ItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.SizeItemAdapter;
import com.example.nike.Views.Shop.Product.DetailProduct;
import com.example.nike.Views.Shop.ShopFragment;
import com.example.nike.Views.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavAdapter.ItemClickListener,FavSizeAdapter.ItemClickListener {

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
    private TextView tvPrice,titleSize;
    private Button btnAddToBag;
    private Button btnMoreDetails;
    private TextView tvValidation;
    private Boolean isSelectedSize = false;
    private void addControls(View view)
    {

        recycleFav = view.findViewById(R.id.recycleFav);
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        toggleBtnFavorite = view.findViewById(R.id.toggleBtnFavorite);
        btnShopNow = view.findViewById(R.id.btnShopingNow);
        relativeNonData = view.findViewById(R.id.relativeNonData);
        btnAddToBag = view.findViewById(R.id.btnAddToBag);
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
                onItemDetailClick(product);
                dialog.dismiss();
            }
        });
        btnMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDetailClick(product);
                dialog.dismiss();
            }
        });
        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelectedSize == false){
                        tvValidation.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void onItemDetailClick(Product product){
        DetailProduct detailProduct = DetailProduct.newInstance(product,ProductHandler.getDataByParentID(product.getProductParentID()));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,detailProduct).addToBackStack("Favorites").commit();

    }
    private int totalQuantityProduct(){
        return listSize.stream().mapToInt(ProductSize::getSoluong).sum();
    }
    private void BindDataOfPopup(Product product){
        Bitmap bitmap = Util.convertStringToBitmapFromAccess(getContext(),product.getImg());
        imgProduct.setImageBitmap(bitmap);
        tvNameProduct.setText(product.getName());
        tvStyle.setText(product.getStyleCode());
       tvPrice.setText("đ"+Util.formatCurrency(product.getPrice())+",000");
       tvNameOfObject.setText(product.getObjectName()+"'s "+product.getCategoryName());

       if(totalQuantityProduct() == 0){
           recycleSize.setVisibility(View.GONE);
           btnAddToBag.setVisibility(View.GONE);
           titleSize.setVisibility(View.GONE);
           btnMoreDetails.setVisibility(View.VISIBLE);
       }else {
           recycleSize.setVisibility(View.VISIBLE);
           btnAddToBag.setVisibility(View.VISIBLE);
           titleSize.setVisibility(View.VISIBLE);
           btnMoreDetails.setVisibility(View.GONE);
       }
    }
    private void showPopUpAddToBagSuccessful(){
        dialog = new Dialog(getContext());
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_favorites_add_to_bag_popup,null);
        LottieAnimationView lottieSuccessful = convertView.findViewById(R.id.lottieAddToBag);
        TextView tvAddToBag = convertView.findViewById(R.id.tvAddToBag);
        Button btnViewBag = convertView.findViewById(R.id.btnViewBag);
        dialog.setContentView(convertView);


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true); // Cho phép đóng Dialog khi chạm ra ngoài
        dialog.show();

        lottieSuccessful.animate().setDuration(3000).setStartDelay(0);
        tvAddToBag.animate().setStartDelay(3000).setStartDelay(0);
        btnViewBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LoadFragment(new BagFragment());
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                bottomNavigationView.setSelectedItemId(R.id.itemBag);
            }
        });


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
        sizeAdapter.setItemClickListener(this);
        btnAddToBag = view.findViewById(R.id.btnAddToBag);
        btnMoreDetails = view.findViewById(R.id.btnMoreDetails);
        titleSize = view.findViewById(R.id.sizeTitle);
        tvValidation = view.findViewById(R.id.tvValidationSelectSize);

    }

    @Override
    public void onItemClick(Product product) {
        ShowPopup(product);
    }


    @Override
    public void onSizeSelected(ProductSize productSize) {
        for (ProductSize size : listSize) {
            size.setSelect(false);
        }
        productSize.setSelect(true);
        sizeAdapter.UpdateSelectedSize(listSize);
        isSelectedSize = true;
        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id = Util.getUserID(getContext());
                int product_size_id =  productSize.getProduct_size_id();
                if(product_size_id != -1){
                    tvValidation.setVisibility(View.GONE);
                    if(BagHandler.isExists(user_id,product_size_id) && productSize.getSoluong()!=0){
                        BagHandler.increaseQuantity(user_id,product_size_id);
                        isSelectedSize = false;
                    }else if(!BagHandler.isExists(user_id,product_size_id) && productSize.getSoluong()!=0){
                        BagHandler.addToBag(user_id,product_size_id, 1);
                        isSelectedSize = false;
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                                showPopUpAddToBagSuccessful();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 500);
                }




            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}