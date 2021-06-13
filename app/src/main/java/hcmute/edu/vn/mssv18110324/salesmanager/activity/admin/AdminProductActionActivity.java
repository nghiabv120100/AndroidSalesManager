package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategorySpinnerAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;

public class AdminProductActionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    ArrayList<Category> lstCategory;
    Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_action);

        addControls();

    }


    private void addControls() {
        CategoryDatabaseHandler dbCategory = new CategoryDatabaseHandler(this);
        lstCategory= dbCategory.findAllCategory();

        spinnerCategory = findViewById(R.id.spinnerCategory);
        CategorySpinnerAdapter spinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(),lstCategory);
//        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(spinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminProductActionActivity.this,"itemm",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}