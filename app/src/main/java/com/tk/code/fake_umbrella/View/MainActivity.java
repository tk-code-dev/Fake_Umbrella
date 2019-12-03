package com.tk.code.fake_umbrella.View;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.TextView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.tk.code.fake_umbrella.Model.Customer;
//import com.tk.code.fake_umbrella.R;
//
//import java.util.ArrayList;
//import java.util.List;

//public class MainActivity extends AppCompatActivity {
//
//    public static String BaseUrl = "http://api.openweathermap.org/";
//    public static String AppId = "33a82b8c0da7233c25d00cf6f830cb9a";
//
//    // Get a reference to our posts
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("customer/");
//
//    public static List<Customer> itemCustomers = new ArrayList<>();
//
//    TextView titleTV;
//    TextView multiTV;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        titleTV = findViewById(R.id.titleTV);
//        multiTV = findViewById(R.id.multiTV);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Customer customer = dataSnapshot.getValue(Customer.class);
//                    itemCustomers.add(customer);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.item_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_add:
//                Intent myIntent = new Intent(MainActivity.this, InputInfoActivity.class);
//                myIntent.putExtra("INTENT_ADD", "BACK"); //Optional parameters
//                MainActivity.this.startActivity(myIntent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//}