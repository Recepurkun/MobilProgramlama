package com.example.vizeprojedeneme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_Convertor;
    Button btn_Random;
    Button btn_Sms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Convertor = (Button) findViewById(R.id.btn_convertor);
        btn_Random = (Button) findViewById(R.id.btn_random);
        btn_Sms = (Button) findViewById(R.id.btn_sms);

        btn_Convertor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent convertorIntent = new Intent(MainActivity.this,Convertor_Activity.class);
                startActivity(convertorIntent);
            }
        });

        btn_Random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomIntent = new Intent(getApplicationContext(), RandomGenerator_Activity22.class);
                startActivity(randomIntent);
            }
        });

        btn_Sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(MainActivity.this, SMS_Activity.class);
                startActivity(smsIntent);
            }
        });

    }
}