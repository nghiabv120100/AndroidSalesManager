package hcmute.edu.vn.mssv18110324.salesmanager.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.admin.AdminHomeActivity;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.client.HomeActivity;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;

public class Login extends AppCompatActivity {
    public static final String MY_PREFS_FILENAME="hcmute.edu.vn.mssv18110324.salesmanager.UserId";

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText txtEmail,txtPassword;
    Button btnRegister, btnLogin;
    TextView txtForgotPassword;

    UserDatabaseHandler db = new UserDatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
        /*User admin = new User(null,"Bùi Văn Nghĩa","nghiaadmin2@gmail.com","01111","123456",1,1);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.ancol);
        admin.set_avatar(icon);
        db.addUser(admin);
*/

    }
    private void addControls() {
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
    }
    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEmail.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(Login.this,"Please enter all fields!",Toast.LENGTH_LONG).show();
                } else {
                    String email = txtEmail.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    showProgress(true);

                    User user = db.getUserByEmail(email);

                    if (user.get_email()==null) {
                        Toast.makeText(Login.this,"Email incorrect",Toast.LENGTH_LONG).show();
                        showProgress(false);
                    } else if (user.get_email().equals(email) && user.get_password().equals(password)) {
                        Toast.makeText(Login.this,"Logged successfully",Toast.LENGTH_LONG).show();

                        Intent intent;
                        if (user.get_role() == 1) {
                            intent = new Intent(Login.this, AdminHomeActivity.class);
                        } else {
                            intent = new Intent(Login.this, HomeActivity.class);
                        }

                        // add session
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_FILENAME,MODE_PRIVATE).edit();
                        editor.putInt("userId",user.get_id());
                        editor.commit();
                        //

                        startActivity(intent);
                        showProgress(false);
                    } else {
                        Toast.makeText(Login.this,"Error: Logged fail",Toast.LENGTH_LONG).show();
                        showProgress(false);
                    }
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });
    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}