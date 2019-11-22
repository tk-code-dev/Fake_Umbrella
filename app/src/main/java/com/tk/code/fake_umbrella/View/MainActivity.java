package com.tk.code.fake_umbrella.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.tk.code.fake_umbrella.R;

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
    String reformatDate1, reformatDate2, reformatDate3, reformatDate4, reformatDate5;

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
//                String res = getSavedResponse();
//                String[] resCity = res.split("@");
//                Log.d("formatArray", Arrays.toString(resCity));

//                String[] resDescriptions = resCity[1].split(">");
//                String[] resIcons = resCity[2].split(">");
//                String[] resDate = resCity[3].split(">");
//
//                Log.d("Des", Arrays.toString(resDescriptions));
//                Log.d("Date", Arrays.toString(resDate));
//                Log.d("Icon", Arrays.toString(resIcons));




                Intent myIntent = new Intent(MainActivity.this, InputInfoActivity.class);
                myIntent.putExtra("INTENT_ADD", "BACK"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    List<String> getCurrentData(List<Customer> customers) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        String stringBuilder;
//        stringMerge = "";
//
//        for (int i = 0; i < customers.size(); i++) {
//            stringBuilder = "Company\t " + customers.get(i).customerName + "\n" +
//                    "\t\t\tContact\t" + customers.get(i).contactPerson + "\t\t\t\t\t\t" +
//                    customers.get(i).telephone + "\n" +
//                    "\t\t\tLocation\t" + customers.get(i).location + "\n\n";
//            stringMerge = stringMerge + stringBuilder;
//            multiTV.setText(stringMerge);
//
//            WeatherService service = retrofit.create(WeatherService.class);
//            Call<WeatherResponse5days> call = service.getCurrentWeatherData(customers.get(i).location, "", AppId);
//            call.enqueue(new Callback<WeatherResponse5days>() {
//
//                @Override
//                public void onResponse(@NonNull Call<WeatherResponse5days> call, @NonNull Response<WeatherResponse5days> response) {
//                    if (response.code() == 200) {
//                        WeatherResponse5days weatherResponse5days = response.body();
//                        assert weatherResponse5days != null;
//
//                        String strCity = weatherResponse5days.city.name;
//                        String strWeather1 = weatherResponse5days.list.get(7).weather.get(0).description;
//                        String strWeather2 = weatherResponse5days.list.get(15).weather.get(0).description;
//                        String strWeather3 = weatherResponse5days.list.get(23).weather.get(0).description;
//                        String strWeather4 = weatherResponse5days.list.get(31).weather.get(0).description;
//                        String strWeather5 = weatherResponse5days.list.get(39).weather.get(0).description;
//                        String strIcon1 = weatherResponse5days.list.get(7).weather.get(0).icon;
//                        String strIcon2 = weatherResponse5days.list.get(15).weather.get(0).icon;
//                        String strIcon3 = weatherResponse5days.list.get(23).weather.get(0).icon;
//                        String strIcon4 = weatherResponse5days.list.get(31).weather.get(0).icon;
//                        String strIcon5 = weatherResponse5days.list.get(39).weather.get(0).icon;
//                        String date1 = weatherResponse5days.list.get(7).dt_txt;
//                        String date2 = weatherResponse5days.list.get(15).dt_txt;
//                        String date3 = weatherResponse5days.list.get(23).dt_txt;
//                        String date4 = weatherResponse5days.list.get(31).dt_txt;
//                        String date5 = weatherResponse5days.list.get(39).dt_txt;
//                        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//                        SimpleDateFormat myFormat = new SimpleDateFormat("dd");
//                        try {
//                            reformatDate1 = myFormat.format(fromUser.parse(date1));
//                            reformatDate2 = myFormat.format(fromUser.parse(date2));
//                            reformatDate3 = myFormat.format(fromUser.parse(date3));
//                            reformatDate4 = myFormat.format(fromUser.parse(date4));
//                            reformatDate5 = myFormat.format(fromUser.parse(date5));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        String[] responseData = {strCity + "@" + strWeather1 + ">" + strWeather2 + ">" + strWeather3 + ">" + strWeather4 + ">" + strWeather5 + "@" +
//                                strIcon1 + ">" + strIcon2 + ">" + strIcon3 + ">" + strIcon4 + ">" + strIcon5 + ">" + "@" +
//                                reformatDate1 + ">" + reformatDate2 + ">" + reformatDate3 + ">" + reformatDate4 + ">" + reformatDate5};
//                        String joinedStringData = TextUtils.join(" ", responseData);
//
//                        saveResponse(strCity, joinedStringData);
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<WeatherResponse5days> call, @NonNull Throwable t) {
//                    Log.d("Error", "Error !!");
//                }
//            });
//        }
//        return descriptions;
//    }

//    public void saveResponse(String city, String res) {
//        try {
//            FileOutputStream fileOutputStream = openFileOutput("weather." + city.toLowerCase(), Context.MODE_PRIVATE);
//            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
//            out.writeObject(res);
//            out.close();
//            fileOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private String getSavedResponse() {
//        String savedResponse;
//        try {
//            FileInputStream inputStream = openFileInput("weather.Montreal");
//            ObjectInputStream in = new ObjectInputStream(inputStream);
//            savedResponse = (String) in.readObject();
//            in.close();
//            inputStream.close();
//            Log.d("loadData", "LOADED " + savedResponse);
//            return savedResponse;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "load error";
//    }
}