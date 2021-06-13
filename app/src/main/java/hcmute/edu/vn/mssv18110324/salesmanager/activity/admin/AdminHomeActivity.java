package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.Login;

public class AdminHomeActivity extends AppCompatActivity {

    Button btnManagerProduct,btnManagerCategory,btnManagerOrders,btnManagerUsers,btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnManagerProduct = findViewById(R.id.btnManagerProduct);
        btnManagerCategory = findViewById(R.id.btnManagerCategory);
        btnManagerOrders = findViewById(R.id.btnManagerOrders);
        btnManagerUsers = findViewById(R.id.btnManagerUsers);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void addEvents() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(Login.MY_PREFS_FILENAME,MODE_PRIVATE).edit();
                editor.clear().commit();
                Intent i = new Intent(AdminHomeActivity.this, Login.class);
                startActivity(i);
            }
        });
        btnManagerCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminHomeActivity.this, AdminCategoryActivity.class);
                startActivity(i);
            }
        });

        btnManagerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminHomeActivity.this, AdminProductActivity.class);
                startActivity(i);
            }
        });
    }


}