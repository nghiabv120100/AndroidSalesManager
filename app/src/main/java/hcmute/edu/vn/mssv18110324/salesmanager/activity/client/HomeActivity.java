package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.InsertData;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;

public class HomeActivity extends AppCompatActivity implements CategoryAdapter.ItemClicked {
    ImageButton btnToggle,btnHome;


    CategoryDatabaseHandler dbCategory;
    ProductDatabaseHandler dbProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControl();
        addEvent();

        dbCategory = new CategoryDatabaseHandler(this);
        dbProduct = new ProductDatabaseHandler(this);

        Bitmap bitmapCategory = ((BitmapDrawable)getResources().getDrawable(R.drawable.cream)).getBitmap();
        Bitmap bitmapProduct = BitmapFactory.decodeResource(getResources(), R.drawable.poster);
        addCategory(bitmapCategory);

        addProduct(bitmapProduct);
//        ArrayList<Product> lstPro= dbProduct.findByCategoryID(1);
        Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();


        showFragmentProduct();
    }

    private void addControl() {
        btnToggle = findViewById(R.id.btnToggle);
        btnHome = findViewById(R.id.btnHome);
    }

    private void addEvent() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentHome();
            }
        });

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentListCategory();
            }
        });
    }

    public void showFragmentHome() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .show(manager.findFragmentById(R.id.fragHome))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentListCategory() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragHome))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .show(manager.findFragmentById(R.id.fragListCategory))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentProduct() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragHome))
                .show(manager.findFragmentById(R.id.fragListProduct))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void OnItemClicked(int index) {
        showFragmentProduct();
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
        product.set_category_id(4);
        product.set_image(bitmap);
        product.set_describe("Nothing");
        product.set_name("ABCCC");
        product.set_price(20000);
        product.set_quantity(22);
        dbProduct.addProduct(product);


    }
}