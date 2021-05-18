package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;

public class CategoryDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "salesManager";
    private static final String TABLE_CATEGORY = "category";
    private static final String KEY_ID ="_id";
    private static final String KEY_IMAGE="_image";
    private static final String KEY_NAME = "_name";
    private static final String KEY_STATUS = "_status";

    public CategoryDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "Create Table "+TABLE_CATEGORY+"("+KEY_ID+" Integer Primary Key ,"
                + KEY_IMAGE+ " Text, "+ KEY_NAME+ " Text, "+ KEY_STATUS+ " Integer) ";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete table
        db.execSQL("Drop Table If Exists "+TABLE_CATEGORY);

        //Create new table
        onCreate(db);
    }
    public int addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID,category.get_id());
            cv.put(KEY_IMAGE,category.get_image());
            cv.put(KEY_NAME,category.get_name());
            cv.put(KEY_STATUS,category.get_status());
            db.insert(TABLE_CATEGORY,null,cv);
            db.close();
            return 1;

        } catch (Exception ex) {
            db.close();
            return -1;
        }
    }

    public ArrayList<Category> findAllCategory() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Category> lstCategory = new ArrayList<Category>();

        String query = "Select * from "+ TABLE_CATEGORY;


        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        int iID = cursor.getColumnIndex(KEY_ID);
        int iImage = cursor.getColumnIndex(KEY_IMAGE);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            Category category = new Category();
            category.set_id(cursor.getInt(iID));
            category.set_image(cursor.getString(iImage));
            category.set_name(cursor.getString(iName));
            category.set_status(cursor.getInt(iStatus));
            lstCategory.add(category);
        }
        db.close();
        return lstCategory;
    }
}
