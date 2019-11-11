package com.example.studentattendent;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class timetableparent extends Fragment {

    View view;

    private TextView dayparent,dateparent,termparent,yearparent;


    public timetableparent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_timetableparent, container, false);

        dayparent = view.findViewById(R.id.thomeparent);
        dateparent = view.findViewById(R.id.datehomeparent);
        termparent = view.findViewById(R.id.term1);
        yearparent = view.findViewById(R.id.year1);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDayFormat = new SimpleDateFormat("EEEE");
        String ct = simpleDateFormat.format(new Date());
        String dayn = simpleDayFormat.format(new Date());

        dayparent.setText("ตารางเรียน"+dayn);
        dateparent.setText(ct);


        return view;
    }

}
