package cdac.diot.sps;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserParkingBookActivity extends AppCompatActivity {
    private String prk_no, vehicle_no;
    private EditText edtprk_slot_no, edtprk_slot_vehicle;
    private Button btnBook;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private TextInputLayout txtinputvehicle;

    private ProgressBar mBar;

    private String userID;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parking_book);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            prk_no = extras.getString("prk_no");
        }

        mContext = UserParkingBookActivity.this;

        mBar = findViewById(R.id.progressbar);
        txtinputvehicle = findViewById(R.id.ed2);
        edtprk_slot_no = findViewById(R.id.edtprk_slot_no);
        edtprk_slot_vehicle = findViewById(R.id.edtprk_slot_vehicle);
        btnBook = findViewById(R.id.btnBookSlt);
        edtprk_slot_no.setText(prk_no);


        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("sps_booking_details");

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle_no = edtprk_slot_vehicle.getText().toString().trim();
                if (vehicle_no.isEmpty()) {
                    txtinputvehicle.setError("Please Enter Vehicle Number");
                } else {
                    mBar.setVisibility(View.VISIBLE);
                    txtinputvehicle.setErrorEnabled(false);
                    if (TextUtils.isEmpty(userID)) {
                        bookPark(prk_no, vehicle_no);
                    }
                }

            }
        });


    }

    private void bookPark(String prk_no, String vehicle_no) {
        if (TextUtils.isEmpty(userID)) {
            userID = mFirebaseDatabase.push().getKey();
        }
        mBar.setVisibility(View.GONE);
        ParkingBookingDetails bookingDetails = new ParkingBookingDetails(prk_no, vehicle_no);
        mFirebaseDatabase.child(userID).setValue(bookingDetails);
        Toast.makeText(mContext, "Booking Successfull", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mContext, UserActivity.class);
        startActivity(intent);

    }
}
