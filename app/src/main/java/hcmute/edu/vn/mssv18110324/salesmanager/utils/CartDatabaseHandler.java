package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.repositories.CartRepo;

public class CartDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "salesManager";
    private static final String TABLE_CART = "cart";
    private static final String KEY_ID ="_id";
    private static final String KEY_TOTAL_PRICE="_totalPrice";
    private static final String KEY_CUSTOMER_ID = "_customerId";
    private static final String KEY_STATUS = "_status";
    private static final String KEY_BUY_DATE ="_buyDate";

    private Context context;

    public CartDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
//        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE ="Create Table "+TABLE_CART+"("+KEY_ID+" Integer Primary Key,"
                +KEY_TOTAL_PRICE+" Integer,"+KEY_CUSTOMER_ID+" Integer,"+KEY_STATUS+" Integer,"+KEY_BUY_DATE+" Date)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists "+TABLE_CART);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists "+TABLE_CART);
        onCreate(db);
    }

    public int addCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        CartItemDatabaseHandler cartItemDatabaseHandler = new CartItemDatabaseHandler(context);
        try {
            ContentValues cv = new ContentValues();
//            cv.put(KEY_ID,cart.get_id());
            cv.put(KEY_TOTAL_PRICE,cart.get_total_price());
            cv.put(KEY_CUSTOMER_ID,cart.get_customer_id());
            cv.put(KEY_STATUS,cart.get_status());
            long id = db.insert(TABLE_CART,null,cv);
            //add Cart item
            List<CartItem> lstCartItem = cart.get_lst_cart_item();
            for (CartItem item:lstCartItem) {
                item.set_cart_id((int)id);
                cartItemDatabaseHandler.addCartItem(item);
            }
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
