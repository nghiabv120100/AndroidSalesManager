package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.DetailOderAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.HistoryPurchaseAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CartDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CartItemDatabaseHandler;

public class DetailOrder extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<CartItem> lstCartItem;
    TextView txtTotalPrice,txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        txtTitle = findViewById(R.id.txtTitle);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id",-1);

        CartItemDatabaseHandler db = new CartItemDatabaseHandler(this);

        lstCartItem = db.getByCartID(id);
        recyclerView =  findViewById(R.id.recyclerDetailOrder);
        recyclerView.setHasFixedSize(true);
//        manager = new GridLayoutManager(activity,2,GridLayoutManager.HORIZONTAL,false);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        myAdapter = new DetailOderAdapter(lstCartItem);
        recyclerView.setAdapter(myAdapter);

        txtTitle.setText("Chi tiết đơn hàng #"+id);

        int totalPrice = 0;
        for (CartItem item:lstCartItem) {
            totalPrice += item.get_unit_price() * item.get_quantity();
        }
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String sTotalPrice = formatter.format(totalPrice);


        txtTotalPrice.setText(sTotalPrice+"đ");

    }

}