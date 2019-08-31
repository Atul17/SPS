package cdac.diot.sps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserActivity2 extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView rcv;
    private RecyclerViewAdapter adapter;

    private List<ParkingSlotDetails> slotData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("sps_parking_slots_data");

        rcv=findViewById(R.id.rcv_slot_data);

        slotData=new ArrayList<>();


        //RecyclerViewAdapter adapter =new RecyclerViewAdapter(this,slotData);
        rcv.setLayoutManager(new GridLayoutManager(this,3));
       // rcv.setAdapter(adapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getSlotData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //getSlotData(dataSnapshot);
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

    private void getSlotData(DataSnapshot dataSnapshot) {
        ParkingSlotDetails slotDetails = dataSnapshot.getValue(ParkingSlotDetails.class);

        String pl_name = slotDetails.getpl_name();
        Long book_status = slotDetails.getBook_status();
        Long slot_status = slotDetails.getSlot_status();

        ParkingSlotDetails newDetails = new ParkingSlotDetails(book_status,slot_status,pl_name);
        slotData.add(newDetails);

        adapter = new RecyclerViewAdapter(this,slotData);
        rcv.setAdapter(adapter);
    }
}
