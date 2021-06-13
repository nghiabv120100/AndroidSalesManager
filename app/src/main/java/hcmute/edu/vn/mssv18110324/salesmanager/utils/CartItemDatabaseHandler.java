package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;

public class CartItemDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "salesManager";
    private static final String TABLE_CART_ITEM = "cart_item";
    private static final String KEY_PRODUCT_ID ="_product_id";
    private static final String KEY_CART_ID="_cart_id";
    private static final String KEY_QUANTITY = "_quantity";
    private static final String KEY_UNIT_PRICE ="_unit_price";
    private static final String KEY_STATUS = "_status";

    ProductDatabaseHandler dbProduct;

    public CartItemDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbProduct = new ProductDatabaseHandler(context);
//        onCreate(this.getWritableDatabase());

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE ="Create Table "+TABLE_CART_ITEM+"("+KEY_PRODUCT_ID+
                " Integer ,"+KEY_CART_ID+" Integer ,"+KEY_QUANTITY+" Integer,"
                +KEY_UNIT_PRICE+" Integer ,"+KEY_STATUS+" Integer,"+ "Primary Key("+KEY_PRODUCT_ID+","+KEY_CART_ID+"))";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists "+TABLE_CART_ITEM);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists "+TABLE_CART_ITEM);
        onCreate(db);
    }

    public int addCartItem(CartItem cartItem) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KEY_PRODUCT_ID,cartItem.get_product().get_id());
            cv.put(KEY_CART_ID,cartItem.get_cart_id());
            cv.put(KEY_CART_ID,cartItem.get_cart_id());
            cv.put(KEY_QUANTITY,cartItem.get_quantity());
            cv.put(KEY_UNIT_PRICE,cartItem.get_unit_price());
            cv.put(KEY_STATUS,cartItem.get_status());
            db.insert(TABLE_CART_ITEM,null,cv);
            return 1;
        } catch (Exception ex){
            return -1;
        }
    }
    public ArrayList<CartItem> getByCartID(Integer id) {
        ArrayList<CartItem> lstCartItem = new ArrayList<CartItem>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "Select * From "+TABLE_CART_ITEM + " Where "+KEY_CART_ID +" = "+id;
            Cursor cursor = db.rawQuery(query,null);
            int iProductID = cursor.getColumnIndex(KEY_PRODUCT_ID);
            int iCartId = cursor.getColumnIndex(KEY_CART_ID);
            int iQuantity = cursor.getColumnIndex(KEY_QUANTITY);
            int iStatus = cursor.getColumnIndex(KEY_STATUS);
            int iUnitPrice = cursor.getColumnIndex(KEY_UNIT_PRICE);

            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {

                Integer productId = cursor.getInt(iProductID);

                CartItem cartItem = new CartItem();
                cartItem.set_cart_id(cursor.getInt(iCartId));
                cartItem.set_product(dbProduct.getByProductID(productId));
                cartItem.set_unit_price(cursor.getInt(iUnitPrice));
                cartItem.set_status(cursor.getInt(iStatus));
                cartItem.set_quantity(cursor.getInt(iQuantity));
                lstCartItem.add(cartItem);
            }
        } catch (Exception e) {
            return null;
        }
        return lstCartItem;
    }
}
