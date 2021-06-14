package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;

public class ProductDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
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
                +KEY_QUANTITY+" Integer,"+KEY_PRICE+" Integer,"+KEY_DESCRIBE+" Text,"+KEY_IMAGE+" Blob,"
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

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete table
        db.execSQL("Drop Table If Exists "+TABLE_PRODUCT);

        //Create new table
        onCreate(db);
    }

    private byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;

    }

    public int addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // convert bitmap to byte
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            product.get_image().compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            byte[] bytesImage = byteArrayOutputStream.toByteArray();

            bytesImage = imagemTratada(bytesImage);

            ContentValues cv = new ContentValues();
            cv.put(KEY_ID, product.get_id());
            cv.put(KEY_NAME,product.get_name());
            cv.put(KEY_QUANTITY,product.get_quantity());
            cv.put(KEY_PRICE,product.get_price());
            cv.put(KEY_DESCRIBE,product.get_describe());
            cv.put(KEY_IMAGE,bytesImage);
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

            // convert byte to bitmap
            byte[] bytesImage =cursor.getBlob(iImage);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            product.set_image(bitmapImage);
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

            // convert byte to bitmap
            byte[] bytesImage =cursor.getBlob(iImage);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            product.set_image(bitmapImage);

            product.set_category_id(cursor.getInt(iCategoryID));
            product.set_status(cursor.getInt(iStatus));
            lstProduct.add(product);
        }
        return lstProduct.isEmpty()?null:lstProduct;
    }


    public Product getByProductID(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From " + TABLE_PRODUCT + " Where _id = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        int iID = cursor.getColumnIndex(KEY_ID);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iPrice = cursor.getColumnIndex(KEY_PRICE);
        int iDescribe = cursor.getColumnIndex(KEY_DESCRIBE);
        int iImage = cursor.getColumnIndex(KEY_IMAGE);
        int iCategoryID = cursor.getColumnIndex(KEY_CATEGORY_ID);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);

        Product product = new Product();

        if (cursor.getCount() > 0) {
            product.set_id(cursor.getInt(iID));
            product.set_name(cursor.getString(iName));
            product.set_price(cursor.getInt(iPrice));
            product.set_describe(cursor.getString(iDescribe));

            // convert byte to bitmap
            byte[] bytesImage = cursor.getBlob(iImage);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            product.set_image(bitmapImage);

            product.set_category_id(cursor.getInt(iCategoryID));
            product.set_status(cursor.getInt(iStatus));
        }
        return product;
    }


    public ArrayList<Product> findByKeyword(String keyword) {
        ArrayList<Product> lstProduct = new ArrayList<Product>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From " + TABLE_PRODUCT + " Where " +KEY_NAME +" Like '%"+keyword+"%'";

        Cursor cursor = db.rawQuery(query, null);



        int iID = cursor.getColumnIndex(KEY_ID);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iPrice = cursor.getColumnIndex(KEY_PRICE);
        int iDescribe = cursor.getColumnIndex(KEY_DESCRIBE);
        int iImage = cursor.getColumnIndex(KEY_IMAGE);
        int iCategoryID = cursor.getColumnIndex(KEY_CATEGORY_ID);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);


        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            Product product = new Product();

            product.set_id(cursor.getInt(iID));
            product.set_name(cursor.getString(iName));
            product.set_price(cursor.getInt(iPrice));
            product.set_describe(cursor.getString(iDescribe));

            // convert byte to bitmap
            byte[] bytesImage = cursor.getBlob(iImage);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            product.set_image(bitmapImage);

            product.set_category_id(cursor.getInt(iCategoryID));
            product.set_status(cursor.getInt(iStatus));

            lstProduct.add(product);
        }

        return lstProduct;
    }
}
