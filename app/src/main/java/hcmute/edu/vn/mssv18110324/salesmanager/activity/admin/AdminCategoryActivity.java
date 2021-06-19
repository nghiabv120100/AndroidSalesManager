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

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.client.HistoryPurchase;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AdminCategoryActivity extends AppCompatActivity implements CategoryAdapter.ItemClicked {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<Category> lstCategory;
    FloatingActionButton btnAddCategory;
    CategoryDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addControls();
        addEvents();


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
                    String sDeletedCategory ="Đã xoá danh mục: "+ lstCategory.get(position).get_name();
                    Category deletedCategory = lstCategory.get(position);
                    deletedCategory.set_status(0);
                    db.updateCategory(deletedCategory);
                    lstCategory.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, sDeletedCategory,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deletedCategory.set_status(1);
                                    db.updateCategory(deletedCategory);
                                    lstCategory.add(position,deletedCategory);
                                    myAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(AdminCategoryActivity.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.rgb(255,152,0))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        db = new CategoryDatabaseHandler(this);
        recyclerView =  findViewById(R.id.recyclerCategory);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lstCategory = new ArrayList<Category>();
        lstCategory = db.findAllCategory();
        myAdapter = new CategoryAdapter(this,lstCategory);
        recyclerView.setAdapter(myAdapter);
        
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void addControls() {
        btnAddCategory = findViewById(R.id.btnAddCategory);
    }

    private void addEvents() {
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminCategoryActionActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void OnItemClicked(int id) {
        Intent intent = new Intent(AdminCategoryActivity.this, AdminCategoryActionActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}