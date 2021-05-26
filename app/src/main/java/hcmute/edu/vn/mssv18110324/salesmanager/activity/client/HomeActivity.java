package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


    CategoryDatabaseHandler dbCategory =  new CategoryDatabaseHandler(this);
    ProductDatabaseHandler dbProduct  = new ProductDatabaseHandler(this);
    NavController navController = new NavController(getSupportFragmentManager());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControl();
        addEvent();

        navController.showFragmentProduct();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                navController.showFragmentProduct();
                return false;
            }
        });
        return true;
//        return super.onOptionsItemSelected(item);
    }

    private void addControl() {
        btnToggle = findViewById(R.id.btnToggle);
        btnHome = findViewById(R.id.btnHome);
    }

    private void addEvent() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentHome();
            }
        });

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentListCategory();
            }
        });
    }


    @Override
    public void OnItemClicked(int index) {
        navController.showFragmentProduct();
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