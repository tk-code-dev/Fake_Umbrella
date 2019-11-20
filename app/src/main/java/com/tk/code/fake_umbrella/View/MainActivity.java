package com.tk.code.fake_umbrella.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tk.code.fake_umbrella.Model.Customer;
import com.tk.code.fake_umbrella.Model.Weather5days;
import com.tk.code.fake_umbrella.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "33a82b8c0da7233c25d00cf6f830cb9a";

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("customer/");

    public static List<Customer> itemCustomers = new ArrayList<>();
    final ArrayList<String> test = new ArrayList<>();

    public static List<String> descriptions = new ArrayList<>();
    TextView titleTV;
    TextView multiTV;
    String stringMerge = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTV = findViewById(R.id.titleTV);
        multiTV = findViewById(R.id.multiTV);
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
                Intent myIntent = new Intent(MainActivity.this, InputInfoActivity.class);
                myIntent.putExtra("INTENT_ADD", "BACK"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
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

        String stringBuilder;
        stringMerge = "";

        for (int i = 0; i < customers.size(); i++) {
            stringBuilder = "Company\t "+customers.get(i).customerName + "\n" +
                    "\t\t\tContact\t"+customers.get(i).contactPerson + "\t\t\t\t\t\t" +
                    customers.get(i).telephone + "\n" +
                    "\t\t\tLocation\t"+customers.get(i).location + "\n\n";
            stringMerge = stringMerge + stringBuilder;
            multiTV.setText(stringMerge);

            WeatherService service = retrofit.create(WeatherService.class);
            Call<WeatherResponse5days> call = service.getCurrentWeatherData(customers.get(i).location, "", AppId);
            call.enqueue(new Callback<WeatherResponse5days>() {

                @Override
                public void onResponse(@NonNull Call<WeatherResponse5days> call, @NonNull Response<WeatherResponse5days> response) {
                    if (response.code() == 200) {
                        WeatherResponse5days weatherResponse5days = response.body();
                        assert weatherResponse5days != null;

                        String strCity = weatherResponse5days.city.name;
                        String strWeather1 = weatherResponse5days.list.get(7).weather.get(0).description;
                        String strWeather2 = weatherResponse5days.list.get(15).weather.get(0).description;
                        String strWeather3 = weatherResponse5days.list.get(23).weather.get(0).description;
                        String strWeather4 = weatherResponse5days.list.get(31).weather.get(0).description;
                        String strWeather5 = weatherResponse5days.list.get(39).weather.get(0).description;
                        String date1 = weatherResponse5days.list.get(7).dt_txt;
                        String date2 = weatherResponse5days.list.get(15).dt_txt;
                        String date3 = weatherResponse5days.list.get(23).dt_txt;
                        String date4 = weatherResponse5days.list.get(31).dt_txt;
                        String date5 = weatherResponse5days.list.get(39).dt_txt;

                        String[] responseData = {strCity + "@" + strWeather1 + strWeather2 + strWeather3 + strWeather4 + strWeather5 + "@" + date1 + date2 + date3 + date4 + date5};
                        String joinedStringData = TextUtils.join(" ", responseData);
                        saveResponse(joinedStringData);
                        getSavedResponse();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WeatherResponse5days> call, @NonNull Throwable t) {
                    Log.d("Error", "Error !!");
                }
            });
        }
        return descriptions;
    }

    public void saveResponse(String res) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("weather.res", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(res);
            out.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSavedResponse() {
        String savedResponse;
        try {
            FileInputStream inputStream = openFileInput("weather.res");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedResponse = (String) in.readObject();
            in.close();
            inputStream.close();
            Log.d("loadData", "LOADED " + savedResponse);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}