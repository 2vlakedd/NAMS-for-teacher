package com.forappnams.nfcattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;


public class attend extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recycle_id);

        arrayList = new ArrayList<String>();

        arrayList.add("hello");
        arrayList.add("hello");
        arrayList.add("hello");
        arrayList.add("hello");
        arrayList.add("hello");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //MyAdapter myAdapter = new MyAdapter(arrayList);
        //recyclerView.setAdapter(myAdapter);

    }
}