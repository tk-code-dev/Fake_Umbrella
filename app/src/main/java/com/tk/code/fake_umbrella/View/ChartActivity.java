package com.tk.code.fake_umbrella.View;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.SweepGradient;
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
import com.tk.code.fake_umbrella.R;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

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
                "            if ('true' == '"+ customerChartData1[2] +"')" +
                "                return 'yellow';" +
                "            return 'blue';" +
                "        }");

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        String[] rangeColors = new String[]{"#87cebb", "#ffffff"};
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
    }
}