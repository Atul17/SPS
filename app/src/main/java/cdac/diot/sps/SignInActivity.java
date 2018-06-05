package cdac.diot.sps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_signup;
    private Button btn_signin;
    private EditText edt_emailid, edt_passwd;
    private String email, pwsd;
    private Context mContext;
    private FirebaseAuth auth;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mContext = SignInActivity.this;
        txt_signup = findViewById(R.id.txt_signup);
        edt_emailid = findViewById(R.id.edt_email_id);
        edt_passwd = findViewById(R.id.edt_passwd);
        btn_signin = findViewById(R.id.bt_sign_in);
        mProgressBar = findViewById(R.id.progressbar);


        auth = FirebaseAuth.getInstance();

        btn_signin.setOnClickListener(this);

        txt_signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sign_in:
                //validateLogin();
                email = edt_emailid.getText().toString().trim();
                pwsd = edt_passwd.getText().toString().trim();
                if (email.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Email ID", Snackbar.LENGTH_SHORT).show();
                }
                if (pwsd.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, pwsd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            if (pwsd.isEmpty()) {
                                Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_SHORT).show();
                            }
                            Toast.makeText(mContext, "Authentication Failed", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(mContext, "Login Success", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(mContext, UserActivity.class);
                            i.putExtra("email", email);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                break;
            case R.id.txt_signup:
                Intent i = new Intent(mContext, SignUpActivity.class);
                startActivity(i);
                break;
        }

    }

}
