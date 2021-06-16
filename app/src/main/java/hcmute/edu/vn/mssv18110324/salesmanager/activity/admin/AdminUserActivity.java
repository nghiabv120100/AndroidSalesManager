package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.UserAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;

public class AdminUserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<User> lstUser;
    FloatingActionButton btnAddUser;

    UserDatabaseHandler dbUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user2);

        dbUser = new UserDatabaseHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView =  findViewById(R.id.recyclerUser);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lstUser = new ArrayList<User>();
        lstUser = dbUser.getAllUser();
        myAdapter = new UserAdapter(this,lstUser);
        recyclerView.setAdapter(myAdapter);
    }
}