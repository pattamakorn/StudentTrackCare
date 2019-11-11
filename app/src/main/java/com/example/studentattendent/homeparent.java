package com.example.studentattendent;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeparent extends Fragment {

    private TextView parentname;
    private ImageView picprofile,moredetail;
    View view;

    private RecyclerView recyclerView;
    private List<news> listnews;

    public homeparent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homeparent, container, false);

        parentname = view.findViewById(R.id.parentname);
        picprofile = view.findViewById(R.id.parentProfile);
        moredetail = view.findViewById(R.id.moredetail);

        parentname.setText("Korn Pattamakorn");

        Glide.with(view.getContext()).load("https://firebasestorage.googleapis.com/v0/b/tsb1-df2ab.appspot.com/o/korn.jpg?alt=media&token=8fdbf4a1-77ab-43ce-b164-739789e65540").
                into(picprofile);

        moredetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = view.findViewById(R.id.recyclernewsparent);
        newsAdapter NewsAdapter = new newsAdapter(getContext(),listnews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(NewsAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listnews = new ArrayList<>();
        listnews.add(new news("42","Testt","Test","11/11/2562",
                "https://firebasestorage.googleapis.com/v0/b/tsb1-df2ab.appspot.com/o/korn.jpg?alt=media&token=8fdbf4a1-77ab-43ce-b164-739789e65540"));
        listnews.add(new news("42","Testt1","Test1","11/11/2562",
                "https://firebasestorage.googleapis.com/v0/b/tsb1-df2ab.appspot.com/o/korn.jpg?alt=media&token=8fdbf4a1-77ab-43ce-b164-739789e65540"));



    }
}
