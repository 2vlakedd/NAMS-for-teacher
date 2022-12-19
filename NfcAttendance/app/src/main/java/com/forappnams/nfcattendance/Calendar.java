package com.forappnams.nfcattendance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Calendar extends AppCompatActivity {


    ListView mListView;
    FirebaseFirestore db;
    TextView Toolbar_name;
    ImageView Right_icon;
    private List<String> nameList=new ArrayList<>();
    String currentTime = new SimpleDateFormat("d/MM/yyyy", Locale.getDefault()).format(new Date()); ;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mListView = (ListView) findViewById(R.id.listView);
        db = FirebaseFirestore.getInstance();
        Toolbar_name = findViewById(R.id.toolbar_name);




        db.collection("Attendance").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {



                nameList.clear();

                for(DocumentSnapshot snapshot: documentSnapshots){

                    nameList.add(snapshot.getString("name"));

                }
                ArrayAdapter<String> adapter  =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,nameList);
                adapter.notifyDataSetChanged();
                mListView.setAdapter(adapter);



            }
        });
    }
}