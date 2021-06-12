package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.User;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "salesManager";
    private static final String TABLE_USER = "user";
    private static final String KEY_ID ="_id";
    private static final String KEY_FULL_NAME="_full_name";
    private static final String KEY_EMAIL = "_email";
    private static final String KEY_PHONE_NUMBER = "_phone_number";
    private static final String KEY_PASSWORD = "_password";
    private static final String KEY_ROLE = "_role";
    private static final String KEY_STATUS ="_status";
    private static final String KEY_AVATAR ="_avatar";

    private byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.5), (int)(bitmap.getHeight()*0.5), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;

    }

    public UserDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "Create table " + TABLE_USER +"("+KEY_ID +" Integer Primary Key , "+ KEY_FULL_NAME+" Text, "
                + KEY_EMAIL+" Text, "+ KEY_PHONE_NUMBER+" Text, "+  KEY_PASSWORD+" Text, "+ KEY_ROLE+" Integer, "
                + KEY_AVATAR+" Blob,"+ KEY_STATUS+" Integer) ";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Delete table
        db.execSQL("Drop Table If Exists "+TABLE_USER);

        //Create new table
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete table
        db.execSQL("Drop Table If Exists "+TABLE_USER);

        //Create new table
        onCreate(db);
    }

    public int addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // convert bitmap to byte
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            user.get_avatar().compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            byte[] bytesImage = byteArrayOutputStream.toByteArray();

            bytesImage = imagemTratada(bytesImage);

            ContentValues cv = new ContentValues();
            // Put data
            cv.put(KEY_ID,user.get_id());
            cv.put(KEY_FULL_NAME,user.get_full_name());
            cv.put(KEY_EMAIL,user.get_email());
            cv.put(KEY_PHONE_NUMBER,user.get_phone_number());
            cv.put(KEY_PASSWORD,user.get_password());
            cv.put(KEY_ROLE,user.get_role());
            cv.put(KEY_AVATAR,bytesImage);
            cv.put(KEY_STATUS,user.get_status());

            //insert data into sqlite
            db.insert(TABLE_USER,null,cv);
            db.close();
            return 1;
        } catch (Exception e) {
            db.close();
            return -1;
        }
    }

    public int update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // convert bitmap to byte
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            user.get_avatar().compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            byte[] bytesImage = byteArrayOutputStream.toByteArray();
            bytesImage = imagemTratada(bytesImage);
            ContentValues cv = new ContentValues();
            // Put data
            cv.put(KEY_ID,user.get_id());
            cv.put(KEY_FULL_NAME,user.get_full_name());
            cv.put(KEY_EMAIL,user.get_email());
            cv.put(KEY_PHONE_NUMBER,user.get_phone_number());
            cv.put(KEY_PASSWORD,user.get_password());
            cv.put(KEY_ROLE,user.get_role());
            cv.put(KEY_AVATAR,bytesImage);
            cv.put(KEY_STATUS,user.get_status());

            //insert data into sqlite
            db.update(TABLE_USER, cv, "_id = ?", new String[]{user.get_id().toString()});
            db.close();
            return 1;
        } catch (Exception e) {
            db.close();
            return -1;
        }
    }

    public List<User> getAllUser() {
        List<User> lstUser = new ArrayList<User>();
        // Select All Query
        String query = "Select * From "+ TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase(); //Why use getWritable but not getReadable
        Cursor cursor = db.rawQuery(query,null);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            User user = new User();

        }
        return null;
    }

    public User getUserByEmail(String email) {
        String[] columns = new String[] {KEY_ID,KEY_FULL_NAME,KEY_EMAIL,KEY_PHONE_NUMBER,KEY_PASSWORD,KEY_ROLE,KEY_AVATAR,KEY_STATUS};

        SQLiteDatabase db = this.getReadableDatabase();
        String query ="Select * From "+ TABLE_USER+ " Where _email = '"+email+ "'";

        Cursor cursor= db.rawQuery(query,null);

        cursor.moveToFirst();

        int iID = cursor.getColumnIndex(KEY_ID);
        int iFullName = cursor.getColumnIndex(KEY_FULL_NAME);
        int iEmail = cursor.getColumnIndex(KEY_EMAIL);
        int iPhoneNumber = cursor.getColumnIndex(KEY_PHONE_NUMBER);
        int iPassword = cursor.getColumnIndex(KEY_PASSWORD);
        int iRole = cursor.getColumnIndex(KEY_ROLE);
        int iAvatar = cursor.getColumnIndex(KEY_AVATAR);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);
        User user = new User();
        if (cursor.getCount() > 0) {
            user.set_id(cursor.getInt(iID));
            user.set_full_name(cursor.getString(iFullName));
            user.set_email(cursor.getString(iEmail));
            user.set_phone_number(cursor.getString(iPhoneNumber));
            user.set_password(cursor.getString(iPassword));
            user.set_role(cursor.getInt(iRole));
            // convert byte[] to bitmap
            byte[] bytesImage =cursor.getBlob(iAvatar);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            user.set_avatar(bitmapImage);
            //
            user.set_status(cursor.getInt(iStatus));
        }
        return user;
    }

    public User getUserByID(Integer id) {
        String[] columns = new String[] {KEY_ID,KEY_FULL_NAME,KEY_EMAIL,KEY_PHONE_NUMBER,KEY_PASSWORD,KEY_ROLE,KEY_AVATAR,KEY_STATUS};

        SQLiteDatabase db = this.getReadableDatabase();
        String query ="Select * From "+ TABLE_USER+ " Where _id = '"+id+ "'";

        Cursor cursor= db.rawQuery(query,null);

        cursor.moveToFirst();

        int iID = cursor.getColumnIndex(KEY_ID);
        int iFullName = cursor.getColumnIndex(KEY_FULL_NAME);
        int iEmail = cursor.getColumnIndex(KEY_EMAIL);
        int iPhoneNumber = cursor.getColumnIndex(KEY_PHONE_NUMBER);
        int iPassword = cursor.getColumnIndex(KEY_PASSWORD);
        int iRole = cursor.getColumnIndex(KEY_ROLE);
        int iAvatar= cursor.getColumnIndex(KEY_AVATAR);
        int iStatus = cursor.getColumnIndex(KEY_STATUS);
        User user = new User();
        if (cursor.getCount() > 0) {
            user.set_id(cursor.getInt(iID));
            user.set_full_name(cursor.getString(iFullName));
            user.set_email(cursor.getString(iEmail));
            user.set_phone_number(cursor.getString(iPhoneNumber));
            user.set_password(cursor.getString(iPassword));
            user.set_role(cursor.getInt(iRole));
            // convert byte[] to bitmap
            byte[] bytesImage =cursor.getBlob(iAvatar);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            user.set_avatar(bitmapImage);
            //
            user.set_status(cursor.getInt(iStatus));
        }
        return user;
    }


}
