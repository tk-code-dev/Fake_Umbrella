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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tk.code.fake_umbrella.Model.Adapter;
import com.tk.code.fake_umbrella.Model.Customer;
import com.tk.code.fake_umbrella.Model.CustomerListWeather;
import com.tk.code.fake_umbrella.Model.CustomerWeather;
import com.tk.code.fake_umbrella.Model.MyAdapter;
import com.tk.code.fake_umbrella.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static android.view.View.*;
import static com.tk.code.fake_umbrella.View.GoogleSignInActivity.mAuth;
import static com.tk.code.fake_umbrella.View.GoogleSignInActivity.mGoogleSignInClient;


public class TableActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.tk.code.fake_umbrella.View.MESSAGE";

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "33a82b8c0da7233c25d00cf6f830cb9a";

    //    SampleData sampleData = new SampleData();
    //    SampleWeatherData sampleWeatherData = new SampleWeatherData();

    // Get a reference to our posts
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("customer/");

    public static List<Customer> itemCustomers = new ArrayList<>();
    RecyclerView recyclerView;

    static List<CustomerWeather> customerWeathers = new ArrayList<>();
    static List<Object> locations = new ArrayList<>();
    static String reformatDate1, reformatDate2, reformatDate3, reformatDate4, reformatDate5;
    public static List<CustomerListWeather> customerListWeathers = new ArrayList<>();
    String chartCompanyName, chartWeather1, chartWeather2, chartWeather3, chartWeather4, chartWeather5;
    Integer chartNum;
    Boolean isRain;
    String chartData = "";

    Button btnWeather, btnChart, btnList, btnBack;

    public RecyclerView.Adapter rAdapter;

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

        //  Fetch current weather
        btnWeather = findViewById(R.id.btnWeather);
        btnWeather.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                locations = locations.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //  Distinct City
                List<String> locationList = new ArrayList<>(locations.size());
                for (Object object : locations) {
                    locationList.add(Objects.toString(object, null));
                }
                Log.d("distinctLocation", Arrays.toString(locationList.toArray()));

                int i = 0;
                while (i < locationList.size()) {
                    getCurrentData(locationList.get(i));
                    i++;
                }
                btnBack.setVisibility(VISIBLE);
                btnList.setVisibility(VISIBLE);
                btnChart.setVisibility(VISIBLE);
                btnWeather.setVisibility(GONE);
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onResume();
            }
        });

        btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                customerListWeathers.clear();
                customerListWeathers = makeFormatData(itemCustomers);
                final RecyclerView.Adapter listAdapter = new MyAdapter(customerListWeathers);
                recyclerView.setAdapter(listAdapter);
            }
        });

        btnChart = findViewById(R.id.btnChart);
        btnChart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Collections.sort(itemCustomers, new CompareNumEmployee());
                for (Customer c : itemCustomers) {
                    System.out.println(c.numberOfEmployees + ", " + c.customerName);
                }
                customerListWeathers.clear();
                customerListWeathers = makeFormatData(itemCustomers);
                chartData = "";
                for (int i = 0; i < customerListWeathers.size(); i++) {
                    chartCompanyName = customerListWeathers.get(i).customer.customerName;
                    chartNum = customerListWeathers.get(i).customer.numberOfEmployees;
                    chartWeather1 = customerListWeathers.get(i).description[0];
                    chartWeather2 = customerListWeathers.get(i).description[1];
                    chartWeather3 = customerListWeathers.get(i).description[2];
                    chartWeather4 = customerListWeathers.get(i).description[3];
                    chartWeather5 = customerListWeathers.get(i).description[4];
                    isRain = chartWeather1.contains("rain") || chartWeather2.contains("rain") || chartWeather3.contains("rain") || chartWeather4.contains("rain");

                    chartData += "@" + chartCompanyName + ">" + chartNum.toString() + ">" + isRain.toString();
                }
                Log.d("chartData", chartData);
                Intent intent = new Intent(getApplication(), ChartActivity.class);
                intent.putExtra(EXTRA_MESSAGE, chartData);
                startActivity(intent);
            }
        });
        btnBack.setVisibility(GONE);
        btnList.setVisibility(GONE);
        btnChart.setVisibility(GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnWeather.setVisibility(VISIBLE);
        btnBack.setVisibility(GONE);
        btnList.setVisibility(GONE);
        btnChart.setVisibility(GONE);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemCustomers.clear();
                locations.clear();

                for (final DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Customer customer = dataSnapshot.getValue(Customer.class);
                    itemCustomers.add(customer);
                    assert customer != null;
                    String str_location = customer.location;
                    Log.d("data", String.format("location:%s", str_location));
                    locations.add(str_location);
                    Log.d("locations", Arrays.toString(locations.toArray()));

                    Log.d("unique key", snapshot.getKey());

                    rAdapter = new Adapter(itemCustomers);
                    recyclerView.setAdapter(rAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


//        ItemTouchHelper itemDecor = new ItemTouchHelper(
//                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
//                        ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
//                    @Override
//                    public boolean onMove(@NonNull RecyclerView recyclerView,
//                                          @NonNull RecyclerView.ViewHolder viewHolder,
//                                          @NonNull RecyclerView.ViewHolder target) {
//                        final int fromPos = viewHolder.getAdapterPosition();
//                        final int toPos = target.getAdapterPosition();
//                        rAdapter.notifyItemMoved(fromPos, toPos);
//                        return true;
//                    }

//                    @Override
//                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
//                                         int direction) {
//                        int fromPos = viewHolder.getAdapterPosition();
//                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//
//                        Query deleteQuery = ref.child("customer").orderByChild("customerName").equalTo(itemCustomers.get(fromPos).customerName);
//                        itemCustomers.remove(fromPos);
//                        rAdapter.notifyItemRemoved(fromPos);
//
//                        deleteQuery.getRef().removeValue();
//
//                    }
//                });

//        itemDecor.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            // Handle item selection ADD
        if (item.getItemId() == R.id.action_add) {
            Intent myIntent = new Intent(TableActivity.this, InputInfoActivity.class);
            myIntent.putExtra("INTENT_ADD", "BACK"); //Optional parameters
            TableActivity.this.startActivity(myIntent);
            return true;

            // Handle item selection SIGN OUT
        } else if (item.getItemId() == R.id.sign_out) {
            if (mAuth != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                finish();
                Toast.makeText(this, user.getDisplayName() + " Sign out!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You aren't login Yet!", Toast.LENGTH_SHORT).show();
            }
        }}
        finish();

        return super.onOptionsItemSelected(item);
    }

    void getCurrentData(String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Log.d("weather_location", city);

        Call<WeatherResponse5days> call = service.getCurrentWeatherData(city, "", AppId);
        call.enqueue(new Callback<WeatherResponse5days>() {

            @Override
            public void onResponse(@NonNull Call<WeatherResponse5days> call, @NonNull Response<WeatherResponse5days> response) {
                if (response.code() == 200) {
                    WeatherResponse5days weatherResponse5days = response.body();
                    assert weatherResponse5days != null;
                    Log.d("weather", "fetch weather");

                    String strCity = weatherResponse5days.city.name;
                    String strWeather1 = weatherResponse5days.list.get(7).weather.get(0).description;
                    String strWeather2 = weatherResponse5days.list.get(15).weather.get(0).description;
                    String strWeather3 = weatherResponse5days.list.get(23).weather.get(0).description;
                    String strWeather4 = weatherResponse5days.list.get(31).weather.get(0).description;
                    String strWeather5 = weatherResponse5days.list.get(39).weather.get(0).description;
                    String strIcon1 = weatherResponse5days.list.get(7).weather.get(0).icon;
                    String strIcon2 = weatherResponse5days.list.get(15).weather.get(0).icon;
                    String strIcon3 = weatherResponse5days.list.get(23).weather.get(0).icon;
                    String strIcon4 = weatherResponse5days.list.get(31).weather.get(0).icon;
                    String strIcon5 = weatherResponse5days.list.get(39).weather.get(0).icon;
                    String date1 = weatherResponse5days.list.get(7).dt_txt;
                    String date2 = weatherResponse5days.list.get(15).dt_txt;
                    String date3 = weatherResponse5days.list.get(23).dt_txt;
                    String date4 = weatherResponse5days.list.get(31).dt_txt;
                    String date5 = weatherResponse5days.list.get(39).dt_txt;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("dd");
                    try {
                        reformatDate1 = myFormat.format(fromUser.parse(date1));
                        reformatDate2 = myFormat.format(fromUser.parse(date2));
                        reformatDate3 = myFormat.format(fromUser.parse(date3));
                        reformatDate4 = myFormat.format(fromUser.parse(date4));
                        reformatDate5 = myFormat.format(fromUser.parse(date5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String[] responseData = {strCity + "@" + strWeather1 + ">" + strWeather2 + ">" + strWeather3 + ">" + strWeather4 + ">" + strWeather5 + "@" +
                            strIcon1 + ">" + strIcon2 + ">" + strIcon3 + ">" + strIcon4 + ">" + strIcon5 + ">" + "@" +
                            reformatDate1 + ">" + reformatDate2 + ">" + reformatDate3 + ">" + reformatDate4 + ">" + reformatDate5};
                    String joinedStringData = TextUtils.join(" ", responseData);
                    Log.d("joinedData", joinedStringData);
                    saveResponse(strCity, joinedStringData);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse5days> call, @NonNull Throwable t) {
                Log.d("Error", "Error !!");
            }
        });
    }

    void saveResponse(String city, String res) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("weather." + city.toLowerCase(), Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(res);
            out.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getSavedResponse(String city) {
        Log.d("CITY", city);
        String savedResponse;
        try {
            FileInputStream inputStream = openFileInput("weather." + city.toLowerCase());
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedResponse = (String) in.readObject();
            in.close();
            inputStream.close();
            Log.d("loadData", "LOADED " + savedResponse);
            return savedResponse;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "load error";
    }

    List<CustomerListWeather> makeFormatData(List<Customer> customers) {

        for (int i = 0; i < customers.size(); i++) {
            Log.d("Check location", customers.get(i).location);
            customerWeathers.add(new CustomerWeather(customers.get(i),
                    getSavedResponse(customers.get(i).location)));

            String res = customerWeathers.get(i).locationWeather;
            String[] resCity = res.split("@");
            Log.d("formatArray", Arrays.toString(resCity));

            String[] resDescriptions = resCity[1].split(">");
            String[] resIcons = resCity[2].split(">");
            String[] resDate = resCity[3].split(">");

            customerListWeathers.add(new CustomerListWeather(customers.get(i), resDescriptions, resDate, resIcons));

            Log.d("FinalData", customerListWeathers.get(i).customer.customerName + " " +
                    customerListWeathers.get(i).customer.contactPerson + " " +
                    customerListWeathers.get(i).customer.telephone + " " +
                    customerListWeathers.get(i).description[0] + " " +
                    customerListWeathers.get(i).description[1] + " " +
                    customerListWeathers.get(i).description[2] + " " +
                    customerListWeathers.get(i).description[3] + " " +
                    customerListWeathers.get(i).description[4]);
        }
        return customerListWeathers;
    }

    public class CompareNumEmployee implements Comparator<Customer> {
        public int compare(Customer c1, Customer c2) {
            if (c1.getNumberOfEmployees() < c2.getNumberOfEmployees()) {
                return 1;
            } else if (c1.getNumberOfEmployees() > c2.getNumberOfEmployees()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

//    public class CompareNumEmployeeWeather implements Comparator<CustomerListWeather> {
//        @Override
//        public int compare(CustomerListWeather c1, CustomerListWeather c2) {
//            if (c1.getCustomer().getNumberOfEmployees() < c2.getCustomer().getNumberOfEmployees()) {
//                return 1;
//            } else if (c1.getCustomer().getNumberOfEmployees() > c2.getCustomer().getNumberOfEmployees()) {
//                return -1;
//            } else {
//                return 0;
//            }
//        }
//    }
}