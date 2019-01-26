package com.example.kostas.dvt;

import android.content.Intent;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.HashMap;
import java.util.Map;

public class dimografika extends AppCompatActivity {

    String[] arraySpinner;
    boolean demogDataExists = false;
    String id = "";
    String user;
    Spinner gender;


    public void logout(View view)
    {
        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }

    public void dvt(View view)
    {
        Intent k = new Intent(getApplicationContext(),dvt.class);
        k.putExtra("username",user);
        k.putExtra("id",id);
        startActivity(k);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimografika);


        Intent i = getIntent();
        //Log.i("Name:", i.getStringExtra("name"));

        user =i.getStringExtra("username");

        id = i.getStringExtra("id");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://139.91.190.186/lesson/api.php/demographics/" + id;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        try {
                            JSONObject json = new JSONObject(response);
                            TextView nameTextV = (TextView) findViewById(R.id.onoma);
                            TextView lastnameTextV = (TextView) findViewById(R.id.epitheto);
                            TextView gennisiTextV = (TextView) findViewById(R.id.gennisi);
                            TextView filoTextV = (TextView) findViewById(R.id.filo);;
                            TextView poliTextV = (TextView) findViewById(R.id.poli);
                            TextView ethnikotitaTextV = (TextView) findViewById(R.id.ethnikotita);



                            nameTextV.setText(json.get("name").toString());
                            lastnameTextV.setText(json.get("lastname").toString());
                            gennisiTextV.setText(json.get("birthday").toString());
                            filoTextV.setText(json.get("gender").toString());
                            poliTextV.setText(json.get("city").toString());
                            ethnikotitaTextV.setText(json.get("ethnicity").toString());

                            demogDataExists = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please provide your demographics data.", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void save(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://139.91.190.186/lesson/api.php/demographics/" + id;
        int method = Request.Method.PUT;
        if (!demogDataExists) {
            url = "http://139.91.190.186/lesson/api.php/demographics/";
            method = Request.Method.POST;
        }

        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Demographics data updated.", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody = error.networkResponse.data.toString();
                Toast.makeText(getApplicationContext(), "Error saving your data to the DB.\nPlease try again.\n"+responseBody, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                TextView nameTextV = (TextView) findViewById(R.id.onoma);
                TextView lastnameTextV = (TextView) findViewById(R.id.epitheto);
                TextView filoTextV = (TextView) findViewById(R.id.filo);
                TextView gennisiTextV = (TextView) findViewById(R.id.gennisi);
                TextView poliTextV = (TextView) findViewById(R.id.poli);
                TextView ethnikotitaTextV = (TextView) findViewById(R.id.ethnikotita);

                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", id);
                params.put("name", nameTextV.getText().toString());
                params.put("lastname", lastnameTextV.getText().toString());
                params.put("gender", filoTextV.getText().toString());
                params.put("birthday",gennisiTextV.getText().toString());
                params.put("city", poliTextV.getText().toString());
                params.put("ethnicity", ethnikotitaTextV.getText().toString());
                return params;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}


