package cdac.diot.sps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAccountDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView mtxtUserName, mtxtUserEmail, mtxtUserMob;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener listener;
    private Context mContext;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_details);
        mContext = UserAccountDetailsActivity.this;

        mtxtUserName = findViewById(R.id.txt_user_name);
        mtxtUserEmail = findViewById(R.id.txt_user_email);
        mtxtUserMob = findViewById(R.id.txt_user_mob);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null) {
                    startActivity(new Intent(mContext, SignInActivity.class));
                    finish();
                }
            }
        };
        mtxtUserName.setText(user.getDisplayName().toString().trim());
        mtxtUserEmail.setText(user.getEmail().toString().trim());
        mtxtUserMob.setText(user.getPhoneNumber().toString().trim());

    }
}
