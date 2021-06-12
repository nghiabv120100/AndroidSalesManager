package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.Login;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.HistoryPurchaseAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CartDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CartItemDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;

public class HistoryPurchase extends AppCompatActivity implements HistoryPurchaseAdapter.IOrderClicked {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    View view;
    ArrayList<Cart> lstCart;

    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_purchase);
        SharedPreferences pref = getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE);
        id = pref.getInt("userId",-1);
        CartDatabaseHandler db = new CartDatabaseHandler(this);
        recyclerView =  findViewById(R.id.recyclerHistoryPurchase);
        recyclerView.setHasFixedSize(true);
//        manager = new GridLayoutManager(activity,2,GridLayoutManager.HORIZONTAL,false);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lstCart = new ArrayList<Cart>();
        lstCart = db.getCartByCustomerID(id);
        myAdapter = new HistoryPurchaseAdapter(this,lstCart);
        recyclerView.setAdapter(myAdapter);
    }


    @Override
    public void onOrderClicked(Integer id) {
        CartItemDatabaseHandler db = new CartItemDatabaseHandler(this);
        ArrayList<CartItem> lstCartItem = db.getByCartID(id);
    }
}