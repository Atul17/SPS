package cdac.diot.sps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView mtxtUserName, mtxtUserEmail, mtxtUserMob;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private Context mContext;
    private String email, name, mob, user_email;

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
        email = user.getEmail();
        Log.d("emali", email);

        //mtxtUserName.setText();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("sps_users");

        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUserData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUserData(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
            user_email = userDetails.getEmail();
            mtxtUserEmail.setText(user_email);
            if (email.equals(user_email)) {
                name = userDetails.getUsername();
                mtxtUserName.setText(name);
                mob = userDetails.getMobile();
                mtxtUserMob.setText(mob);
            }
           /* user_email = snapshot.child("email").getValue(String.class);
            if (email.equals(user_email)) {
                name = snapshot.child("name").getValue(String.class);
                mtxtUserName.setText(name);
                mob = snapshot.child("mobile").getValue(String.class);
                mtxtUserMob.setText(mob);
            }*/
        }
    }
}
