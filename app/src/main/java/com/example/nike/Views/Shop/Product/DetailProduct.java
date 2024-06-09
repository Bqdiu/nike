package com.example.nike.Views.Shop.Product;

import static com.example.nike.Views.Util.bags;
import static com.example.nike.Views.Util.formatCurrency;
import static com.example.nike.Views.Util.getUserID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nike.Controller.BagHandler;
import com.example.nike.Controller.FavoriteProductHandler;
import com.example.nike.Controller.ImageHandler;
import com.example.nike.Controller.ProductReviewHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.FragmentUtils;
import com.example.nike.MainActivity;
import com.example.nike.Model.Bag;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductImage;
import com.example.nike.Model.ProductReview;
import com.example.nike.Model.ProductSize;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.PhotoProductAdapter;
import com.example.nike.Views.Shop.Adapter.PhotoRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.ReviewRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.SizeItemAdapter;
import com.example.nike.Views.Util;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProduct extends Fragment implements PhotoRecycleViewAdapter.ItemClickListener {

    private ImageButton btnBack;
    private TextView tvNameFragment;
    private ArrayList<ProductImage> photoList = new ArrayList<>();
    private RecyclerView photoRecycleView;
    private PhotoRecycleViewAdapter photoReAdapter;
    private ArrayList<String> thumbnailString = new ArrayList<>();
    private ArrayList<Integer> idProductOfThumbnail = new ArrayList<>();
    private ViewPager imagePager;
    private CircleIndicator circleIndicator;
    private PhotoProductAdapter adapter;
    private TextView tvObject,tvNameProduct,tvPrice,tvDescription,tvStyle,tvShown;

    // Select Size
    ProductSize productSize;
    private FrameLayout container;
    private Button btnSpinnerSize;
    Dialog dialog;
    private ListView listViewSize;
    private TextView btnClose;

    private SizeItemAdapter sizeAdapter;

    private ArrayList<ProductSize> listSize;

    private Product CurrentProduct;
    private UserAccount user;
    private Button btnAddToWhistList,btnOutOfStock;
    private SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "object_product";
    private static final String CurPro = "CurrentProduct";

    // TODO: Rename and change types of parameters
    private ArrayList<Product> mProduct;
    private Button btnAddToBag;
    private AppCompatButton btnSizeGuide;
    private TextView tvSizeGuide;
    private LinearLayout expandSizeGuide;

    private LinearLayout expandReviews;
    private CardView cardViewReviews;
    private Button btnWriteReviews;
    private AppCompatButton btnReviews;
    private RatingBar ratingBarReviewsTitle;
    private RecyclerView recyclerReviews;
    private ArrayList<ProductReview> productReviews;
    private ReviewRecycleViewAdapter reviewRecycleViewAdapter;
    private Button btnMoreReviews;
    private int objectID;
    private int categoryID;
    private int selectedItem = -1;
    private boolean isExpandedSizeGuide = false;
    private boolean isExpandedReviews = false;
    public DetailProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @return A new instance of fragment DetailProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProduct newInstance(ArrayList<Product> list) {
        DetailProduct fragment = new DetailProduct();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @param curPro Parameter 2.
     * @return A new instance of fragment DetailProduct.
     */
    public static DetailProduct newInstance(Product curPro,ArrayList<Product> list) {
        DetailProduct fragment = new DetailProduct();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, list);
        args.putSerializable(CurPro,curPro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (ArrayList<Product>) getArguments().getSerializable(ARG_PARAM1);
            if(getArguments().getSerializable("CurrentProduct")!=null){
                CurrentProduct = (Product) getArguments().getSerializable("CurrentProduct");

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);

        Activity currentActivity = getActivity();
        if (currentActivity instanceof MainActivity) {
            addControl(view,currentActivity);
            addEvent();
            bindingDataOfProduct(mProduct.get(0));
            if(CurrentProduct!=null){
                setDefaultVALUEOfCurrentProduct(CurrentProduct);
            }

            setDataRecycleViewPhotoList();
            dialog = new Dialog(getContext());

        }


        return view;
    }
    private void addControl(View view,Activity currentActivity) {
        // Nếu Activity là loại Activity bạn mong muốn, bạn có thể tìm kiếm ImageButton từ đó
        btnBack = currentActivity.findViewById(R.id.btnBack);
        // Kiểm tra xem ImageButton có null hay không trước khi thực hiện thay đổi
        if (btnBack != null) {
            btnBack.setVisibility(View.VISIBLE);

        }
        productSize = null;
        tvNameFragment = currentActivity.findViewById(R.id.tvNameOfFragment);
        tvNameFragment.setText(mProduct.get(0).getName());
        //Image Product Slider
        imagePager = view.findViewById(R.id.viewPagerProduct_photo);
        circleIndicator = view.findViewById(R.id.circle_Indicator);
        //thumbnailRecycle View
        photoRecycleView = view.findViewById(R.id.recycleViewPhotoList);

        tvObject = view.findViewById(R.id.tvObject);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvNameProduct = view.findViewById(R.id.tvNameProduct);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvShown = view.findViewById(R.id.tvShown);
        tvStyle = view.findViewById(R.id.tvStyle);

        btnAddToBag = view.findViewById(R.id.btnAddToBag);
        btnSpinnerSize = view.findViewById(R.id.btnSpinner);


        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        btnAddToWhistList = view.findViewById(R.id.btnFavorite);
        btnOutOfStock = view.findViewById(R.id.btnOutOfStock);
        String email = sharedPreferences.getString("email",null).toString();
        int UserID = UserAccountHandler.getUserByEmail(email).getId();
        if(FavoriteProductHandler.CheckProductFavorite(UserID,mProduct.get(0).getProductID())){
            mProduct.get(0).setFavorite(true);
            btnAddToWhistList.setText("Favorited");
        }
        listSize = ProductSizeHandler.getDataByProductID(mProduct.get(0).getProductID());
        CurrentProduct = mProduct.get(0);

        btnSizeGuide = view.findViewById(R.id.btnSizeAndFit);
        tvSizeGuide = view.findViewById(R.id.tvSizeGuide);
        expandSizeGuide = view.findViewById(R.id.expand_SizeGuide);

        expandReviews = view.findViewById(R.id.expand_reviews);
        cardViewReviews = view.findViewById(R.id.cardViewReviews);
        btnWriteReviews = view.findViewById(R.id.btnWriteReview);
        btnMoreReviews = view.findViewById(R.id.btnMoreReviews);
        btnReviews = view.findViewById(R.id.btnReviews);
        ratingBarReviewsTitle = view.findViewById(R.id.ratingBarReviewsTitle);
        recyclerReviews = view.findViewById(R.id.recycleReviews);
        productReviews = new ArrayList<>();

    }
    private void setDataRecycleViewPhotoList(){
       
        photoReAdapter = new PhotoRecycleViewAdapter(mProduct,getContext(),this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        photoRecycleView.setLayoutManager(layoutManager);
        photoRecycleView.setAdapter(photoReAdapter);
    }
    private void setDataPhotoViewPager(Product product){
       photoList = ImageHandler.getPhotoByProductID(product.getProductID());
        adapter = new PhotoProductAdapter(getContext(),photoList);
        imagePager.setAdapter(adapter);

        circleIndicator.setViewPager(imagePager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }
    private void bindingDataOfProduct(Product product){
        setDataPhotoViewPager(product);
        tvNameProduct.setText(product.getName());
        tvPrice.setText("đ"+formatCurrency(product.getPrice()).replace(",", ".")+".000");
        tvDescription.setText(product.getDescription());
        tvObject.setText(product.getObjectName()+"'s "+product.getCategoryName());
        tvStyle.setText("Style: "+product.getStyleCode());
        tvShown.setText("Shown: "+product.getColorShown());
        listSize = ProductSizeHandler.getDataByProductID(product.getProductID());
        String email = sharedPreferences.getString("email",null).toString();
        int UserID = UserAccountHandler.getUserByEmail(email).getId();

        if(FavoriteProductHandler.CheckProductFavorite(UserID,product.getProductID())){
            product.setFavorite(true);
            btnAddToWhistList.setText("Favorited");
        }else {
            product.setFavorite(false);
            btnAddToWhistList.setText("Favorite");
        }
        if(totalQuantityProduct() == 0){
            btnOutOfStock.setVisibility(View.VISIBLE);
            btnSpinnerSize.setVisibility(View.GONE);
            btnAddToBag.setVisibility(View.GONE);
        }else {
            btnOutOfStock.setVisibility(View.GONE);
            btnSpinnerSize.setVisibility(View.VISIBLE);
            btnAddToBag.setVisibility(View.VISIBLE);
        }

        productReviews = ProductReviewHandler.getDataByProductID(product.getProductID());
        ArrayList<ProductReview> reviewsTmp = new ArrayList<>();
        for(int i = 0;i<productReviews.size();i++){
            if(i<2){
                ProductReview pr = productReviews.get(i);
                reviewsTmp.add(pr);
            }
        }
        reviewRecycleViewAdapter = new ReviewRecycleViewAdapter(reviewsTmp);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerReviews.setLayoutManager(layoutManager);
        recyclerReviews.setAdapter(reviewRecycleViewAdapter);

        float avgRating = (float) productReviews.stream().mapToDouble(ProductReview::getReviewRate).average().orElse(0.0);
        ratingBarReviewsTitle.setRating(avgRating);
        btnReviews.setText("Reviews ("+productReviews.size()+")");

        if(ProductReviewHandler.checkReviewerExist(UserID,product.getProductID())){
            btnWriteReviews.setVisibility(View.GONE);
        }
        if(productReviews.size()==0){
            btnMoreReviews.setVisibility(View.GONE);
        }

    }
    private int totalQuantityProduct(){
        return listSize.stream().mapToInt(ProductSize::getSoluong).sum();
    }
    private int totalQuantityBag(){
        bags = BagHandler.getBag(Util.getUserID(getContext()));
        return bags.stream().mapToInt(Bag::getQuantity).sum();
    }
    private void   CustomToast(View currentView, String msg){


        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast,currentView.findViewById(R.id.customToast));
        TextView msgTv = view.findViewById(R.id.msgToast);



        Toast toast = new Toast(currentView.getContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 120);
        msgTv.setText(msg);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);

       //ViewGroup.LayoutParams params = view.getLayoutParams();
        //if (params == null) {
          //  params = new ViewGroup.LayoutParams(
             //       ViewGroup.LayoutParams.MATCH_PARENT,
               //    ViewGroup.LayoutParams.WRAP_CONTENT
            //);
        //} else {
         //   params.width = ViewGroup.LayoutParams.MATCH_PARENT; // hoặc chiều rộng mong muốn khác
        //}
        //view.setLayoutParams(params);
        toast.show();


    }
    private void addToBag() {
        int user_id = Util.getUserID(getContext());
        int product_size_id = productSize.getProduct_size_id();
        if (BagHandler.isExists(user_id, product_size_id)) {
            BagHandler.increaseQuantity(user_id, product_size_id);
        } else {
            BagHandler.addToBag(user_id, product_size_id, 1);
        }
        showPopupAddToBag();
    }

    private void onExpandSizeGuideClick(){
        isExpandedSizeGuide = !isExpandedSizeGuide;
        if(isExpandedSizeGuide){
            tvSizeGuide.setVisibility(View.VISIBLE);

        }else{
            tvSizeGuide.setVisibility(View.GONE);
        }
        TransitionManager.beginDelayedTransition(expandSizeGuide);


    }
    private void updateRecycleReview(){
        productReviews = ProductReviewHandler.getDataByProductID(CurrentProduct.getProductID());
        ArrayList<ProductReview> reviewsTmp = new ArrayList<>();
        for(int i = 0;i<productReviews.size();i++){
            if(i<2){
                ProductReview pr = productReviews.get(i);
                reviewsTmp.add(pr);
            }
        }

        reviewRecycleViewAdapter.notifyDataSetChanged();
    }
    private void onExpandReviewsClick(){
        isExpandedReviews = !isExpandedReviews;
        if(isExpandedReviews){
            updateRecycleReview();
            if(ProductReviewHandler.checkReviewerExist(Util.getUserID(getContext()),CurrentProduct.getProductID())==false){
                btnWriteReviews.setVisibility(View.VISIBLE);
            }if(productReviews.size()>0){
                recyclerReviews.setVisibility(View.VISIBLE);
                btnMoreReviews.setVisibility(View.VISIBLE);
            }

        }else{
            btnWriteReviews.setVisibility(View.GONE);
            recyclerReviews.setVisibility(View.GONE);
            btnMoreReviews.setVisibility(View.GONE);
        }
        TransitionManager.beginDelayedTransition(expandReviews);
    }
    private void showPopupWriteReview(){
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_popup_write_review,null);
        //add Control
        TextView tvOverallRating = convertView.findViewById(R.id.titleOrverallRating);
        TextView tvYourReview = convertView.findViewById(R.id.tvYourReview);
        TextView tvRatingBarValidation = convertView.findViewById(R.id.tvErrorRatingBar);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        TextInputLayout layoutReviewTitle = convertView.findViewById(R.id.layoutReviewTitle);
        TextInputLayout layoutYourReview = convertView.findViewById(R.id.layoutYourReview);
        Button btnSubmit = convertView.findViewById(R.id.btnSubmit);
        TextView btnClose = convertView.findViewById(R.id.btnClose);
        String text = "Overall rating <font color='red'>*</font>";
        tvOverallRating.setText(Html.fromHtml(text));
        String text1 = "Your Review <font color='red'>*</font>";
        tvYourReview.setText(Html.fromHtml(text1));

        //addEvent

        layoutReviewTitle.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    layoutReviewTitle.setError("Summarise your review in 150 characters or less.");
                }
            }

            @SuppressLint("SuspiciousIndentation")
            @Override
            public void afterTextChanged(Editable s) {
                    if(s.length()>0)
                    layoutReviewTitle.setError(null);

            }
        });
        layoutYourReview.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<30){
                    layoutYourReview.setError("Describe what you liked, what you didn't like and other key things shoppers should know. Minimum 30 characters.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>=30)
                    layoutYourReview.setError(null);

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag= true;
                if(layoutReviewTitle.getEditText().getText().length()==0){
                    layoutReviewTitle.setError("Summarise your review in 150 characters or less.");
                    flag = false;

                }else{
                    layoutReviewTitle.setError(null);
                }
                if(layoutYourReview.getEditText().getText().length()<30){
                    flag = false;
                    layoutYourReview.setError("Describe what you liked, what you didn't like and other key things shoppers should know. Minimum 30 characters.");
                }else{
                    layoutYourReview.setError(null);
                }
                if(ratingBar.getRating() == 0){
                    flag = false;
                    tvRatingBarValidation.setVisibility(View.VISIBLE);
                }else{
                    tvRatingBarValidation.setVisibility(View.VISIBLE);
                }
                if(flag==false){
                    return;
                }else{
                    int productID = CurrentProduct.getProductID();
                    String title = layoutReviewTitle.getEditText().getText().toString();
                    String content = layoutYourReview.getEditText().getText().toString();
                    float rate = ratingBar.getRating();
                    ProductReviewHandler.submitReview(getUserID(getContext()),productID,title,content,rate);
                    Toast.makeText(getContext(),"Review Successful",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    updateRecycleReview();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //Popup Show
        dialog.setContentView(convertView);


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    private void addEvent(){
        btnWriteReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWriteReview();
            }
        });
        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpandReviewsClick();
            }
        });
        tvSizeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.nike.com/vn/size-fit/mens-footwear");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        btnSizeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpandSizeGuideClick();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.fragment.app.FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.popBackStack("TabLayoutOfShop", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack();
                btnBack.setVisibility(View.GONE);
                tvNameFragment.setText("Shop");
            }
        });
        btnSpinnerSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup(null);

            }
        });
        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (productSize == null) {
                    showPopup(new PopupCallback() {
                        @Override
                        public void onProductSizeSet(ProductSize selectedSize) {
                            productSize = selectedSize;
                            addToBag();

                        }
                    });
                } else {
                    addToBag();

                }

            }
        });
        btnAddToWhistList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sharedPreferences.getString("email",null).toString();
                int UserID = UserAccountHandler.getUserByEmail(email).getId();
                if(CurrentProduct != null && CurrentProduct.isFavorite() == false){
                    btnAddToWhistList.setText("Favorited");
                    CurrentProduct.setFavorite(true);
                    CustomToast(v,"Added to Favorites");
                    FavoriteProductHandler.insertFavoriteProduct(UserID,CurrentProduct.getProductID());
                } else if (CurrentProduct != null && CurrentProduct.isFavorite() == true){
                    btnAddToWhistList.setText("Favorite");
                    CurrentProduct.setFavorite(false);
                    CustomToast(v,"Removed from Favorites");
                    FavoriteProductHandler.removeFavoriteProduct(UserID,CurrentProduct.getProductID());
                }
                if (CurrentProduct == null && mProduct.get(0).isFavorite() == false){
                    btnAddToWhistList.setText("Favorited");
                    mProduct.get(0).setFavorite(true);
                    CustomToast(v,"Added to Favorites");
                    FavoriteProductHandler.insertFavoriteProduct(UserID,mProduct.get(0).getProductID());
                } else if(CurrentProduct == null && mProduct.get(0).isFavorite() == true){
                    btnAddToWhistList.setText("Favorite");
                    mProduct.get(0).setFavorite(false);
                    CustomToast(v,"Removed from Favorites");
                    FavoriteProductHandler.removeFavoriteProduct(UserID,mProduct.get(0).getProductID());
                }

            }
        });

        btnMoreReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int product_id = CurrentProduct.getProductID();
                SeeMoreReviewFragment seeMoreReview = SeeMoreReviewFragment.newInstance(product_id);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtils.addFragment(fm,seeMoreReview,R.id.frameLayout);
            }
        });

    }
    protected void addControlOfPopupMenu(View view){
        //Layout
        container = getActivity().findViewById(R.id.frameLayout);
                //Component of PopupMenu
        listViewSize = view.findViewById(R.id.listViewSize);
        btnClose = view.findViewById(R.id.btnExit);
        //Set Data ListView of Size

        sizeAdapter = new SizeItemAdapter(getContext(),R.layout.row_item_size,listSize);
        listViewSize.setAdapter(sizeAdapter);
    }
    protected void addEventOfPopupMenu(View view,final PopupCallback callback){

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        listViewSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < listSize.size(); i++) {
                    ProductSize size = listSize.get(i);
                    if (size.isSelect()) {
                        // Nếu có, hủy chọn (xóa dấu check) cho size đó
                        size.setSelect(false);
                        // Cập nhật lại giao diện của ListView
                        View itemView = listViewSize.getChildAt(i - listViewSize.getFirstVisiblePosition());
                        if (itemView != null) {
                            ImageView checkedImageView = itemView.findViewById(R.id.itemCheck);
                            checkedImageView.setVisibility(View.GONE);
                        }
                        break;
                    }
                }
                productSize = listSize.get(position);
                productSize.setSelect(true);
                sizeAdapter.notifyDataSetChanged();
                btnSpinnerSize.setText("Size "+productSize.getSize().getName());
                dialog.dismiss();

                if (callback != null) {
                    callback.onProductSizeSet(productSize);
                }
            }
        });
    }

    private void showPopupAddToBag(){

        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_to_bag_successfully_custom_popup,null);
        LottieAnimationView addToBagAnimation = convertView.findViewById(R.id.lottieAddToBag);
        TextView tvAddToBag = convertView.findViewById(R.id.tvAddToBag);

        tvAddToBag.setText("Added To Bag\n( "+ totalQuantityBag()+" items )");
        addToBagAnimation.animate().setDuration(3000).setStartDelay(0);
        tvAddToBag.animate().setDuration(3000).setDuration(0);

        dialog.setContentView(convertView);


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }
    public interface PopupCallback {
        void onProductSizeSet(ProductSize selectedSize);
    }
    protected void showPopup(final PopupCallback callback){
        updateProductSizeList();
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.popup_menu_size,null);
        addControlOfPopupMenu(convertView);
        addEventOfPopupMenu(convertView,callback);
        dialog.setContentView(convertView);


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }
    private void updateProductSizeList() {
        listSize = ProductSizeHandler.getDataByProductID(CurrentProduct.getProductID());


        boolean foundSelected = false;
        for (ProductSize ps : listSize) {
            if (productSize != null && ps.getProduct_size_id() == productSize.getProduct_size_id()) {
                ps.setSelect(true);
                foundSelected = true;
            } else {
                ps.setSelect(false);
            }
        }

        if (!foundSelected) {
            productSize = null;
        }

        if (sizeAdapter != null) {
            sizeAdapter.notifyDataSetChanged();
        }
    }
    private void bindingDataOfCurrentProduct(Product product){
        bindingDataOfProduct(product);
        CurrentProduct = product;
    }
    private void setDefaultVALUEOfCurrentProduct(Product cur){
        bindingDataOfCurrentProduct(cur);
    }
    @Override
    public void onPhotoClick(Product product) {

      bindingDataOfCurrentProduct(product);

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
    }

}