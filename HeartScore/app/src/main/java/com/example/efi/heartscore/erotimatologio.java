package com.example.efi.heartscore;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class erotimatologio extends AppCompatActivity {


    String[] historySpinner;
    String[] ekgSpinner;
    String[] ageSpinner;
    String[] rfSpinner;
    String[] itSpinner;
    Spinner history,ekg,age, rf, it;
    int results=0;
    TextView username;
    String user;
    String id="";
    public void logout(View view)
    {
        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erotimatologio);

        //history_spin
        this.historySpinner = new String[]{"Slightly suspicious     0", "Moderately suspicious      +1", "Highly suspicious     +2"};
        history = (Spinner) findViewById(R.id.history_spin);
        ArrayAdapter<String> history_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, historySpinner);
        history.setAdapter(history_adapter);

        //ekg_spin
        this.ekgSpinner = new String[]{"Normal     0", "Non-specific repolarization disturbance      +1", "Significant ST depression     +2"};
        ekg = (Spinner) findViewById(R.id.ekg_spin);
        ArrayAdapter<String> ekg_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ekgSpinner);
        ekg.setAdapter(ekg_adapter);

        //age_spin
        this.ageSpinner = new String[]{"<45     0", "45-64      +1", "≥65     +2"};
        age = (Spinner) findViewById(R.id.age_spin);
        ArrayAdapter<String> age_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ageSpinner);
        age.setAdapter(age_adapter);

        //rf_spin
        this.rfSpinner = new String[]{"No known risk factors     0", "1-2 risk factors      +1", "≥3 risk factors or history of atherosclerotic disease     +2"};
        rf = (Spinner) findViewById(R.id.rf_spin);
        ArrayAdapter<String> rf_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rfSpinner);
        rf.setAdapter(rf_adapter);

        //it_spin
        this.itSpinner = new String[]{"≤normal limit     0", "1–2× normal limit      +1", ">2× normal limit     +2"};
        it = (Spinner) findViewById(R.id.it_spin);
        ArrayAdapter<String> it_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itSpinner);
        it.setAdapter(it_adapter);


        Intent i = getIntent();
        //Log.i("Name:", i.getStringExtra("name"));

        user =i.getStringExtra("username");

        id = i.getStringExtra("id");

    }

    public void save(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://139.91.190.186/lesson/api.php/mdcalcresults/" + id;
        int method = Request.Method.PUT;

        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Mdcalc data updated.", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody = error.networkResponse.data.toString();
                Toast.makeText(getApplicationContext(), "Error saving your data to the DB.\nPlease try again.\n"+responseBody, Toast.LENGTH_LONG).show();
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, String> getParams() {
                String historyTextV = ((Spinner) findViewById(R.id.history_spin)).getSelectedItem().toString();
                String ekgTextV = ((Spinner) findViewById(R.id.ekg_spin)).getSelectedItem().toString();
                String ageTextV = ((Spinner) age.getSelectedView().findViewById(R.id.age_spin)).getSelectedItem().toString();
                String rfTextV = ((Spinner) rf.getSelectedView().findViewById(R.id.rf_spin)).getSelectedItem().toString();
                String itTextV = ((Spinner) it.getSelectedView().findViewById(R.id.it_spin)).getSelectedItem().toString();
                if(historyTextV.toString()=="Moderately suspicious      +1") {
                    results=results+1;
                }else if(historyTextV.toString()=="Highly suspicious     +2") {
                    results = results + 2;
                }

                if(ekgTextV.toString()=="Non-specific repolarization disturbance      +1") {
                    results=results+1;
                }else if (ekgTextV.toString()=="Significant ST depression     +2") {
                    results = results + 2;
                }

                if(ageTextV.toString()=="45-64      +1") {
                    results=results+1;
                }else if (ageTextV.toString()=="≥65     +2") {
                    results = results + 2;
                }

                if(rfTextV.toString()=="1-2 risk factors      +1") {
                    results=results+1;
                }else if (rfTextV.toString()=="≥3 risk factors or history of atherosclerotic disease     +2") {
                    results = results + 2;
                }

                if(itTextV.toString()=="1–2× normal limit      +1") {
                    results=results+1;
                }else if (itTextV.toString()==">2× normal limit     +2") {
                    results = results + 2;
                }



                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", id);
                params.put("username", String.valueOf(username));
                String currentDateandTime;
                currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                params.put("date", currentDateandTime);
                params.put("results",String.valueOf(results));
                params.put("calculatorName","Efi");
                return params;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}




