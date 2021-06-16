package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE ="Create Table "+TABLE_CART+"("+KEY_ID+" Integer Primary Key,"
                +KEY_TOTAL_PRICE+" Integer,"+KEY_CUSTOMER_ID+" Integer,"+KEY_STATUS+" Integer,"+KEY_BUY_DATE+" Text)";

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            ContentValues cv = new ContentValues();
//            cv.put(KEY_ID,cart.get_id());
            cv.put(KEY_TOTAL_PRICE,cart.get_total_price());
            cv.put(KEY_CUSTOMER_ID,cart.get_customer_id());
            cv.put(KEY_STATUS,cart.get_status());
            cv.put(KEY_BUY_DATE,dateFormat.format(cart.get_buy_date()));
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

    public int updateCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // convert bitmap to byte
            ContentValues cv = new ContentValues();
            cv.put(KEY_TOTAL_PRICE,cart.get_total_price());
            cv.put(KEY_CUSTOMER_ID,cart.get_customer_id());
            cv.put(KEY_STATUS,cart.get_status());
            cv.put(KEY_BUY_DATE,dateFormat.format(cart.get_buy_date()));
            db.update(TABLE_CART, cv, "_id = ?", new String[]{cart.get_id()+""});
            db.close();
            return 1;

        } catch (Exception ex) {
            db.close();
            return -1;
        }
    }

    public ArrayList<Cart> getCartByCustomerID(Integer id) {
        ArrayList<Cart> lstCart = new ArrayList<Cart>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "Select * From "+TABLE_CART + " Where "+KEY_CUSTOMER_ID +" = "+id +" and "+ KEY_STATUS+" !=0" ;
            Cursor cursor = db.rawQuery(query,null);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int iID = cursor.getColumnIndex(KEY_ID);
            int iTotalPrice = cursor.getColumnIndex(KEY_TOTAL_PRICE);
            int iCustomerID = cursor.getColumnIndex(KEY_CUSTOMER_ID);
            int iStatus = cursor.getColumnIndex(KEY_STATUS);
            int iBuyDate = cursor.getColumnIndex(KEY_BUY_DATE);

            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                Cart cart = new Cart();
                cart.set_id(cursor.getInt(iID));
                cart.set_buy_date(dateFormat.parse(cursor.getString(iBuyDate)));
                cart.set_total_price(cursor.getInt(iTotalPrice));
                cart.set_customer_id(cursor.getInt(iCustomerID));
                cart.set_status(cursor.getInt(iStatus));
                lstCart.add(cart);
            }
        } catch (Exception e) {

        }
        return lstCart;
    }

    public ArrayList<Cart> getAll() {
        ArrayList<Cart> lstCart = new ArrayList<Cart>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "Select * From "+TABLE_CART + " Where "+ KEY_STATUS+" !=0" ;
            Cursor cursor = db.rawQuery(query,null);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int iID = cursor.getColumnIndex(KEY_ID);
            int iTotalPrice = cursor.getColumnIndex(KEY_TOTAL_PRICE);
            int iCustomerID = cursor.getColumnIndex(KEY_CUSTOMER_ID);
            int iStatus = cursor.getColumnIndex(KEY_STATUS);
            int iBuyDate = cursor.getColumnIndex(KEY_BUY_DATE);

            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                Cart cart = new Cart();
                cart.set_id(cursor.getInt(iID));
                cart.set_buy_date(dateFormat.parse(cursor.getString(iBuyDate)));
                cart.set_total_price(cursor.getInt(iTotalPrice));
                cart.set_customer_id(cursor.getInt(iCustomerID));
                cart.set_status(cursor.getInt(iStatus));
                lstCart.add(cart);
            }
        } catch (Exception e) {

        }
        return lstCart;
    }
}
