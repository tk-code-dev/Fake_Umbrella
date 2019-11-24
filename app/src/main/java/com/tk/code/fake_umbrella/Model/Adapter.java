package com.tk.code.fake_umbrella.Model;

//AndroidX

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tk.code.fake_umbrella.R;
import com.tk.code.fake_umbrella.View.ModifyActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public static List<Customer> iCustomers;

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
        TextView date1V, date2V, date3V, date4V, date5V;
        ImageView weather1V, weather2V, weather3V, weather4V, weather5V;
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

            date1V.setVisibility(View.GONE);
            date2V.setVisibility(View.GONE);
            date3V.setVisibility(View.GONE);
            date4V.setVisibility(View.GONE);
            date5V.setVisibility(View.GONE);

            weather1V.setVisibility(View.GONE);
            weather2V.setVisibility(View.GONE);
            weather3V.setVisibility(View.GONE);
            weather4V.setVisibility(View.GONE);
            weather5V.setVisibility(View.GONE);

//            itemView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//
//                 }
//            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });

            //Long Press
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Customer modifyCustomer = new Customer(iCustomers.get(getAdapterPosition()).customerName,
                            iCustomers.get(getAdapterPosition()).contactPerson,
                            iCustomers.get(getAdapterPosition()).telephone,
                            iCustomers.get(getAdapterPosition()).location,
                            iCustomers.get(getAdapterPosition()).numberOfEmployees);
                    Intent intent = new Intent(context, ModifyActivity.class);
                    intent.putExtra("MODIFY", modifyCustomer);
                    context.startActivity(intent);
                    return false;
                }
            });
        }
    }

    // Provide a suitable constructor
    public Adapter(List<Customer> itemCustomers) {
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
//        holder.imageView.setImageResource(iImages.get(position));
        holder.nameView.setText(iCustomers.get(position).customerName);
        holder.contactView.setText(iCustomers.get(position).contactPerson);
        holder.phoneView.setText(iCustomers.get(position).telephone);
        holder.locationView.setText(iCustomers.get(position).location);
        holder.numView.setText(iCustomers.get(position).numberOfEmployees);
    }
    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iCustomers.size();
    }
}