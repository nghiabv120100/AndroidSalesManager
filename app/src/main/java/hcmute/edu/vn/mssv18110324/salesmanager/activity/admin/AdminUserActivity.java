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
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.UserAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AdminUserActivity extends AppCompatActivity implements UserAdapter.UserClicked {

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
        addControls();
        addEvents();
    }

    private void addControls() {
        btnAddUser = findViewById(R.id.btnAddUser);
    }

    private void addEvents() {
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminUserActivity.this,AdminUserActionActivity.class);
                startActivity(intent);
            }
        });
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
                    String sDeletedOrder ="Đã xoá người dùng"+ lstUser.get(position).get_full_name();
                    User deletedUser = lstUser.get(position);
                    deletedUser.set_status(0);
                    dbUser.update(deletedUser);
                    lstUser.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, sDeletedOrder,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deletedUser.set_status(1);
                                    dbUser.update(deletedUser);
                                    lstUser.add(position,deletedUser);
                                    myAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(AdminUserActivity.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.rgb(255,152,0))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(AdminUserActivity.this,AdminUserActionActivity.class);
        intent.putExtra("id",user.get_id());
        startActivity(intent);
    }
}