package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.InsertData;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.viewmodels.ShopViewModel;

public class HomeActivity extends AppCompatActivity implements CategoryAdapter.ItemClicked, ProductAdapter.ProductClicked {
    ImageButton btnToggle,btnHome,btnPersonalInfo;
    LinearLayout linearToggle,linearHome,linearPersonal;

    //Widgets of Product Detail Fragment
    ImageView imgProductDetail;
    TextView txtNameProductDetail, txtStatus, txtDescription;
    Button btnAddToCart;
    //
    // Widgets of List Product Fragment
    RecyclerView.Adapter myAdapter;
    RecyclerView recyclerView;
    //

    ShopViewModel shopViewModel = new ShopViewModel();
    CategoryDatabaseHandler dbCategory =  new CategoryDatabaseHandler(this);
    ProductDatabaseHandler dbProduct  = new ProductDatabaseHandler(this);
    NavController navController = new NavController(getSupportFragmentManager());

    private int cartQuantity = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControl();
        addEvent();

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        shopViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int quantity=0;
                for (CartItem cartItem:cartItems) {
                    quantity+=cartItem.get_quantity();
                }
                cartQuantity = quantity;
                invalidateOptionsMenu();
            }
        });



      /*  Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.cafe_milk);
        addCategory(icon);
        addProduct(icon);
        */
        navController.showFragmentProduct();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume","Back Click");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause","Back Click");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        final MenuItem menuItem = menu.findItem(R.id.cart);
        View actionView = menuItem.getActionView();
        TextView cartBadgeTextView = actionView.findViewById(R.id.cart_badge_text_view);
        cartBadgeTextView.setText(String.valueOf(cartQuantity));
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController.showFragmentCart();
        return true;
    }

    private void addControl() {
        //Widget of Navbar
        btnToggle = findViewById(R.id.btnToggle);
        btnHome = findViewById(R.id.btnHome);
        btnPersonalInfo = findViewById(R.id.btnPersonalInfo);
        linearToggle = findViewById(R.id.linearToggle);
        linearHome = findViewById(R.id.linearHome);
        linearPersonal = findViewById(R.id.linearPersonal);
        //
        //Widgets of Fragment Product Detail
        imgProductDetail = findViewById(R.id.imgProductDetail);
        txtNameProductDetail = findViewById(R.id.txtNameProductDetail);
        txtStatus = findViewById(R.id.txtStatus);
        txtDescription = findViewById(R.id.txtDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        //
        //Widgets of Fragment List Product
        recyclerView = findViewById(R.id.lstProduct);
        //
    }

    private void addEvent() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentHome();
                linearHome.setBackgroundColor(Color.rgb(255,224,178));
                linearPersonal.setBackgroundColor(Color.WHITE);
                linearToggle.setBackgroundColor(Color.WHITE);
            }
        });

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentListCategory();
                linearHome.setBackgroundColor(Color.WHITE);
                linearPersonal.setBackgroundColor(Color.WHITE);
                linearToggle.setBackgroundColor(Color.rgb(255,224,178));
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentPersonalInfo();
                linearHome.setBackgroundColor(Color.WHITE);
                linearPersonal.setBackgroundColor(Color.rgb(255,224,178));
                linearToggle.setBackgroundColor(Color.WHITE);
            }
        });

        linearToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentListCategory();
                linearHome.setBackgroundColor(Color.WHITE);
                linearPersonal.setBackgroundColor(Color.WHITE);
                linearToggle.setBackgroundColor(Color.rgb(255,224,178));
            }
        });
        linearHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentHome();
                linearHome.setBackgroundColor(Color.rgb(255,224,178));
                linearPersonal.setBackgroundColor(Color.WHITE);
                linearToggle.setBackgroundColor(Color.WHITE);
            }
        });
        linearPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentPersonalInfo();
                linearHome.setBackgroundColor(Color.WHITE);
                linearPersonal.setBackgroundColor(Color.rgb(255,224,178));
                linearToggle.setBackgroundColor(Color.WHITE);
            }
        });

    }


    @Override
    public void OnItemClicked(int id) {
        ArrayList<Product>  arrayList= new ArrayList<Product>();
        arrayList = dbProduct.findByCategoryID(id);

        if (arrayList==null || arrayList.isEmpty()) {
            Toast.makeText(this,"Loại hàng này hiện không còn sản phẩm",Toast.LENGTH_SHORT).show();
            return;
        } else {
            myAdapter = new ProductAdapter(this,arrayList);
            recyclerView.setAdapter(myAdapter);
            navController.showFragmentProduct();
        }
    }
    @Override
    public void OnProductClicked(int index) {
        Product product= ProductAdapter.lstProduct.get(index);
        imgProductDetail.setImageBitmap(product.get_image());
        txtNameProductDetail.setText(product.get_name());
//        txtStatus.setText();
        txtDescription.setText(product.get_describe());

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdded = shopViewModel.addItemToCart(product);
                if (isAdded) {
                    Snackbar.make(findViewById(android.R.id.content).getRootView(),product.get_name()+" đã được thêm vào giỏ hàng",Snackbar.LENGTH_LONG)
                            .setAction("Checkout", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    navController.showFragmentCart();
                                }
                            }).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content).getRootView(),"Sản phẩm đã đạt giới hạn trong giỏ hàng",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        navController.showFragmentDetailProduct();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("Back:","clicked_back");
            // your code
            return true;
        }
        Log.d("Back:","No");
        return super.onKeyDown(keyCode, event);
    }

    ///Insert data
    public void addCategory(Bitmap bitmap) {
//        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ancol)).getBitmap();

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ancol);
        Category category = new Category();
        category.set_id(4);
        category.set_name("Đồ ăn");
        category.set_status(1);
        category.set_image(bitmap);
        dbCategory.addCategory(category);
    }

    public void addProduct(Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.froster_unicorn);
        Product product = new Product();
        product.set_id(3);
        product.set_status(1);
        product.set_category_id(5);
        product.set_image(bitmap);
        product.set_describe("Nothing");
        product.set_name("ABCCC");
        product.set_price(20000);
        product.set_quantity(22);
        dbProduct.addProduct(product);
    }


}