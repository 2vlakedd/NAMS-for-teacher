package com.forappnams.nfcattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class attendance1 extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<String> arrayList;
    private MyAdapter myAdapter;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance1);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recycler_id);
        editText = findViewById(R.id.edit_text_id);

        arrayList = new ArrayList<String>();

        arrayList.add("Hello Coders");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(arrayList, this);
        recyclerView.setAdapter(myAdapter);

    }

    public void addItem(View view) {
        String text = editText.getText().toString();
        arrayList.add(text);
        int position = arrayList.indexOf(text);
        //notify adapter
        myAdapter.notifyItemInserted(position);
        //clean edittext....
        editText.setText("");
    }

    public void deleteItem(View view) {

        String text = editText.getText().toString();
        int position = arrayList.indexOf(text);
        arrayList.remove(position);
        //notify adapter
        myAdapter.notifyItemRemoved(position);
        editText.setText("");

    }

}