package cdac.diot.sps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_usr_name, edt_usr_email, edt_usr_mob, edt_usr_passwd, edt_usr_cnf_passwd;
    private Button btnreg;
    private Context mContext;
    private TextInputLayout txtInputname, txtInputemail, txtInputmob, txtInputpasswd, txtInputcnfpasswd;
    private String name, email, mob, passwd, cnfpasswd;
    private ProgressBar mProgressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mContext = SignUpActivity.this;
        edt_usr_name = findViewById(R.id.edt_user_name);
        edt_usr_email = findViewById(R.id.edt_user_email);
        edt_usr_mob = findViewById(R.id.edt_user_mobile);
        edt_usr_passwd = findViewById(R.id.edt_user_passwd);
        edt_usr_cnf_passwd = findViewById(R.id.edt_user_cnf_passwd);

        mProgressBar = findViewById(R.id.progressbar);
        auth = FirebaseAuth.getInstance();
        txtInputname = findViewById(R.id.txtinputname);
        txtInputemail = findViewById(R.id.txtinputemail);
        txtInputmob = findViewById(R.id.txtinputmob);
        txtInputpasswd = findViewById(R.id.txtinputpasswd);
        txtInputcnfpasswd = findViewById(R.id.txtinputcnfpasswd);
        btnreg = findViewById(R.id.btnregister);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("sps_users");

        mFirebaseInstance.getReference("app_title").setValue("Smart Parking System");


        btnreg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnregister:
                name = edt_usr_name.getText().toString().trim();
                email = edt_usr_email.getText().toString().trim();
                mob = edt_usr_mob.getText().toString().trim();
                passwd = edt_usr_passwd.getText().toString().trim();
                cnfpasswd = edt_usr_cnf_passwd.getText().toString().trim();

                if (name.isEmpty()) {
                    txtInputname.setError("Please Enter Name");
                } else {
                    txtInputname.setErrorEnabled(false);
                }

                if (email.isEmpty()) {
                    txtInputemail.setError("Please Enter Email");

                } else {
                    txtInputemail.setErrorEnabled(false);
                }
                if (mob.isEmpty()) {
                    txtInputmob.setError("Please Enter Mobile");

                } else {
                    txtInputmob.setErrorEnabled(false);
                }
                if (passwd.isEmpty()) {
                    txtInputpasswd.setError("Please Enter Password");

                } else {
                    txtInputpasswd.setErrorEnabled(false);
                }
                if (cnfpasswd.isEmpty()) {
                    txtInputcnfpasswd.setError("Please Enter Confirm Password");

                } else {
                    txtInputcnfpasswd.setErrorEnabled(false);
                }
                if (!passwd.equals(cnfpasswd)) {
                    Toast.makeText(mContext, "Password Don't Match", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(mContext, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (TextUtils.isEmpty(userID)) {
                                createUser(name, email, mob, passwd);
                            }
                            Intent intent = new Intent(mContext, SignInActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    }
                });


        }

    }

    private void createUser(String name, String email, String mob, String passwd) {
        if (TextUtils.isEmpty(userID)) {
            userID = mFirebaseDatabase.push().getKey();
        }
        UserDetails userDetails = new UserDetails(name, email, mob, passwd);
        mFirebaseDatabase.child(userID).setValue(userDetails);
    }

}
