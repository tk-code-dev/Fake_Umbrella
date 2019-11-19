package com.tk.code.fake_umbrella.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tk.code.fake_umbrella.Model.Adapter;
import com.tk.code.fake_umbrella.Model.Customer;
import com.tk.code.fake_umbrella.Model.CustomerWeather;
import com.tk.code.fake_umbrella.Model.SampleWeatherData;
import com.tk.code.fake_umbrella.Model.Weather;
import com.tk.code.fake_umbrella.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TableActivity extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "33a82b8c0da7233c25d00cf6f830cb9a";

    //    SampleData sampleData = new SampleData();
    SampleWeatherData sampleWeatherData = new SampleWeatherData();

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("customer/");

    public static List<Customer> itemCustomers = new ArrayList<>();
    RecyclerView recyclerView;

    final List<Integer> weather5days = new ArrayList<>(Arrays.asList(sampleWeatherData.weatherIcons));
    final ArrayList<String> test = new ArrayList<>();

    static int countOfRain = 0;
    static List<String> descriptions = new ArrayList<>();
    static List<String> dates = new ArrayList<>();
    static List<String> weatherIcons = new ArrayList<>();
    static List<Weather> weathers = new ArrayList<>();
    final List<CustomerWeather> customerWeathers = new ArrayList<>();
    List<Weather> returnWeatherDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

//        if (isNetwork(getApplicationContext())) {
//            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
//        }

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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView
                );

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.chart: {
                                return true;
                            }
                            case R.id.list: {
                                onResume();
                                return true;
                            }
                        }
                        return false;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Customer customer = dataSnapshot.getValue(Customer.class);
                    itemCustomers.add(customer);
                    String str_location = customer.location;

                    Log.d("data", String.format("location:%s", str_location));
                    test.add(str_location);

                    getCurrentData(itemCustomers);
                }
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

    List<String> getCurrentData(List<Customer> customers) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        for (int i = 0; i < customers.size(); i++) {
            WeatherService service = retrofit.create(WeatherService.class);
            Call<WeatherResponse5days> call = service.getCurrentWeatherData(customers.get(i).location, "", AppId);
            call.enqueue(new Callback<WeatherResponse5days>() {
                List<List<String>> weather5icons = new ArrayList<List<String>>();

                @Override
                public void onResponse(@NonNull Call<WeatherResponse5days> call, @NonNull Response<WeatherResponse5days> response) {
                    if (response.code() == 200) {
                        WeatherResponse5days weatherResponse5days = response.body();
                        assert weatherResponse5days != null;

                        for (int i = 0; i <= 39; i++) {
                            descriptions.add(weatherResponse5days.list.get(i).weather.get(0).description);
                        }
                        for (int i = 0; i <= 39; i++) {
                            dates.add(weatherResponse5days.list.get(i).dt_txt);
                        }
                        for (int i = 0; i <= 39; i++) {
                            weatherIcons.add("http://openweathermap.org/img/w/" + weatherResponse5days.list.get(i).weather.get(0).icon + ".png");
                        }
                        weather5icons.add(weatherIcons);

                    }
                    final RecyclerView.Adapter rAdapter = new Adapter(itemCustomers,
                            dates, weather5icons);
                    recyclerView.setAdapter(rAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<WeatherResponse5days> call, @NonNull Throwable t) {
                    Log.d("Error", "Error !!");
                }
            });
        }
        return descriptions;
    }

    public boolean isNetwork(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}