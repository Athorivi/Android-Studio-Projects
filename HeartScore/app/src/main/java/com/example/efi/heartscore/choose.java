package com.example.efi.heartscore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class choose extends AppCompatActivity {

    public void dimogragika(View view)
    {
        Intent i = getIntent();
        String username=i.getStringExtra("username");
        String id= i.getStringExtra("id");



        //Log.i("id:", i.getStringExtra("id"));

        Intent dimografika = new Intent(getApplicationContext(),dimografika.class);
        dimografika.putExtra("username",username);
        dimografika.putExtra("id", id);
        startActivity(dimografika);
    }

    public void erotimatologio(View view)
    {

        Intent i = getIntent();
        String username=i.getStringExtra("username");
        String id=i.getStringExtra("id");



        //Log.i("id:", i.getStringExtra("id"));
        Intent erotimatologio = new Intent(getApplicationContext(),erotimatologio.class);
        erotimatologio.putExtra("username",username);
        erotimatologio.putExtra("id", id);
        startActivity(erotimatologio);
    }

    public void logout(View view)
    {
        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }
}
