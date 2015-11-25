package com.joaopires.servidor_intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CostumerActivity extends AppCompatActivity {

    protected TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer);


        Intent w = getIntent();
        Cliente c = (Cliente) w.getSerializableExtra("costumer");

        t = (TextView)findViewById(R.id.t);

        t.setText("Fist Name: " + c.getFirstName() + "\nLast Name: " + c.getLastName() + "\nCity: " + c.getCity() + "\nStreet: " + c.getStreet());
    }
}
