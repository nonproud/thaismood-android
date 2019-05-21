package com.nnspace.thaismoodandroid.HomeActivity.Graph;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.nnspace.thaismoodandroid.Database.EmotionDB;
import com.nnspace.thaismoodandroid.MoodObject;
import com.nnspace.thaismoodandroid.MoodType;
import com.nnspace.thaismoodandroid.MyThaiCalender;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphMonth extends Fragment {

    private Calendar currentCalender, calendar;
    private ArrayList<String> xLabel;
    private int today_dayOfWeek;
    private TextView dateDes;
    private ImageView leftbtn, rightbtn;
    private LineChart chart;
    private PieChart chart2;
    private ArrayList<MoodObject> moodlist;

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

        dateDes = getView().findViewById(R.id.graph_month_date_des);
        leftbtn = getView().findViewById(R.id.graph_month_left_btn);
        rightbtn = getView().findViewById(R.id.graph_month_right_btn);
        chart = getView().findViewById(R.id.graph_month_chart1);
        chart2 = getView().findViewById(R.id.graph_month_chart2);

        EmotionDB db = new EmotionDB(getActivity());
        String[] dr = getDateRange();
        moodlist = db.getMoodWeek(dr[0], dr[1]);

        dateDes.setText(getMonthString());
        setOnClickListener();
        setChart1();
        setChart2();

//        refreshGraph();
    }

    private void setChart1() {

        if(moodlist.size() == 0){
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

        List<Entry> dSet = new ArrayList<Entry>();
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
        dateDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private String[] getDateRange(){
        String[] r = new String[2];

        r[0] = calendar.get(Calendar.YEAR) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.getActualMinimum(Calendar.DATE);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        r[1] = calendar.get(Calendar.YEAR) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.getActualMaximum(Calendar.DATE);

        return r;
    }

    private String getMonthString(){

        return MyThaiCalender.getMonthOfYear(calendar.get(Calendar.MONTH)) + " " + (calendar.get(Calendar.YEAR) + 543);

    }

}
