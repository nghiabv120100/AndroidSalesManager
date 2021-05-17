package hcmute.edu.vn.mssv18110324.salesmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class ResetPassword extends AppCompatActivity {

    Button btnReset;
    EditText txtEmail;
    TextView txtBackToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnReset = findViewById(R.id.btnReset);
        txtEmail = findViewById(R.id.txtEmail);
        txtBackToLogIn = findViewById(R.id.txtBackToLogIn);

    }

    private void addEvents() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEmail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(hcmute.edu.vn.mssv18110324.salesmanager.ResetPassword.this,"Please enter your email!",Toast.LENGTH_LONG).show();
                } else {
                    String email = txtEmail.getText().toString().trim();
                    Backendless.UserService.restorePassword(email, new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            Toast.makeText(hcmute.edu.vn.mssv18110324.salesmanager.ResetPassword.this,"Reset instruction sent to email address!",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(hcmute.edu.vn.mssv18110324.salesmanager.ResetPassword.this,"Error: "+fault.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        txtBackToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hcmute.edu.vn.mssv18110324.salesmanager.ResetPassword.this.finish();
            }
        });
    }

}