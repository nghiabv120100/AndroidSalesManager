package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.Login;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText txtPassword,txtNewPassword,txtConfirmNewPassword;
    Button btnChangePassword;
    TextView txtBackToInfo;
    UserDatabaseHandler userDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        userDatabaseHandler  =new UserDatabaseHandler(this);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtPassword = findViewById(R.id.txtPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmNewPassword = findViewById(R.id.txtConfirmNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        txtBackToInfo = findViewById(R.id.txtBackToInfo);
    }

    private void addEvents() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtPassword.getText().toString().isEmpty() || txtNewPassword.getText().toString().isEmpty() || txtConfirmNewPassword.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this,"Vui lòng nhâp đầy đủ thông tin.",Toast.LENGTH_SHORT).show();

                } else {
                    if (txtNewPassword.getText().toString().trim().equals(txtConfirmNewPassword.getText().toString().trim())) {
                        SharedPreferences pref = getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE);
                        Integer id = pref.getInt("userId",-1);
                        if (id != -1) {
                            User user =  userDatabaseHandler.getUserByID(id);
                            if (txtPassword.getText().toString().trim().equals(user.get_password())) {
                                try {
                                    user.set_password(txtNewPassword.getText().toString().trim());
                                    userDatabaseHandler.update(user);
                                    Toast.makeText(ChangePasswordActivity.this,"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                                    ChangePasswordActivity.this.finish();
                                } catch (Exception e) {
                                    Toast.makeText(ChangePasswordActivity.this,"Thao tác thất bại.",Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(ChangePasswordActivity.this,"Mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // toward login
                        }
                    } else {
                        Toast.makeText(ChangePasswordActivity.this,"Mật khẩu xác nhận không đúng.",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        txtBackToInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordActivity.this.finish();
            }
        });
    }


}