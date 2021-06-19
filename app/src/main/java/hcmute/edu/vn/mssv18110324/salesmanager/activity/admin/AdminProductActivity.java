package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdminAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AdminProductActivity extends AppCompatActivity implements ProductAdminAdapter.ProductClicked {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<Product> lstProduct;
    FloatingActionButton btnAddProduct;
    ProductDatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = new ProductDatabaseHandler(this);
        recyclerView =  findViewById(R.id.recyclerProduct);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lstProduct = new ArrayList<Product>();
        lstProduct = db.findAllProduct();
        myAdapter = new ProductAdminAdapter(this,lstProduct);
        recyclerView.setAdapter(myAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    String sDeletedProduct ="Đã xoá "+ lstProduct.get(position).get_name();
                    Product deletedProduct = lstProduct.get(position);
                    deletedProduct.set_status(0);
                    db.update(deletedProduct);
                    lstProduct.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, sDeletedProduct,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deletedProduct.set_status(1);
                                    db.update(deletedProduct);
                                    lstProduct.add(position,deletedProduct);
                                    myAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(AdminProductActivity.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.rgb(255,152,0))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    public void OnProductClicked(Product product) {
        Intent intent = new Intent(AdminProductActivity.this,AdminProductActionActivity.class);
        intent.putExtra("id",product.get_id());
        startActivity(intent);
    }
}