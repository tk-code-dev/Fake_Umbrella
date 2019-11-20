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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.tk.code.fake_umbrella.Model.CustomerWeather;
import com.tk.code.fake_umbrella.Model.SampleWeatherData;
import com.tk.code.fake_umbrella.Model.Weather;
import com.tk.code.fake_umbrella.Model.Weather5days;
import com.tk.code.fake_umbrella.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "33a82b8c0da7233c25d00cf6f830cb9a";

    //    SampleData sampleData = new SampleData();
    SampleWeatherData sampleWeatherData = new SampleWeatherData();

    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("customer/");

    public static List<Customer> itemCustomers = new ArrayList<>();

    final List<Integer> weather5days = new ArrayList<>(Arrays.asList(sampleWeatherData.weatherIcons));
    final ArrayList<String> test = new ArrayList<>();

    static int countOfRain = 0;
    public static List<String> descriptions = new ArrayList<>();
    public static List<String> dates = new ArrayList<>();
    public static List<String> weatherIcons = new ArrayList<>();
    public static List<Weather> weathers = new ArrayList<>();
    final List<CustomerWeather> customerWeathers = new ArrayList<>();
    List<Weather> returnWeatherDatas = new ArrayList<>();
    TextView titleTV;
    TextView multiTV;
    String stringMerge = "";
    static String reformatDate1, reformatDate2, reformatDate3, reformatDate4, reformatDate5;
    public static List<List<String>> weather5icons = new ArrayList<List<String>>();
    public static List<Weather5days> weather5daysList = new ArrayList<Weather5days>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        titleTV = findViewById(R.id.titleTV);
        multiTV = findViewById(R.id.multiTV);

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
            stringBuilder = customers.get(i).customerName + "\n" +
                    customers.get(i).contactPerson + "\n" +
                    customers.get(i).telephone + "\n\n";
            stringMerge = stringMerge + stringBuilder;

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

                        String[] responseDatas = {strCity + "@" + strWeather1 + strWeather2 + strWeather3 + strWeather4 + strWeather5 + "@" + date1 + date2 + date3 + date4 + date5};
                        String joinedStringData = TextUtils.join(" ", responseDatas);


                        Log.d("response", joinedStringData);
                        saveResponse(joinedStringData);
                        getSavedResponse();

//                        descriptions.add(weatherResponse5days.list.get(0).weather.get(i).main);
//                            weatherIcons.add("http://openweathermap.org/img/w/" + weatherResponse5days.list.get(0).weather.get(i).icon + ".png");
//                            dates.add(weatherResponse5days.list.get(i).dt_txt);
//                            SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
//                            SimpleDateFormat myFormat = new SimpleDateFormat("dd");
//
//                            try {
//                                String reformatDate = myFormat.format(fromUser.parse(date));
//                                dates.add(reformatDate);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

//                            Log.d("Check", weatherIcons.get(i));


                    }

//                        weather5icons.add(weatherIcons);
//                        getSavedIcons();
//                        getSavedDates();
//                        getSavedDescriptions();


//                    display(weather5icons);
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
        String savedResponse = null;

        try {
            FileInputStream inputStream = openFileInput("weather.res");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedResponse = (String) in.readObject();
            in.close();
            inputStream.close();

            Log.d("loadDescriptionsData", "LOADED " + savedResponse);


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//    public void saveResponse(String res) {
//        try {

//            FileOutputStream fos =
//                    new FileOutputStream("weather.response");

//            byte sbyte[] = "Java".getBytes(StandardCharsets.UTF_8);

//            for(int i = 0; i < sbyte.length; i++){
//                fos.write(sbyte[i]);
//            }

//            //ファイルに書き込む
//            fos.flush();
//
//            //ファイルをクローズする
//            fos.close();
//            FileOutputStream fileOutputStream = openFileOutput("weather.response", Context.MODE_PRIVATE);
//            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
//            out.writeObject(res);
//            out.close();
//            fileOutputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void getSavedResponse() {
//        WeatherResponse5days savedResponse = null;
//
//        try {
//            FileInputStream inputStream = openFileInput("weather.response");
//            ObjectInputStream in = new ObjectInputStream(inputStream);
//            savedResponse = (WeatherResponse5days) in.readObject();
//            in.close();
//            inputStream.close();
//
//            Log.d("loadDescriptionsData", savedResponse.city.name);
//
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public void saveDescriptions(List<String> descriptions) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("weather.descriptions", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(descriptions);
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSavedDescriptions() {
        List<String> savedDescriptions = null;

        try {
            FileInputStream inputStream = openFileInput("weather.descriptions");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedDescriptions = (List<String>) in.readObject();
            in.close();
            inputStream.close();

            Log.d("loadDescriptionsData", savedDescriptions.get(0));


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveDates(List<String> icons) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("weather.dates", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(icons);
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSavedDates() {
        List<String> savedDates = null;

        try {
            FileInputStream inputStream = openFileInput("weather.dates");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedDates = (List<String>) in.readObject();
            in.close();
            inputStream.close();

            Log.d("loadDatesData", savedDates.get(0));


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveIcons(List<String> icons) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("weather.icons", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(icons);
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSavedIcons() {
        List<String> savedIcons = null;

        try {
            FileInputStream inputStream = openFileInput("weather.icons");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedIcons = (List<String>) in.readObject();
            in.close();
            inputStream.close();

            Log.d("loadIconsData", savedIcons.get(0));


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private List<Weather5days> getSavedArrayList() {
//        List<Weather5days> savedList = null;
//
//        try {
//            FileInputStream inputStream = openFileInput("weather.icons");
//            ObjectInputStream in = new ObjectInputStream(inputStream);
//            savedList = (List<Weather5days>) in.readObject();
//            in.close();
//            inputStream.close();
//
//                Log.d("loadData", savedList.get(0).descriptions5days.get(0));
//
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return savedList;
//    }


//    public void saveIcons(List<Weather5days> icons) {
//        try {
//            FileOutputStream fileOutputStream = openFileOutput("weather.icons", Context.MODE_PRIVATE);
//            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
//            out.writeObject(icons);
//            out.close();
//            fileOutputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void display(List<List<String>> icons) {
        for (int i = 0; i < icons.size(); i++)
            multiTV.setText(stringMerge + icons.get(i).get(39));
    }
}