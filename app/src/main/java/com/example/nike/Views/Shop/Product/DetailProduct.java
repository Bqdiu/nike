package com.example.nike.Views.Shop.Product;

import static com.example.nike.Views.Util.formatCurrency;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nike.Controller.ImageHandler;
import com.example.nike.Controller.ProductSizeHandler;
import com.example.nike.Controller.UserAccountHandler;
import com.example.nike.MainActivity;
import com.example.nike.Model.Product;
import com.example.nike.Model.ProductImage;
import com.example.nike.Model.ProductSize;
import com.example.nike.Model.ShopByIcons;
import com.example.nike.Model.UserAccount;
import com.example.nike.R;
import com.example.nike.Views.Shop.Adapter.IconsItemRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.PhotoProductAdapter;
import com.example.nike.Views.Shop.Adapter.PhotoRecycleViewAdapter;
import com.example.nike.Views.Shop.Adapter.SizeItemAdapter;

import org.w3c.dom.Text;

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
    // control for favorite
    private Button btnFavorite;
    private SharedPreferences sharedPreferences;
    private String user_email;

    private UserAccount user;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "object_product";


    // TODO: Rename and change types of parameters
    private ArrayList<Product> mProduct;
    private int objectID;
    private int categoryID;
    private int selectedItem = -1;
    public DetailProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @param objectID Parameter 2.
     * @param categoryID Parameter 3.
     * @return A new instance of fragment DetailProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProduct newInstance(int categoryID,int objectID,ArrayList<Product> list) {
        DetailProduct fragment = new DetailProduct();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, list);
        args.putSerializable("ObjectID",objectID);
        args.putSerializable("CategoryID",categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (ArrayList<Product>) getArguments().getSerializable(ARG_PARAM1);
            objectID = getArguments().getInt("ObjectID");
            categoryID = getArguments().getInt("CategoryID");
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
            setDataRecycleViewPhotoList();
            dialog = new Dialog(getContext());

        }


        return view;
    }
    private void addControl(View view,Activity currentActivity){
        // Nếu Activity là loại Activity bạn mong muốn, bạn có thể tìm kiếm ImageButton từ đó
        btnBack = currentActivity.findViewById(R.id.btnBack);
        // Kiểm tra xem ImageButton có null hay không trước khi thực hiện thay đổi
        if (btnBack != null) {
            btnBack.setVisibility(View.VISIBLE);

        }
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

        btnSpinnerSize = view.findViewById(R.id.btnSpinner);
        listSize = ProductSizeHandler.getDataByProductID(mProduct.get(0).getProductID());

        // favorites control
        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        user_email = sharedPreferences.getString("email",null);
        user = UserAccountHandler.getUserByEmail(user_email);
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
    private String getFullObjectName(int categoryID,int objectID){
        String categoryName = "";
        if(categoryID == 1){
            categoryName = "Clothing";
        }
        else {
            categoryName = "Shoes";
        }
        String objectName = "";
        if(objectID == 1){
            objectName = "Men";
        }
        else if(objectID == 2){
            objectName = "Women";
        }
        else {
            objectName = "Kids";
        }
        return objectName +"'s "+categoryName;
    }
    private void bindingDataOfProduct(Product product){
        setDataPhotoViewPager(product);
        tvNameProduct.setText(product.getName());
        tvPrice.setText("đ"+formatCurrency(product.getPrice()).replace(",", ".")+".000");
        tvDescription.setText(product.getDescription());
        tvObject.setText(getFullObjectName(categoryID,objectID));
        tvStyle.setText("Style: "+product.getStyleCode());
        tvShown.setText("Shown: "+product.getColorShown());
        listSize = ProductSizeHandler.getDataByProductID(product.getProductID());

    }
    private void addEvent(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.fragment.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("TabLayoutOfShop", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                btnBack.setVisibility(View.GONE);
                tvNameFragment.setText("Shop");
            }
        });
        btnSpinnerSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup();

            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    protected void addEventOfPopupMenu(View view){

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

            }
        });
    }
    protected void showPopup(){

        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.popup_menu_size,null);
        addControlOfPopupMenu(convertView);
        addEventOfPopupMenu(convertView);
        dialog.setContentView(convertView);
        //dialog.setCancelable(true); // Cho phép đóng Dialog khi chạm ra ngoài


        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(true); // Cho phép đóng Dialog khi chạm ra ngoài
        dialog.show();
    }
    @Override
    public void onPhotoClick(Product product) {

        bindingDataOfProduct(product);
    }
}