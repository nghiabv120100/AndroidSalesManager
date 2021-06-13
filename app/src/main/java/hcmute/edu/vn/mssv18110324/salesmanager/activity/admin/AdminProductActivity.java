package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;

public class AdminProductActivity extends AppCompatActivity implements ProductAdapter.ProductClicked {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<Product> lstProduct;
    FloatingActionButton btnAddProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);
        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ProductDatabaseHandler db = new ProductDatabaseHandler(this);
        recyclerView =  findViewById(R.id.recyclerProduct);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lstProduct = new ArrayList<Product>();
        lstProduct = db.findAllProduct();
        myAdapter = new ProductAdapter(this,lstProduct);
        recyclerView.setAdapter(myAdapter);
    }

    private void addControls() {
        btnAddProduct = findViewById(R.id.btnAddProduct);
    }

    private void addEvents() {
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProductActivity.this, AdminProductActionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnProductClicked(int index) {

    }
}