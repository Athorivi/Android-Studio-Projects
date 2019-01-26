package com.example.kostas.dvt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class epilogh extends AppCompatActivity {

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

        public void dvt(View view)
        {

            Intent i = getIntent();
            String username=i.getStringExtra("username");
            String id=i.getStringExtra("id");



            //Log.i("id:", i.getStringExtra("id"));
            Intent dvt = new Intent(getApplicationContext(),dvt.class);
            dvt.putExtra("username",username);
            dvt.putExtra("id", id);
            startActivity(dvt);
        }

        public void logout(View view)
        {
            Intent i = new Intent(getApplicationContext(),login.class);
            startActivity(i);
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epilogh);
    }
}
