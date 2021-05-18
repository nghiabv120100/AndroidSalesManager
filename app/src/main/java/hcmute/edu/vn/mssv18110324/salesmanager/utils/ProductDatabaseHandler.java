package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;

public class ProductDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "salesManager";
    private static final String TABLE_PRODUCT = "product";
    private static final String KEY_ID ="_id";
    private static final String KEY_NAME="_name";
    private static final String KEY_QUANTITY = "_quantity";
    private static final String KEY_PRICE = "_price";
    private static final String KEY_DESCRIBE = "_describe";
    private static final String KEY_IMAGE = "_image";
    private static final String KEY_CATEGORY_ID ="_category_id";
    private static final String KEY_STATUS ="_status";
    public ProductDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="Create Table "+TABLE_PRODUCT+"("+KEY_ID +" Integer Primary Key,"+KEY_NAME+" Text,"
                +KEY_QUANTITY+" Integer,"+KEY_PRICE+" Integer,"+KEY_DESCRIBE+" Text,"+KEY_IMAGE+" Text,"
                +KEY_CATEGORY_ID+" Integer,"+KEY_STATUS+" Integer)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete table
        db.execSQL("Drop Table If Exists "+TABLE_PRODUCT);

        //Create new table
        onCreate(db);
    }

    public int addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID, product.get_id());
            cv.put(KEY_NAME,product.get_name());
            cv.put(KEY_QUANTITY,product.get_quantity());
            cv.put(KEY_PRICE,product.get_price());
            cv.put(KEY_DESCRIBE,product.get_describe());
            cv.put(KEY_IMAGE,product.get_image());
            cv.put(KEY_CATEGORY_ID,product.get_category_id());
            cv.put(KEY_STATUS,product.get_status());

            db.insert(TABLE_PRODUCT,null,cv);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    public ArrayList<Product> findByCategoryID(int categoryID) {
        ArrayList<Product> lstProduct = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * From "+TABLE_PRODUCT + " Where "+KEY_CATEGORY_ID +" = "+categoryID;
        Cursor cursor = db.rawQuery(query,null);

        int iID = cursor.getColumnIndex(KEY_ID);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iPrice = cursor.getColumnIndex(KEY_PRICE);
        int iDescribe = cursor.getColumnIndex(KEY_DESCRIBE);
        int iImage = cursor.getColumnIndex(KEY_IMAGE);
        int iCategoryID = cursor.getColumnIndex(KEY_CATEGORY_ID);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            Product product = new Product();
            product.set_id(cursor.getInt(iID));
            product.set_name(cursor.getString(iName));
            product.set_price(cursor.getInt(iPrice));
            product.set_describe(cursor.getString(iDescribe));
            product.set_image(cursor.getString(iImage));
            product.set_category_id(cursor.getInt(iCategoryID));
            product.set_status(cursor.getInt(iStatus));
            lstProduct.add(product);
        }
        return lstProduct.isEmpty()?null:lstProduct;
    }

    public ArrayList<Product> findAllProduct() {
        ArrayList<Product> lstProduct = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * From "+TABLE_PRODUCT;
        Cursor cursor = db.rawQuery(query,null);

        int iID = cursor.getColumnIndex(KEY_ID);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iPrice = cursor.getColumnIndex(KEY_PRICE);
        int iDescribe = cursor.getColumnIndex(KEY_DESCRIBE);
        int iImage = cursor.getColumnIndex(KEY_IMAGE);
        int iCategoryID = cursor.getColumnIndex(KEY_CATEGORY_ID);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            Product product = new Product();
            product.set_id(cursor.getInt(iID));
            product.set_name(cursor.getString(iName));
            product.set_price(cursor.getInt(iPrice));
            product.set_describe(cursor.getString(iDescribe));
            product.set_image(cursor.getString(iImage));
            product.set_category_id(cursor.getInt(iCategoryID));
            product.set_status(cursor.getInt(iStatus));
            lstProduct.add(product);
        }
        return lstProduct.isEmpty()?null:lstProduct;
    }
}
