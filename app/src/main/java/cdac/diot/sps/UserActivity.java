package cdac.diot.sps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private CardView crdindr, crdwhite;
    private Context mContext;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView txtUserName;
    public String area_name;
    private String name;
    private TextView txt_indr, txt_white;
    private RecyclerView rcv_my_book;
    private FirebaseDatabase mFirebaseIntance;
    private DatabaseReference mFirebaseDatabase;
    private ParkingBookDetailsAdapter detailsAdapter;
    private List<ParkingBookingDetails> bookingDetailsList;
    private ProgressBar bar;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_icon);


        auth = FirebaseAuth.getInstance();

        //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(mContext, SignInActivity.class));
                    finish();
                } else {
                    String userEmail = user.getEmail();
                    String userID = user.getUid();
                    txtUserName.setText(userEmail + " " + userID);
                }

            }
        };


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        crdindr = findViewById(R.id.crdindiranagar);
        crdwhite = findViewById(R.id.crdwhitefield);
        txt_indr = findViewById(R.id.txt_indr_area);
        txt_white = findViewById(R.id.txt_white_area);
        txtUserName = header.findViewById(R.id.txtusr_name);
        bar = findViewById(R.id.progressbar);
        bar.setVisibility(View.VISIBLE);

        rcv_my_book = findViewById(R.id.rcv_my_book);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("email");

        }

        // txtUserName.setText(name);
        mContext = UserActivity.this;
        crdindr.setOnClickListener(this);
        crdwhite.setOnClickListener(this);

        mFirebaseIntance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseIntance.getReference().child("sps_booking_details");

        bookingDetailsList = new ArrayList<>();
        rcv_my_book.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                bar.setVisibility(View.GONE);
                getBookingData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                bar.setVisibility(View.GONE);

                getBookingData(dataSnapshot);
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

    private void getBookingData(DataSnapshot dataSnapshot) {
        ParkingBookingDetails bookingDetails = dataSnapshot.getValue(ParkingBookingDetails.class);
        String area_name = bookingDetails.getArea_name();
        String pl_no = bookingDetails.getPl_no();
        String vehicle_no = bookingDetails.getVehicle_no();
        ParkingBookingDetails details = new ParkingBookingDetails(pl_no, vehicle_no, area_name);
        bookingDetailsList.add(details);
        detailsAdapter = new ParkingBookDetailsAdapter(mContext, bookingDetailsList);
        rcv_my_book.setAdapter(detailsAdapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("Do you want to logout ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                finish();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("Do you want to logout ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    auth.signOut();
                    finish();

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crdindiranagar:
                Intent i = new Intent(mContext, UserParkingActivity.class);
                area_name = txt_indr.getText().toString().trim();
                i.putExtra("area_name", area_name);
                startActivity(i);
                break;
            case R.id.crdwhitefield:
                Intent w = new Intent(mContext, UserParkingActivity.class);
                area_name = txt_white.getText().toString().trim();
                w.putExtra("area_name", area_name);
                startActivity(w);
                break;
        }
    }


}
