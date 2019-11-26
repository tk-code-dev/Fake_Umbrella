package com.tk.code.fake_umbrella.View;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tk.code.fake_umbrella.Model.CustomerChart;
import com.tk.code.fake_umbrella.R;

public class ChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ImageView c1iv = findViewById(R.id.iv1);
        ImageView c2iv = findViewById(R.id.iv2);
        ImageView c3iv = findViewById(R.id.iv3);
        ImageView c4iv = findViewById(R.id.iv4);

        Intent intent = getIntent();
        String chartData = intent.getStringExtra(TableActivity.EXTRA_MESSAGE);
        Log.d("chartDataB", chartData);

        String[] fourCustomers = chartData.split("@");
        String[] customerChartData1 = fourCustomers[1].split(">");
        String[] customerChartData2 = fourCustomers[2].split(">");
        String[] customerChartData3 = fourCustomers[3].split(">");
        String[] customerChartData4 = fourCustomers[4].split(">");


        Log.d("formatArray", Arrays.toString(customerChartData1));

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry(customerChartData1[0], Integer.parseInt(customerChartData1[1])));
        data.add(new ValueDataEntry(customerChartData2[0], Integer.parseInt(customerChartData2[1])));
        data.add(new ValueDataEntry(customerChartData3[0], Integer.parseInt(customerChartData3[1])));
        data.add(new ValueDataEntry(customerChartData4[0], Integer.parseInt(customerChartData4[1])));

        Column column = cartesian.column(data);

        column.fill("function() {" +
                "            if ('true' == '" + customerChartData1[2] + "')" +
                "                return 'red';" +
                "            return 'green';" +
                "        }");

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        String[] rangeColors = new String[]{"#cef", "#ffffff"};
        cartesian.yGrid(0).palette(rangeColors);

        cartesian.animation(true);
        cartesian.title("Top 4 Customer");
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("Company");
        cartesian.yAxis(0).title("Number of Employees");

        anyChartView.setChart(cartesian);
        //  10d -> Rain icon  :   01d -> Sunny icon
        String w1 = customerChartData1[2].contains("true") ? "10d" : "01d";
        String w2 = customerChartData2[2].contains("true") ? "10d" : "01d";
        String w3 = customerChartData3[2].contains("true") ? "10d" : "01d";
        String w4 = customerChartData4[2].contains("true") ? "10d" : "01d";

        Picasso.get().load("http://openweathermap.org/img/wn/" + w1 + "@2x.png").resize(250, 250).into(c1iv);
        Picasso.get().load("http://openweathermap.org/img/wn/" + w2 + "@2x.png").resize(250, 250).into(c2iv);
        Picasso.get().load("http://openweathermap.org/img/wn/" + w3 + "@2x.png").resize(250, 250).into(c3iv);
        Picasso.get().load("http://openweathermap.org/img/wn/" + w4 + "@2x.png").resize(250, 250).into(c4iv);
    }
}