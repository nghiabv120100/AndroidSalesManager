package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;

public class HomeActivity extends AppCompatActivity {
    ImageButton btnToggle,btnHome;


    CategoryDatabaseHandler db = new CategoryDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControl();
        addEvent();
        Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();
        showFragmentHome();
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
                .show(manager.findFragmentById(R.id.fragHome))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentListCategory() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragHome))
                .show(manager.findFragmentById(R.id.fragListCategory))
                .addToBackStack(null)
                .commit();
    }

    private void addCategory() {
        Category category = new Category();
        category.set_id(1);
        category.set_name("Đồ uống");
        category.set_status(1);
        category.set_image("image_cate");
        db.addCategory(category);
    }
}