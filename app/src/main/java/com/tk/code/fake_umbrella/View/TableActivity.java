package com.tk.code.fake_umbrella.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tk.code.fake_umbrella.Model.Adapter;
import com.tk.code.fake_umbrella.Model.Customer;
import com.tk.code.fake_umbrella.Model.SampleData;
import com.tk.code.fake_umbrella.Model.SampleWeatherData;
import com.tk.code.fake_umbrella.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableActivity extends AppCompatActivity {

//    SampleData sampleData = new SampleData();
    SampleWeatherData sampleWeatherData = new SampleWeatherData();

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("customer//");

    public static List<Customer> itemCustomers = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        recyclerView = findViewById(R.id.myRecyclerView);
        // use this setting to improve performance
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        // copy to ArrayList    TEST
//        final List<String> itemNames = new ArrayList<String>(Arrays.asList(sampleData.names));
//        final List<String> itemContacters = new ArrayList<String>(Arrays.asList(sampleData.contacts));
//        final List<String> itemPhones = new ArrayList<String>(Arrays.asList(sampleData.phones));
//        final List<String> itemLocations = new ArrayList<String>(Arrays.asList(sampleData.locations));
//        final List<Integer> itemNumsOfEmployees = new ArrayList<Integer>(Arrays.asList(sampleData.numsOfEmployees));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<String> itemDates = new ArrayList<>(Arrays.asList(sampleWeatherData.dates));
        final List<Integer> weather5days = new ArrayList<>(Arrays.asList(sampleWeatherData.weatherIcons));


        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Customer customer = dataSnapshot.getValue(Customer.class);
                    itemCustomers.add(customer);

//                    String str_name = customer.customerName;
//                    String str_contact = customer.contactPerson;
//                    String str_phone = customer.telephone;
//                    String str_location = customer.location;
//                    int int_num = customer.numberOfEmployees;
//                    Log.d("data", String.format("company:%s, contact:%s, phone:%s, location:%s, num:%d", str_name, str_contact, str_phone, str_location, int_num));
//                    for (Customer model : itemCustomers) {
//                        System.out.println(model.customerName);
//                    }
                }

                final RecyclerView.Adapter rAdapter = new Adapter(itemCustomers,
                        itemDates, weather5days);
                recyclerView.setAdapter(rAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent myIntent = new Intent(TableActivity.this, InputInfoActivity.class);
                myIntent.putExtra("INTENT_ADD", "BACK"); //Optional parameters
                TableActivity.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}