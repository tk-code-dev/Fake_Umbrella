package com.tk.code.fake_umbrella.Model;

//AndroidX

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tk.code.fake_umbrella.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Integer> iImages;
    private List<String> iNames;
    private List<String> iContacts;
    private List<String> iPhones;
    private List<String> iLocations;
    private List<Integer> iNumsOfEmployyes;
    private List<String> iEmails;
    private List<String> iDates;
    private List<Integer> iWeatherIcons;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // data item
        ImageView imageView;
        TextView nameView;
        TextView emailView;
        TextView contactView;
        TextView phoneView;
        TextView locationView;
        TextView numView;
        TextView date1V, date2V,date3V, date4V, date5V;
        ImageView weather1V,weather2V,weather3V,weather4V,weather5V;



        ViewHolder(View v) {
            super(v);
//            imageView = v.findViewById(R.id.image_view);
            nameView = v.findViewById(R.id.companyName_TV);
            contactView = v.findViewById(R.id.contactPerson_TV);
            phoneView = v.findViewById(R.id.phone_TV);
            locationView = v.findViewById(R.id.location_TV);
            numView = v.findViewById(R.id.numOfEmployees_TV);

            date1V = v.findViewById(R.id.date1_TV);
            date2V = v.findViewById(R.id.date2_TV);
            date3V = v.findViewById(R.id.date3_TV);
            date4V = v.findViewById(R.id.date4_TV);
            date5V = v.findViewById(R.id.date5_TV);

            weather1V = v.findViewById(R.id.weatherIV1);
            weather2V = v.findViewById(R.id.weatherIV2);
            weather3V = v.findViewById(R.id.weatherIV3);
            weather4V = v.findViewById(R.id.weatherIV4);
            weather5V = v.findViewById(R.id.weatherIV5);

//            emailView = v.findViewById(R.id.email_view);
        }
    }

    // Provide a suitable constructor
    public MyAdapter(List<String> itemNames, List<String> itemContacts, List<String> itemPhones,
                     List<String> itemLocations, List<Integer> itemNumsOfEmployees,
                     List<String> itemDates, List<Integer> itemWeatherIcons) {
        this.iNames = itemNames;
        this.iContacts = itemContacts;
        this.iPhones = itemPhones;
        this.iLocations = itemLocations;
        this.iNumsOfEmployyes = itemNumsOfEmployees;
        this.iDates = itemDates;
        this.iWeatherIcons = itemWeatherIcons;

    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_cell, parent, false);

        // view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from dataset at this position
        // - replace the contents of the view with that element
//        holder.imageView.setImageResource(iImages.get(position));
        holder.nameView.setText(iNames.get(position));
        holder.contactView.setText(iContacts.get(position));
        holder.phoneView.setText(iPhones.get(position));
        holder.locationView.setText(iLocations.get(position));
        holder.numView.setText(Integer.toString(iNumsOfEmployyes.get(position)));
        holder.date1V.setText(iDates.get(0));
        holder.date2V.setText(iDates.get(1));
        holder.date3V.setText(iDates.get(2));
        holder.date4V.setText(iDates.get(3));
        holder.date5V.setText(iDates.get(4));
        holder.weather1V.setImageResource(iWeatherIcons.get(0));
        holder.weather2V.setImageResource(iWeatherIcons.get(1));
        holder.weather3V.setImageResource(iWeatherIcons.get(2));
        holder.weather4V.setImageResource(iWeatherIcons.get(3));
        holder.weather5V.setImageResource(iWeatherIcons.get(4));
        Log.d("value",iWeatherIcons.get(0).toString());


    }

    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iPhones.size();
    }
}