package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;

public class HomeActivity extends AppCompatActivity implements CategoryAdapter.ItemClicked {
    ImageButton btnToggle,btnHome;


    CategoryDatabaseHandler db = new CategoryDatabaseHandler(this);
    ProductDatabaseHandler dbProduct = new ProductDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControl();
        addEvent();
        ArrayList<Product> lstPro= dbProduct.findByCategoryID(1);
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
}