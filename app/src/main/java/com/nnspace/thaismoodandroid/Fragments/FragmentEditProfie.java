package com.nnspace.thaismoodandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nnspace.thaismoodandroid.R;


/**
 * A simple {@link Fragment} subclass.
 * com.nnspace.thaismoodandroid.Activities that contain this fragment must implement the
 * {@link FragmentEditProfie.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentEditProfie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditProfie extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profie, container, false);
    }

}
