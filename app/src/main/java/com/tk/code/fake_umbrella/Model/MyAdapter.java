package com.tk.code.fake_umbrella.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tk.code.fake_umbrella.R;
import com.tk.code.fake_umbrella.View.CallActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<CustomerListWeather> iCustomers;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // data item
        TextView nameView;
        TextView contactView;
        TextView phoneView;
        TextView locationView;
        TextView numView;
        TextView date1V, date2V,date3V, date4V, date5V;
        ImageView weather1V,weather2V,weather3V,weather4V,weather5V;
        Context context = itemView.getContext();

        ViewHolder(View v) {
            super(v);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // change format (xxx)-xxx-xxxx to xxxxxxxxxx
                    String text = phoneView.getText().toString();
                    String digits = text.replaceAll("[^0-9]", "");
//                    Toast.makeText(context,digits,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CallActivity.class);
                    intent.putExtra("CALLNUMBER",digits);
                    context.startActivity(intent);
                }
            });
        }
    }

    // Provide a suitable constructor
    public MyAdapter(List<CustomerListWeather> itemCustomers) {
        this.iCustomers = itemCustomers;
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
        holder.nameView.setText(iCustomers.get(position).customer.customerName);
        holder.contactView.setText(iCustomers.get(position).customer.contactPerson);
        holder.phoneView.setText((String.valueOf(iCustomers.get(position).customer.telephone).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3")));
        holder.locationView.setText(iCustomers.get(position).customer.location.toUpperCase());
        holder.numView.setText(iCustomers.get(position).customer.numberOfEmployees+"");

        holder.date1V.setText(iCustomers.get(position).date[0]);
        holder.date2V.setText(iCustomers.get(position).date[1]);
        holder.date3V.setText(iCustomers.get(position).date[2]);
        holder.date4V.setText(iCustomers.get(position).date[3]);
        holder.date5V.setText(iCustomers.get(position).date[4]);
            Picasso.get().load("http://openweathermap.org/img/w/"+iCustomers.get(position).icon[0]+ ".png").resize(250, 250).into(holder.weather1V);
            Picasso.get().load("http://openweathermap.org/img/w/"+iCustomers.get(position).icon[1]+ ".png").resize(250, 250).into(holder.weather2V);
            Picasso.get().load("http://openweathermap.org/img/w/"+iCustomers.get(position).icon[2]+ ".png").resize(250, 250).into(holder.weather3V);
            Picasso.get().load("http://openweathermap.org/img/w/"+iCustomers.get(position).icon[3]+ ".png").resize(250, 250).into(holder.weather4V);
            Picasso.get().load("http://openweathermap.org/img/w/"+iCustomers.get(position).icon[4]+ ".png").resize(250, 250).into(holder.weather5V);
    }
    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iCustomers.size();
    }
}