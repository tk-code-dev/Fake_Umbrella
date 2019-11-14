package com.tk.code.fake_umbrella.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tk.code.fake_umbrella.Model.MyAdapter;
import com.tk.code.fake_umbrella.Model.SampleData;
import com.tk.code.fake_umbrella.Model.SampleWeatherData;
import com.tk.code.fake_umbrella.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TableActivity extends AppCompatActivity {

    SampleData sampleData = new SampleData();
    SampleWeatherData sampleWeatherData = new SampleWeatherData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        // use this setting to improve performance
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        // copy to ArrayList
        final  List<String> itemNames = new ArrayList<String>(Arrays.asList(sampleData.names));
//        List<Integer> itemImages = new ArrayList<Integer>(Arrays.asList(sampleData.photos));
        final  List<String> itemContacters = new ArrayList<String>(Arrays.asList(sampleData.contacts));
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
}