package com.nnspace.thaismoodandroid.HomeActivity.Graph;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.nnspace.thaismoodandroid.HomeActivity.List.MoodObject;
import com.nnspace.thaismoodandroid.MoodType;
import com.nnspace.thaismoodandroid.MyThaiCalender;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphWeek extends Fragment {

    private Calendar currentCalender = Calendar.getInstance(), calendar = Calendar.getInstance(),
            tempCalendar = Calendar.getInstance();
    private ArrayList<String> xLabel;
    private int today_dayOfWeek;
    private TextView dateDes;
    private ImageView leftbtn, rightbtn;
    private LineChart chart;
    private PieChart chart2;
    private ArrayList<MoodObject> moodlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        xLabel = new ArrayList<>();
        xLabel.add("อาทิตย์");
        xLabel.add("จันทร์");
        xLabel.add("อังคาร");
        xLabel.add("พุธ");
        xLabel.add("พฤหัสฯ");
        xLabel.add("ศุกร์");
        xLabel.add("เสาร์");
        today_dayOfWeek = currentCalender.get(Calendar.DAY_OF_WEEK);
        moodlist = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_graph_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateDes = getView().findViewById(R.id.graph_week_date_des);
        leftbtn = getView().findViewById(R.id.graph_week_left_btn);
        rightbtn = getView().findViewById(R.id.graph_week_right_btn);
        chart = getView().findViewById(R.id.graph_week_chart1);
        chart2 = getView().findViewById(R.id.graph_week_chart2);

        setOnClickListener();
        setChart1();
        setChart2();

//        refreshGraph();

    }

    @Override
    public void onResume() {
        super.onResume();
        chart.invalidate();
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

        dateDes.setText(getThaiDate());

        dateDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        currentCalender.set(year, month, dayOfMonth);
                        tempCalendar.set(year, month, dayOfMonth);
                        dateDes.setText(getThaiDate());
                        setChart1();
                        setChart2();
                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
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
        currentCalender.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        r[0] = currentCalender.get(Calendar.YEAR) + "/" +
                (currentCalender.get(Calendar.MONTH) + 1) + "/" + currentCalender.get(Calendar.DAY_OF_MONTH);
        Log.d("s-date", r[0]);

        currentCalender.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        r[1] = currentCalender.get(Calendar.YEAR) + "/" +
                (currentCalender.get(Calendar.MONTH) + 1) + "/" + currentCalender.get(Calendar.DAY_OF_MONTH);
        System.out.println("s-date " + r[0]);
        System.out.println("e-date " + r[1]);
        Log.d("e-date", r[1]);

        return r;
    }

    private String getThaiDate(){
        tempCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String s1 = tempCalendar.get(Calendar.DAY_OF_MONTH) + " " +
                MyThaiCalender.getMonthOfYear(tempCalendar.get(Calendar.MONTH));

        tempCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String s2 = tempCalendar.get(Calendar.DAY_OF_MONTH) + " " +
                MyThaiCalender.getMonthOfYear(tempCalendar.get(Calendar.MONTH));

        return s1 + " - " + s2 + " " + tempCalendar.get(Calendar.YEAR) ;

    }
}
