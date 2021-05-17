package hcmute.edu.vn.mssv18110324.salesmanager.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
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
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;

public class Register extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText txtName,txtEmail,txtPassword,txtConfirmPassword,txtPhoneNumber;
    Button btnRegister;

    UserDatabaseHandler db = new UserDatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addControls();
        addEvents();
    }

    private void addControls() {
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtName.getText().toString().trim().isEmpty() || txtEmail.getText().toString().trim().isEmpty() ||
                        txtPassword.getText().toString().trim().isEmpty() || txtConfirmPassword.getText().toString().trim().isEmpty() ) {
                    Toast.makeText(Register.this,"Please enter all details!",Toast.LENGTH_LONG).show();
                } else {
                    if (txtPassword.getText().toString().trim().equals(txtConfirmPassword.getText().toString().trim())) {
                        String name = txtName.getText().toString().trim();
                        String email = txtEmail.getText().toString().trim();
                        String phoneNumber = txtPhoneNumber.getText().toString().trim();
                        String password = txtPassword.getText().toString().trim();

                        User user = new User();
                        user.set_full_name(name);
                        user.set_password(password);
                        user.set_phone_number(phoneNumber);
                        user.set_email(email);


                        showProgress(true);

                        int result= db.addUser(user);

                        if (result ==1 ) {
                            showProgress(false);
                            Toast.makeText(Register.this,"Successfully registered!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Register.this,Login.class));
                        } else {
                            Toast.makeText(Register.this,"fail registered",Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }

                        /*
                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                showProgress(false);
                                Toast.makeText(Register.this,"Successfully registered!",Toast.LENGTH_LONG).show();
                                Register.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(Register.this,"Error:"+fault.getMessage(),Toast.LENGTH_LONG).show();
                                showProgress(false);
                            }
                        });*/

                    }
                    else {
                        Toast.makeText(Register.this,"Please make sure that your password and confirm password is the same!",Toast.LENGTH_LONG).show();
                    }
                }
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