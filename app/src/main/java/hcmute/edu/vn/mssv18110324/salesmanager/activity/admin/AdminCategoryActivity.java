package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;

public class AdminCategoryActivity extends AppCompatActivity implements CategoryAdapter.ItemClicked {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<Category> lstCategory;
    FloatingActionButton btnAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CategoryDatabaseHandler db = new CategoryDatabaseHandler(this);
        recyclerView =  findViewById(R.id.recyclerCategory);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lstCategory = new ArrayList<Category>();
        lstCategory = db.findAllCategory();
        myAdapter = new CategoryAdapter(this,lstCategory);
        recyclerView.setAdapter(myAdapter);
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