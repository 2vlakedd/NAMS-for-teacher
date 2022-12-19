package com.forappnams.nfcattendance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AttendanceRecords extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    private List<String> nameList=new ArrayList<>();

    DatabaseHelper mDatabaseHelper;
    public static String  name;

    public static String  date = new SimpleDateFormat("d/MM/yyyy", Locale.getDefault()).format(new Date()); ;
    private ListView mListView;
    TextView Toolbar_name;
    ImageView Right_icon;
    FirebaseFirestore db;
    Button attendancestud;
    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        Toolbar_name = findViewById(R.id.toolbar_name);

        Right_icon = (ImageView) findViewById(R.id.right_icon);
        this.mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        populateListView();
        db = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
   populateListView();
        Right_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AttendanceRecords.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                date = dayOfMonth+"/"+month+"/"+year;
                Toolbar_name.setText(date);

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
        };
    }





    private void populateListView() {


/*
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {

            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mDatabaseHelper.insertAttend(date , name, currentTime);
                name = adapterView.getItemAtPosition(i).toString();

                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Map<String, Object> user = new HashMap<>();


                user.put("Date", date);
                user.put("StudentName", name);
                user.put("Time", currentTime);

                db.collection("Attendance")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AttendanceRecords.this, "Attendance", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AttendanceRecords.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });




            }

        });*/



    }







}
