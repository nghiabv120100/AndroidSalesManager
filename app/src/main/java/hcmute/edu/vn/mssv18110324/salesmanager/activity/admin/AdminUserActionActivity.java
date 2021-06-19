package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.Login;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.Register;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.UserAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;

public class AdminUserActionActivity extends AppCompatActivity  {
    private static final int IMAGE_PICK_CODE_5 = 1000;
    private static final int PERMISSION_CODE_5 = 1001;

    EditText txtName,txtEmail,txtPassword,txtConfirmPassword,txtPhoneNumber;
    Button btnRegister;
    ImageView btnAvatar;
    Spinner spinnerRole;
    Integer role = 0;
    Integer id = -1; // id of user

    UserDatabaseHandler db = new UserDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_action);

        Integer id = getIntent().getIntExtra("id",-1);

        addControls();
        addEvents();
        if (id != -1) {
            User user = db.getUserByID(id);
            if (user!= null) {
                txtEmail.setText(user.get_email());
                txtName.setText(user.get_full_name());
                txtPassword.setText(user.get_password());
                txtConfirmPassword.setText(user.get_password());
                txtPhoneNumber.setText(user.get_phone_number());
                spinnerRole.setSelection(user.get_role());
                btnAvatar.setImageBitmap(user.get_avatar());
                btnRegister.setText("Cập nhật");

                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (txtName.getText().toString().trim().isEmpty() || txtEmail.getText().toString().trim().isEmpty() ||
                                txtPassword.getText().toString().trim().isEmpty() || txtConfirmPassword.getText().toString().trim().isEmpty() ) {
                            Toast.makeText(AdminUserActionActivity.this,"Vui lòng nhập tất cả các trường!",Toast.LENGTH_LONG).show();
                        } else {

                            if (txtPassword.getText().toString().trim().equals(txtConfirmPassword.getText().toString().trim())) {
                                String name = txtName.getText().toString().trim();
                                String email = txtEmail.getText().toString().trim();
                                String phoneNumber = txtPhoneNumber.getText().toString().trim();
                                String password = txtPassword.getText().toString().trim();

                                Bitmap bm=((BitmapDrawable)btnAvatar.getDrawable()).getBitmap();

                                User user = new User();
                                user.set_id(id);
                                user.set_full_name(name);
                                user.set_password(password);
                                user.set_phone_number(phoneNumber);
                                user.set_email(email);
                                user.set_avatar(bm);
                                user.set_role(role);

                                int result= db.update(user);

                                if (result ==1 ) {
                                    Toast.makeText(AdminUserActionActivity.this,"Cập nhật người dùng thành công",Toast.LENGTH_LONG).show();
                                    AdminUserActionActivity.this.finish();
                                } else {
                                    Toast.makeText(AdminUserActionActivity.this,"Cập nhật người dùng thất bại",Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(AdminUserActionActivity.this,"Mật khẩu xác nhận không trùng khớp.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }

        }
    }


    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
        btnAvatar = findViewById(R.id.btnAvatar);
        spinnerRole = findViewById(R.id.spinnerRole);


    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE_5);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE_5:{
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
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE_5) {
            btnAvatar.setImageURI(data.getData());
        }
    }

    private void addEvents() {
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    role = 0;
                } else if (position ==1) {
                    role = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                        //permission not granted request it
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // show popup for runtime permission
                        requestPermissions(permission,PERMISSION_CODE_5);
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
        if (id == -1 ) {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtName.getText().toString().trim().isEmpty() || txtEmail.getText().toString().trim().isEmpty() ||
                            txtPassword.getText().toString().trim().isEmpty() || txtConfirmPassword.getText().toString().trim().isEmpty() ) {
                        Toast.makeText(AdminUserActionActivity.this,"Please enter all details!",Toast.LENGTH_LONG).show();
                    } else {
                        if (db.isExistEmail(txtEmail.getText().toString().trim()) > 0) {
                            Toast.makeText(AdminUserActionActivity.this,"Email này đã tồn tại",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (txtPassword.getText().toString().trim().equals(txtConfirmPassword.getText().toString().trim())) {
                            String name = txtName.getText().toString().trim();
                            String email = txtEmail.getText().toString().trim();
                            String phoneNumber = txtPhoneNumber.getText().toString().trim();
                            String password = txtPassword.getText().toString().trim();

                            Bitmap bm=((BitmapDrawable)btnAvatar.getDrawable()).getBitmap();

                            User user = new User();
                            user.set_full_name(name);
                            user.set_password(password);
                            user.set_phone_number(phoneNumber);
                            user.set_email(email);
                            user.set_avatar(bm);
                            user.set_role(role);


                            int result= db.addUser(user);

                            if (result ==1 ) {
                                Toast.makeText(AdminUserActionActivity.this,"Thêm người dùng thành công",Toast.LENGTH_LONG).show();
                                AdminUserActionActivity.this.finish();
                            } else {
                                Toast.makeText(AdminUserActionActivity.this,"Thêm người dùng thất bại",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(AdminUserActionActivity.this,"Mật khẩu xác nhận không trùng khớp.",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

    }


}