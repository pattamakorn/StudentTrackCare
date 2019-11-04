package com.example.studentattendent;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class checkname extends Fragment {
    View view;

    private TextView showkeywordtoday,showmysub;
    private Spinner spinnersc;

    List<String> listspinnersc  = new ArrayList<>();
    ArrayAdapter<String> spinnerAdaptersc;

    public checkedAdapter CheckedAdapter;
    private RecyclerView recyclerView;
    private List<checked> listcheck;
    private String Url_Loadcheck = "http://203.154.83.137/StudentAttendent/loadchecked.php";
    public String ccl,keycheck;


    public checkname() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_checkname, container, false);

        showkeywordtoday = view.findViewById(R.id.showqrnowday);
        showmysub = view.findViewById(R.id.mysub);
        spinnersc = view.findViewById(R.id.selectclass);
        recyclerView = view.findViewById(R.id.recyclertcheckscan);

        CheckedAdapter = new checkedAdapter(getContext(),listcheck);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(CheckedAdapter);

        spinnerAdaptersc = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdaptersc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersc.setAdapter(spinnerAdaptersc);
        listspinnersc.add("4/1");
        listspinnersc.add("4/2");
        listspinnersc.add("5/1");
        listspinnersc.add("5/2");
        listspinnersc.add("6/1");
        listspinnersc.add("6/2");
        spinnerAdaptersc.addAll(listspinnersc);
        spinnerAdaptersc.notifyDataSetChanged();
        loadcheck();
        spinnersc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ccl = listspinnersc.get(i);
                listcheck.clear();
                CheckedAdapter.notifyDataSetChanged();
                loadmykey();
                loadcheck();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                loadmykey();
                loadcheck();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listcheck = new ArrayList<>();
        loadmykey();
        loadcheck();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadmykey(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://203.154.83.137/StudentAttendent/loadscanedqrcode.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseStudent",response.toString());
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String mykey = posts.getString("keyword");
                        String subid = posts.getString("subid");
                        String subname = posts.getString("subname");
                        String fname = posts.getString("fnameT");
                        String lname = posts.getString("lnameT");
                        String gen = posts.getString("gen");
                        keycheck = mykey;
                        showkeywordtoday.setText("Keyword "+mykey+"\n"+gen);
                        showmysub.setText(subid+" "+subname+"\n"+fname+" "+lname);

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
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    public void loadcheck(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Url_Loadcheck, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseStudent",response.toString());
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject posts = array.getJSONObject(i);
                        String stbid = posts.getString("stdid");
                        String fname = posts.getString("fname");
                        String lname = posts.getString("lname");
                        String timec = posts.getString("timescaned");

                        listcheck.add(new checked(
                                "รหัสประจำตัว "+stbid+"\nชื่อ "+fname+" "+lname,
                                "เวลาเช็คชื่อ "+timec)
                        );
                        CheckedAdapter = new checkedAdapter(getContext(),listcheck);
                        recyclerView.setAdapter(CheckedAdapter);

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
                params.put("myid",mid);
                params.put("class",ccl);
                params.put("inkey",keycheck);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
