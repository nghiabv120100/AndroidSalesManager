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

public class PersonalInfoActivity extends AppCompatActivity {

    EditText txtName,txtPhoneNumber,txtEmail;
    TextView txtChangePassword;
    Button btnChangeInfo;
    User user;

    UserDatabaseHandler userDatabaseHandler =new UserDatabaseHandler(this);
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
                if ( !userAlter.get_email().equals(user.get_email()) || !userAlter.get_full_name().equals(user.get_full_name()) || !userAlter.get_phone_number().equals(user.get_phone_number())) {
                    user.set_full_name(userAlter.get_full_name());
                    user.set_email(userAlter.get_email());
                    user.set_phone_number(userAlter.get_phone_number());
                    Integer result = userDatabaseHandler.update(user);
                    if (result > 0) {
                        txtEmail.setFocusable(false);
                        txtName.setFocusable(false);
                        txtPhoneNumber.setFocusable(false);
                        Toast.makeText(PersonalInfoActivity.this,"Thay đổi thông tin thành công",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PersonalInfoActivity.this,"Thay đổi thông tin thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtEmail = findViewById(R.id.txtEmail);
        txtChangePassword = findViewById(R.id.txtChangePassword);
        btnChangeInfo = findViewById(R.id.btnChangeInfo);


        SharedPreferences pref = getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE);
        Integer id = pref.getInt("userId",-1);  // Second argument is default value
        if (id != -1) {
            user =  userDatabaseHandler.getUserByID(id);
            txtName.setText(user.get_full_name());
            txtPhoneNumber.setText(user.get_phone_number());
            txtEmail.setText(user.get_email());
        } else {
            // toward login
        }



    }


}