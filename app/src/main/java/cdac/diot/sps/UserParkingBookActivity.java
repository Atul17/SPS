package cdac.diot.sps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserParkingBookActivity extends AppCompatActivity {
    private String prk_no;
    private EditText edtprk_slot_no, edtprk_slot_vehicle;
    private Button btnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parking_book);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            prk_no = extras.getString("prk_no");
        }

        edtprk_slot_no = findViewById(R.id.edtprk_slot_no);
        edtprk_slot_vehicle = findViewById(R.id.edtprk_slot_vehicle);
        btnBook = findViewById(R.id.btnBookSlt);
        edtprk_slot_no.setText(prk_no);
        

    }
}
