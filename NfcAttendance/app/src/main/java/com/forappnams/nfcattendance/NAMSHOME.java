package com.forappnams.nfcattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NAMSHOME extends AppCompatActivity {


    CardView attendance;
    CardView scanner;
    CardView reg;
    CardView present;

    CardView excel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namshome);

        attendance = findViewById(R.id.attendance);
        scanner =  findViewById(R.id.scanner);
        reg =  findViewById(R.id.reg);
        present =  findViewById(R.id.present);
        excel =  findViewById(R.id.spreaddata);
        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            goLink("https://docs.google.com/spreadsheets/d/1Co-7-DO_1UQNMvLUX8rMVVT5wlwK-3G9D10DtdWbXPk/edit?usp=sharing");
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), com.forappnams.nfcattendance.ListDataActivity.class);
                startActivity(intent);
            }
        });
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), com.forappnams.nfcattendance.scan.class);
                startActivity(intent);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), com.forappnams.nfcattendance.regi.class);
                startActivity(intent);
            }
        });
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), com.forappnams.nfcattendance.pres.class);
                startActivity(intent);
            }
        });
    }

    private void goLink(String s) {

        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}