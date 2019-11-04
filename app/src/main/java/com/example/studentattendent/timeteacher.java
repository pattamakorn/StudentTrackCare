package com.example.studentattendent;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class timeteacher extends Fragment {
    private  String URL_TIMETeach = "http://203.154.83.137/StudentAttendent/loadteachtime.php";
    private TextView nowday,nowdate,showy;
    public String today,toyear,toterm;
    private Spinner spinnert;
    public teachAdapter TeachAdapter;
    View view;


    private RecyclerView recyclerView;
    private List<teach> listteachtimetable;

    List<String> listspinners  = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapters;

    public timeteacher() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_timeteacher, container, false);
        nowdate = view.findViewById(R.id.datett);
        nowday = view.findViewById(R.id.daytt);
        spinnert = view.findViewById(R.id.selectterm);
        showy = view.findViewById(R.id.showyear);
        recyclerView = view.findViewById(R.id.recyclerteachertime);
        TeachAdapter = new teachAdapter(getContext(),listteachtimetable);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(TeachAdapter);

        spinnerAdapters = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnert.setAdapter(spinnerAdapters);
        listspinners.add("1");
        listspinners.add("2");
        spinnerAdapters.addAll(listspinners);
        spinnerAdapters.notifyDataSetChanged();

        spinnert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toterm = listspinners.get(i);
                listteachtimetable.clear();
                TeachAdapter.notifyDataSetChanged();
                loadtime();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDayFormat = new SimpleDateFormat("EEEE");
        String ct = simpleDateFormat.format(new Date());
        String dayn = simpleDayFormat.format(new Date());
        today = dayn;
        //toterm = "1";
        toyear = showy.getText().toString();

        nowday.setText("Hello, "+dayn);
        nowdate.setText(ct);

        //loadtime();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listteachtimetable = new ArrayList<>();

        //loadtime();
    }

    public void loadtime(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/StudentAttendent/loadteachtime.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseStudent",response.toString());
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String subid = posts.getString("idsubject");
                        String subname = posts.getString("subjectname");
                        String mclassroom = posts.getString("class");
                        String mtime = posts.getString("time");
                        listteachtimetable.add(new teach(
                                subid+" "+subname,
                                "ห้องเรียน "+mclassroom,
                                "เวลา "+mtime)
                        );
                        TeachAdapter = new teachAdapter(getContext(),listteachtimetable);
                        recyclerView.setAdapter(TeachAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("abc",error.toString());
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferences sp = getActivity().getSharedPreferences(login.MyPREFERENCES, Context.MODE_PRIVATE);
                String mid = sp.getString("IdKey","No ID");
                params.put("id",mid);
                params.put("term",toterm);
                params.put("day",today);
                params.put("year",toyear);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
