package com.nnspace.thaismoodandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.Models.MoodObject;
import com.nnspace.thaismoodandroid.Models.MoodType;
import com.nnspace.thaismoodandroid.MyCalender;
import com.nnspace.thaismoodandroid.R;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphMonth extends Fragment {

    private Calendar currentCalender, calendar;
    private ArrayList<String> xLabel;
    private int today_dayOfWeek;
    private TextView dateDes;
    private LineChart chart;
    private PieChart chart2;
    private ArrayList<MoodObject> moodlist;
    private Calendar today = Calendar.getInstance(), currentDay = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentCalender = Calendar.getInstance();
        calendar = Calendar.getInstance();

        return inflater.inflate(R.layout.fragment_graph_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        dateDes = getView().findViewById(R.id.graph_year_date_des);
        chart = getView().findViewById(R.id.graph_month_chart1);
        chart2 = getView().findViewById(R.id.graph_month_chart2);

        dateDes.setText(getThaiMonthString());
        setOnClickListener();
        setChart1();
        setChart2();

    }

    private void setChart1() {

        ThaisMoodDB db = new ThaisMoodDB(getActivity());
        String[] dr = getDateRange();
        moodlist = db.getMoodRange(dr[0], dr[1]);

        List<Entry> dSet = new ArrayList<Entry>();

        if(moodlist.size() == 0){
            dSet.clear();
            chart.invalidate();
            chart.clear();
            return;
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawZeroLine(true);
        yAxis.setAxisMaxValue(3);
        yAxis.setAxisMinValue(-3);
        yAxis.setZeroLineColor(getResources().getColor(R.color.mood_green));
        yAxis.setDrawLabels(true);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);


        for(int i=0; i<moodlist.size(); i++){
            if(moodlist.get(i).getMoodType() == MoodType.RED || moodlist.get(i).getMoodType() == MoodType.YELLOW){
                dSet.add(new Entry(i+1, moodlist.get(i).getLevel()));
                continue;
            }
            dSet.add(new Entry(i+1, moodlist.get(i).getLevel()*(-1)));
        }


        LineDataSet setmood = new LineDataSet(dSet, "Mood");
        setmood.setLineWidth(5f);
        setmood.setMode(LineDataSet.Mode.CUBIC_BEZIER);



        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setmood);

        LineData data = new LineData(dataSets);

        chart.setData(data);
        chart.invalidate(); // refresh
        chart.animateY(1000, Easing.EasingOption.EaseInBack);
    }

    private void setChart2(){

    }

    private void setOnClickListener() {

        final MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(),
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) { // on date set
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.YEAR, selectedYear);
                        dateDes.setText(getThaiMonthString());
                        setChart1();
                        setChart2();
                    }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        dateDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(2015)
                        .setActivatedYear(calendar.get(Calendar.YEAR))
                        .setActivatedMonth(calendar.get(Calendar.MONTH))
                        .setMonthAndYearRange(Calendar.JANUARY, currentCalender.get(Calendar.MONTH), 2015, currentCalender.get(Calendar.YEAR))
                        .setMaxYear(currentCalender.get(Calendar.YEAR))
                        .setTitle("เลือกเดือน")
                        .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                        .build()
                        .show();
            }
        });

    }

    private String[] getDateRange(){
        String[] r = new String[2];

        r[0] = calendar.get(Calendar.YEAR) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        System.out.println("month s-date: " + r[0]);

        r[1] = calendar.get(Calendar.YEAR) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("month e-date: " + r[1]);

        return r;
    }

    private String getThaiMonthString(){

        return MyCalender.getMonthOfYear(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.YEAR);

    }

}
