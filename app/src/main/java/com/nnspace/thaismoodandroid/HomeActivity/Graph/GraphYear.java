package com.nnspace.thaismoodandroid.HomeActivity.Graph;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.ligl.android.widget.iosdialog.IOSSheetDialog;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.HomeActivity.List.MoodObject;
import com.nnspace.thaismoodandroid.MoodType;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GraphYear extends Fragment {

    private Calendar currentCalender, calendar;
    private ArrayList<String> xLabel;
    private LineChart chart;
    private PieChart chart2;
    private ArrayList<MoodObject> moodlist;
    private LinearLayout viewBtn;
    private TextView dateDes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentCalender = Calendar.getInstance();
        calendar = Calendar.getInstance();

        return inflater.inflate(R.layout.fragment_graph_year, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateDes = getView().findViewById(R.id.graph_year_date_des);
        viewBtn = getView().findViewById(R.id.view_btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IOSSheetDialog.SheetItem[] items = new IOSSheetDialog.SheetItem[6];
                items[0] = new IOSSheetDialog.SheetItem("3 เดือนที่ผ่านมา", IOSSheetDialog.SheetItem.BLUE);
                items[1] = new IOSSheetDialog.SheetItem("6 เดือนที่ผ่านมา", IOSSheetDialog.SheetItem.BLUE);
                items[2] = new IOSSheetDialog.SheetItem("9 เดือนที่ผ่านมา", IOSSheetDialog.SheetItem.BLUE);
                items[3] = new IOSSheetDialog.SheetItem("1 ปีที่ผ่านมา", IOSSheetDialog.SheetItem.BLUE);
                items[4] = new IOSSheetDialog.SheetItem("2 ปีที่ผ่านมา", IOSSheetDialog.SheetItem.BLUE);
                items[5] = new IOSSheetDialog.SheetItem("ทั้งหมด", IOSSheetDialog.SheetItem.BLUE);
                IOSSheetDialog dialog2 = new IOSSheetDialog.Builder(getActivity())
                        .setTitle("เลือกมุมมอง")
                        .setData(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        dateDes.setText("3 เดือนที่ผ่านมา");
                                        setChart1(0);
                                        break;
                                    case 1:
                                        dateDes.setText("6 เดือนที่ผ่านมา");
                                        setChart1(1);
                                        break;
                                    case 2:
                                        dateDes.setText("9 เดือนที่ผ่านมา");
                                        setChart1(2);
                                        break;
                                    case 3:
                                        dateDes.setText("1 ปีที่ผ่านมา");
                                        setChart1(3);
                                        break;
                                    case 4:
                                        dateDes.setText("2 ปีที่ผ่านมา");
                                        setChart1(4);
                                        break;
                                    case 5:
                                        dateDes.setText("ทั้งหมด");
                                        setChart1(5);
                                        break;
                                }
                            }
                        })
                        .setCancelText("ยกเลิก")
                        .show();
            }
        });

        chart = getView().findViewById(R.id.graph_year_chart1);
        chart2 = getView().findViewById(R.id.graph_year_chart2);

        setOnClickListener();
        setChart1(0);
        setChart2();

    }

    private void setChart1(int option) {

        ThaisMoodDB db = new ThaisMoodDB(getActivity());
        moodlist = db.getFliterMood(option);

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

    }
}
