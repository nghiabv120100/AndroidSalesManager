package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.Login;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;

public class PersonalInfoActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE_2 = 1000;
    private static final int PERMISSION_CODE_2 = 1001;
    private int isChangeAvatar = 0;

    EditText txtName,txtPhoneNumber;
    TextView txtChangePassword,txtEmail;
    Button btnChangeInfo;
    User user;
    ImageView imgAvatar;

    UserDatabaseHandler userDatabaseHandler =new UserDatabaseHandler(this);

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE_2);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE_2:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this,"Permission denied...!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE_2) {
            imgAvatar.setImageURI(data.getData());
            isChangeAvatar =1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userAlter = new User();
                userAlter.set_full_name(txtName.getText().toString().trim());
                userAlter.set_email(txtEmail.getText().toString().trim());
                userAlter.set_phone_number(txtPhoneNumber.getText().toString().trim());
                if ( !userAlter.get_email().equals(user.get_email()) || !userAlter.get_full_name().equals(user.get_full_name()) || !userAlter.get_phone_number().equals(user.get_phone_number()) || isChangeAvatar ==1) {
                    user.set_full_name(userAlter.get_full_name());
                    user.set_email(userAlter.get_email());
                    user.set_phone_number(userAlter.get_phone_number());
                    Bitmap bm=((BitmapDrawable)imgAvatar.getDrawable()).getBitmap();
                    user.set_avatar(bm);
                    Integer result = userDatabaseHandler.update(user);
                    if (result > 0) {
                        txtEmail.setFocusable(false);
                        txtName.setFocusable(false);
                        txtPhoneNumber.setFocusable(false);
                        finish();
                        Toast.makeText(PersonalInfoActivity.this,"Thay đổi thông tin thành công",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PersonalInfoActivity.this,"Thay đổi thông tin thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                }
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                        //permission not granted request it
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // show popup for runtime permission
                        requestPermissions(permission,PERMISSION_CODE_2);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }
            }
        });

        txtChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtEmail = findViewById(R.id.txtEmail);
        txtChangePassword = findViewById(R.id.txtChangePassword);
        btnChangeInfo = findViewById(R.id.btnChangeInfo);
        imgAvatar = findViewById(R.id.imgAvatar);

        SharedPreferences pref = getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE);
        Integer id = pref.getInt("userId",-1);  // Second argument is default value
        if (id != -1) {
            user =  userDatabaseHandler.getUserByID(id);
            txtName.setText(user.get_full_name());
            txtPhoneNumber.setText(user.get_phone_number());
            txtEmail.setText(user.get_email());
            imgAvatar.setImageBitmap(user.get_avatar());
        } else {
            // toward login
        }



    }


}