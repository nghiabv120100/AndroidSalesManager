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
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;

public class CategoryDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "salesManager";
    private static final String TABLE_CATEGORY = "category";
    private static final String KEY_ID ="_id";
    private static final String KEY_IMAGE="_image";
    private static final String KEY_NAME = "_name";
    private static final String KEY_STATUS = "_status";

    public CategoryDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "Create Table "+TABLE_CATEGORY+"("+KEY_ID+" Integer Primary Key ,"
                + KEY_IMAGE+ " Blob, "+ KEY_NAME+ " Text, "+ KEY_STATUS+ " Integer) ";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete table
        db.execSQL("Drop Table If Exists "+TABLE_CATEGORY);

        //Create new table
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete table
        db.execSQL("Drop Table If Exists "+TABLE_CATEGORY);

        //Create new table
        onCreate(db);

    }

    public int addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // convert bitmap to byte
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            category.get_image().compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            byte[] bytesImage = byteArrayOutputStream.toByteArray();
            bytesImage = imagemTratada(bytesImage);

            ContentValues cv = new ContentValues();
            cv.put(KEY_ID, category.get_id());
            cv.put(KEY_IMAGE, bytesImage);
            cv.put(KEY_NAME, category.get_name());
            cv.put(KEY_STATUS, category.get_status());
            db.insert(TABLE_CATEGORY, null, cv);
            db.close();
            return 1;

        } catch (Exception ex) {
            db.close();
            return -1;
        }
    }
    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // convert bitmap to byte
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            category.get_image().compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            byte[] bytesImage = byteArrayOutputStream.toByteArray();
            bytesImage = imagemTratada(bytesImage);

            ContentValues cv = new ContentValues();
            cv.put(KEY_ID, category.get_id());
            cv.put(KEY_IMAGE, bytesImage);
            cv.put(KEY_NAME, category.get_name());
            cv.put(KEY_STATUS, category.get_status());
            db.update(TABLE_CATEGORY, cv, "_id = ?", new String[]{category.get_id().toString()});
            db.close();
            return 1;

        } catch (Exception ex) {
            db.close();
            return -1;
        }
    }

    public Category findByID(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="Select * From "+ TABLE_CATEGORY + " Where _id = '"+id+ "'";

        Cursor cursor= db.rawQuery(query,null);

        cursor.moveToFirst();

        int iID = cursor.getColumnIndex(KEY_ID);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iImage = cursor.getColumnIndex(KEY_IMAGE);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);
        Category category = new Category();
        if (cursor.getCount() > 0) {
            category.set_id(cursor.getInt(iID));
            category.set_name(cursor.getString(iName));
            category.set_status(cursor.getInt(iStatus));
            // convert byte[] to bitmap
            byte[] bytesImage =cursor.getBlob(iImage);
            if (bytesImage != null) {
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
                category.set_image(bitmapImage);
            }

        }
        return category;
    }

    public ArrayList<Category> findAllCategory() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Category> lstCategory = new ArrayList<Category>();

        String query = "Select * from "+ TABLE_CATEGORY +" Where "+ KEY_STATUS+" !=0" ;


        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        int iID = cursor.getColumnIndex(KEY_ID);
        int iImage = cursor.getColumnIndex(KEY_IMAGE);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            Category category = new Category();
            category.set_id(cursor.getInt(iID));

            // convert byte to bitmap
            byte[] bytesImage =cursor.getBlob(iImage);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            category.set_image(bitmapImage);


            category.set_name(cursor.getString(iName));
            category.set_status(cursor.getInt(iStatus));
            lstCategory.add(category);
        }
        db.close();
        return lstCategory;
    }
}
