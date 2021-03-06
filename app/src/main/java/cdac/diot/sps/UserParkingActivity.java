package cdac.diot.sps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserParkingActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView crdpl1, crdpl2, crdpl3, crdpl4, crdpl5, crdpl6;
    private TextView txtpl1, txtpl2, txtpl3, txtpl4, txtpl5, txtpl6, txtfl1;
    private String prk_name;
    private Context mContext;
    private String userID;
    public String area_name;
    private ProgressBar progressBar;

    private FirebaseDatabase mFirebaseIntance;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parking);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            area_name = bundle.getString("area_name");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        crdpl1 = findViewById(R.id.crdpl1);
        crdpl2 = findViewById(R.id.crdpl2);
        crdpl3 = findViewById(R.id.crdpl3);
        crdpl4 = findViewById(R.id.crdpl4);
        crdpl5 = findViewById(R.id.crdpl5);
        crdpl6 = findViewById(R.id.crdpl6);

        txtpl1 = findViewById(R.id.txtpl1);
        txtpl2 = findViewById(R.id.txtpl2);
        txtpl3 = findViewById(R.id.txtpl3);
        txtpl4 = findViewById(R.id.txtpl4);
        txtpl5 = findViewById(R.id.txtpl5);
        txtpl6 = findViewById(R.id.txtpl6);
        txtfl1 = findViewById(R.id.txtfl1);

        progressBar = findViewById(R.id.pgBar);

        progressBar.setVisibility(View.VISIBLE);

        mContext = UserParkingActivity.this;
        crdpl1.setOnClickListener(this);
        crdpl2.setOnClickListener(this);
        crdpl3.setOnClickListener(this);
        crdpl4.setOnClickListener(this);
        crdpl5.setOnClickListener(this);
        crdpl6.setOnClickListener(this);

        mFirebaseIntance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseIntance.getReference().child("sps_parking_slots_data");
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressBar.setVisibility(View.GONE);
                getSlotData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                progressBar.setVisibility(View.VISIBLE);
                getSlotData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                getSlotData(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                progressBar.setVisibility(View.GONE);
                getSlotData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getSlotData(DataSnapshot dataSnapshot) {
        for (int i = 0; i < 6; i++) {
            progressBar.setVisibility(View.GONE);

            ParkingSlotDetails slotDetails = dataSnapshot.getValue(ParkingSlotDetails.class);
            long b = slotDetails.getBook_status();
            long st = slotDetails.getSlot_status();
            String pl_name = slotDetails.getpl_name();

            switch(pl_name){
                case "pl1":
                    if (b == 1) {
                        crdpl1.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotFull));
                        crdpl1.setClickable(false);
                    }
                    else {
                        crdpl1.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotEmpty));
                    }
                    break;
                case "pl2":
                    if (b == 1) {
                        crdpl2.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotFull));
                        crdpl2.setClickable(false);
                    }
                    else {
                        crdpl2.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotEmpty));
                    }
                    break;
                case "pl3":
                    if (b == 1) {
                        crdpl3.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotFull));
                        crdpl3.setClickable(false);
                    }
                    else {
                        crdpl3.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotEmpty));
                    }
                    break;
                case "pl4":
                    if (b == 1) {
                        crdpl4.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotFull));
                        crdpl4.setClickable(false);
                    }
                    else {
                        crdpl4.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotEmpty));
                    }
                    break;
                case "pl5":
                    if (b == 1) {
                        crdpl5.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotFull));
                        crdpl5.setClickable(false);
                    }
                    else {
                        crdpl5.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotEmpty));
                    }
                    break;
                case "pl6":
                    if (b == 1) {
                        crdpl6.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotFull));
                        crdpl6.setClickable(false);
                    }
                    else {
                        crdpl6.setCardBackgroundColor(getResources().getColor(R.color.colorParkSlotEmpty));
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crdpl1:
                prk_name = txtpl1.getText().toString().trim().toLowerCase();
                Intent i = new Intent(mContext, UserParkingBookActivity.class);
                i.putExtra("prk_no", prk_name);
                i.putExtra("area_name", area_name);
                startActivity(i);
                break;
            case R.id.crdpl2:
                prk_name = txtpl2.getText().toString().trim().toLowerCase();
                Intent i2 = new Intent(mContext, UserParkingBookActivity.class);
                i2.putExtra("prk_no", prk_name);
                i2.putExtra("area_name", area_name);
                startActivity(i2);
                break;
            case R.id.crdpl3:
                prk_name = txtpl3.getText().toString().trim().toLowerCase();
                Intent i3 = new Intent(mContext, UserParkingBookActivity.class);
                i3.putExtra("prk_no", prk_name);
                i3.putExtra("area_name", area_name);
                startActivity(i3);
                break;
            case R.id.crdpl4:
                prk_name = txtpl4.getText().toString().trim().toLowerCase();
                Intent i4 = new Intent(mContext, UserParkingBookActivity.class);
                i4.putExtra("prk_no", prk_name);
                i4.putExtra("area_name", area_name);
                startActivity(i4);
                break;
            case R.id.crdpl5:
                prk_name = txtpl5.getText().toString().trim().toLowerCase();
                Intent i5 = new Intent(mContext, UserParkingBookActivity.class);
                i5.putExtra("prk_no", prk_name);
                i5.putExtra("area_name", area_name);
                startActivity(i5);
                break;
            case R.id.crdpl6:
                prk_name = txtpl6.getText().toString().trim().toLowerCase();
                Intent i6 = new Intent(mContext, UserParkingBookActivity.class);
                i6.putExtra("prk_no", prk_name);
                i6.putExtra("area_name", area_name);
                startActivity(i6);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
