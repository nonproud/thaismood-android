package com.nnspace.thaismoodandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nnspace.thaismoodandroid.Database.EmotionDB;
import com.nnspace.thaismoodandroid.MoodObject;
import com.nnspace.thaismoodandroid.R;
import com.nnspace.thaismoodandroid.RecordListAdapter;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = getView().findViewById(R.id.list_listview);
        TextView type = getView().findViewById(R.id.list_type);
        EmotionDB db = new EmotionDB(getActivity());
        ArrayList<MoodObject> moodList = db.getMood();
        RecordListAdapter adapter = new RecordListAdapter(getActivity(), moodList);
        listView.setAdapter(adapter);
        type.setText("Amount: " + moodList.size());
        Toast.makeText(getActivity(), "Amount: " + moodList.size() , Toast.LENGTH_LONG);
    }
}
