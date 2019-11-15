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
import com.tk.code.fake_umbrella.Model.Customer;
import com.tk.code.fake_umbrella.Model.MyAdapter;
import com.tk.code.fake_umbrella.Model.SampleData;
import com.tk.code.fake_umbrella.Model.SampleWeatherData;
import com.tk.code.fake_umbrella.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    SampleData sampleData = new SampleData();
    SampleWeatherData sampleWeatherData = new SampleWeatherData();

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("customer//");

    List<Customer> customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Customer customer = dataSnapshot.getValue(Customer.class);
                    String str_name = customer.customerName;
                    String str_contact = customer.contactPerson;
                    String str_phone = customer.telephone;
                    String str_location = customer.location;
                    int int_num = customer.numberOfEmployees;

                    customers = new ArrayList<>(Arrays.asList(customer));
                    Log.d("customers", customers.get(0).customerName);
                    Log.d("data", String.format("company:%s, contact:%s, phone:%s, location:%s, num:%d", str_name, str_contact, str_phone, str_location, int_num));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);


        // use this setting to improve performance
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        // copy to ArrayList
//        List<Integer> itemImages = new ArrayList<Integer>(Arrays.asList(sampleData.photos));
        final List<String> itemNames = new ArrayList<String>(Arrays.asList(sampleData.names));
        final List<String> itemContacters = new ArrayList<String>(Arrays.asList(sampleData.contacts));
        final List<String> itemPhones = new ArrayList<String>(Arrays.asList(sampleData.phones));
        final List<String> itemLocations = new ArrayList<String>(Arrays.asList(sampleData.locations));
        final List<Integer> itemNumsOfEmployees = new ArrayList<Integer>(Arrays.asList(sampleData.numsOfEmployees));
        final List<String> itemDates = new ArrayList<>(Arrays.asList(sampleWeatherData.dates));
        final List<Integer> weather5days = new ArrayList<>(Arrays.asList(sampleWeatherData.weatherIcons));
//        final List<String> itemEmails = new ArrayList<String>();
//        for (int i = 0; i < itemNames.size(); i++) {
//            String str = String.format(Locale.ENGLISH, "%s@gmail.com", itemNames.get(i));
//            itemEmails.add(str);
//        }

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Customer customer = dataSnapshot.getValue(Customer.class);
                    String str_name = customer.customerName;
                    String str_contact = customer.contactPerson;
                    String str_phone = customer.telephone;
                    String str_location = customer.location;
                    int int_num = customer.numberOfEmployees;

                    customers = new ArrayList<>(Arrays.asList(customer));
                    Log.d("customers", customers.get(0).customerName);
                    Log.d("data", String.format("company:%s, contact:%s, phone:%s, location:%s, num:%d", str_name, str_contact, str_phone, str_location, int_num));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        // specify an adapter
        final RecyclerView.Adapter rAdapter = new MyAdapter(itemNames, itemContacters, itemPhones, itemLocations, itemNumsOfEmployees,
                itemDates, weather5days);
        recyclerView.setAdapter(rAdapter);

        // ItemTouchHelper
        ItemTouchHelper itemDecor = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        rAdapter.notifyItemMoved(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        itemNames.remove(fromPos);

                        rAdapter.notifyItemRemoved(fromPos);
                    }
                });
        itemDecor.attachToRecyclerView(recyclerView);
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