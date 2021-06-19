package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

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
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class HistoryPurchase extends AppCompatActivity  implements HistoryPurchaseAdapter.IOrderClicked {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<Cart> lstCart;

    CartDatabaseHandler db;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_purchase);
        SharedPreferences pref = getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE);
        id = pref.getInt("userId",-1);
        db = new CartDatabaseHandler(this);
        recyclerView =  findViewById(R.id.recyclerHistoryPurchase);
        recyclerView.setHasFixedSize(true);
//        manager = new GridLayoutManager(activity,2,GridLayoutManager.HORIZONTAL,false);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lstCart = new ArrayList<Cart>();
        lstCart = db.getCartByCustomerID(id);
        myAdapter = new HistoryPurchaseAdapter(this,lstCart);
        recyclerView.setAdapter(myAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
                    String sDeletedOrder ="Đã xoá lịch sử đơn hàng #"+ lstCart.get(position).get_id();
                    Cart deletedOrder = lstCart.get(position);
                    deletedOrder.set_status(0);
                    db.updateCart(deletedOrder);
                    lstCart.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, sDeletedOrder,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deletedOrder.set_status(1);
                                    db.updateCart(deletedOrder);
                                    lstCart.add(position,deletedOrder);
                                    myAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(HistoryPurchase.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.rgb(255,152,0))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };





    @Override
    public void onOrderClicked(Integer id) {
        Intent intent = new Intent(this,DetailOrder.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}