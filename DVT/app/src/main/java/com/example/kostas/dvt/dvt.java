package com.example.kostas.dvt;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class dvt extends AppCompatActivity {

    int results = 0;
    TextView username;
    String user;
    String id = "";

    public void logout(View view) {
        Intent i = new Intent(getApplicationContext(), login.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvt);
    }


    public void save(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://139.91.190.186/lesson/api.php/mdcalcresults/" + id;
        int method = Request.Method.PUT;
       /* if (!mdcalcresultsDataExists) {
            url = "http://139.91.190.186/lesson/api.php/mdculcresults/";
            method = Request.Method.POST;
        }*/

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
                Toast.makeText(getApplicationContext(), "Error saving your data to the DB.\nPlease try again.\n" + responseBody, Toast.LENGTH_LONG).show();
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, String> getParams() {

                ToggleButton b1 = (ToggleButton) findViewById(R.id.t1);
                ToggleButton b2 = (ToggleButton) findViewById(R.id.t2);
                ToggleButton b3 = (ToggleButton) findViewById(R.id.t3);
                ToggleButton b4 = (ToggleButton) findViewById(R.id.t4);
                ToggleButton b5 = (ToggleButton) findViewById(R.id.t5);
                ToggleButton b6 = (ToggleButton) findViewById(R.id.t6);
                ToggleButton b7 = (ToggleButton) findViewById(R.id.t7);
                ToggleButton b8 = (ToggleButton) findViewById(R.id.t8);
                ToggleButton b9 = (ToggleButton) findViewById(R.id.t9);
                ToggleButton b10 = (ToggleButton) findViewById(R.id.t10);

                if (b1.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b2.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b3.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b4.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b5.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b6.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b7.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b8.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b9.getText().toString() == "Yes") {
                    results = results + 1;
                }
                if (b10.getText().toString() == "Yes") {
                    results = results - 2;
                }


                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", id);
                params.put("username", String.valueOf(username));
                String currentDateandTime;
                currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                params.put("date", currentDateandTime);
                params.put("results", String.valueOf(results));
                params.put("calculatorName", "Kostas");
                return params;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
