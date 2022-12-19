package com.forappnams.nfcattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CoursesLVAdapter extends ArrayAdapter<DataModal> {
        public CoursesLVAdapter(Context context, List<DataModal> object){
            super(context,0, object);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.activity_courses_lvadapter,parent,false);
            }

            TextView titleTextView = (TextView) convertView.findViewById(R.id.mission_title);
             TextView dateTextView = (TextView) convertView.findViewById(R.id.mission_date);

            DataModal mission = getItem(position);



            return convertView;
        }

    }