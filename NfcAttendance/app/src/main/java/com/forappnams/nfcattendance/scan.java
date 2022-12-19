package com.forappnams.nfcattendance;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class scan extends AppCompatActivity {
    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "Text written Successfully!";
    public static final String Write_Error = "Error during writing, try again";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView edit_message;
    TextView nfc_contents;
    Button Attend;
    FirebaseFirestore db;
    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    String date = new SimpleDateFormat("d/MM/yyyy", Locale.getDefault()).format(new Date()); ;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);

        progressDialog = new ProgressDialog( scan.this);
        progressDialog.setMessage("Loading.....");

        db = FirebaseFirestore.getInstance();

       // edit_message = (TextView) findViewById(R.id.edit_message);
        nfc_contents = (TextView) findViewById(R.id.nfc_contents);
        Attend = (Button) findViewById(R.id.nfcattenddance);
        Attend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datanfc();
                addDataStudent();
                progressDialog.show();
            }


        });
        context = this;


        nfcAdapter =NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,"this device does not support nfc",Toast.LENGTH_SHORT).show();
            finish();

        }
        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this,0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        writingTagFilters= new IntentFilter[] {tagDetected};

    }


    private void datanfc() {


        Map<String, Object> user = new HashMap<>();

        user.put("StudentName", nfc_contents.getText().toString());
        user.put("Date", date);
        user.put("Time", currentTime);


        db.collection("Attendance")
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {


                        Toast.makeText(scan.this,"Successfully added",Toast.LENGTH_SHORT).show();

                    }

                });

    }




    private void addDataStudent() {

        final String Date = date;
        final String Studentname =nfc_contents.getText().toString();
       final String Time = currentTime;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyajPjkimA9zP6CdkphZKTaJu0B540bnL-4JOHq80dBK_Q7FEdEyPj3STEe0WcVLw2Wig/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(scan.this,""+response,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();


            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams(){

                Map<String,String>params =new HashMap<>();
                params.put("action","addStudent");
                params.put("nDate",Date);
                params.put("nName",Studentname);
                params.put("nTime",Time);

                return params;


            }
        };

        int socketTimeOut=50000;
        RetryPolicy retryPolicy= new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(scan.this);
        requestQueue.add(stringRequest);


    }






    private void readFromIntent (Intent intent){

        String action = intent.getAction();
        if (nfcAdapter.ACTION_TAG_DISCOVERED.equals(action)

                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;

            if (rawMsgs != null){

                msgs = new NdefMessage[rawMsgs.length];
                for (int i=0; i <rawMsgs.length; i++){
                    msgs[i]=(NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }


    }
    private void buildTagViews(NdefMessage[] msgs){
        if (msgs == null || msgs.length == 0) return;

        String text = "";
//            String tagId = new String(msgs[0].getRecords();
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the text Encoding
        int languageCodeLength = payload[0] & 0063; // get language code, e.g. "en"
        //String languageCode = new String(paylaod, 1, languageCodeLenght, "US-ASCII");

        try {
            //get the text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }catch (UnsupportedEncodingException e){
            Log.e("UnsupportedEncoding", e.toString());
        }

        nfc_contents.setText(text);

    }
    private void write(String text, Tag tag) throws IOException, FormatException{

        NdefRecord[] records = {createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        //Get an Instance of Ndef for the tag
        Ndef ndef = Ndef.get(tag);
        //Enable I/O
        ndef.connect();
        //write the message
        ndef.writeNdefMessage(message);
        //close the connection
        ndef.close();

    }
    private NdefRecord createRecord(@NonNull String text) throws UnsupportedEncodingException{
        String lang       = "en";
        byte[] textBytes  = text.getBytes();
        byte[] langBytes  = lang.getBytes("US-ASCII");
        int    langLength = langBytes.length;
        int    textLength = textBytes.length;
        byte[] payload    = new byte[1 + langLength + textLength];

        //set status byte (see NDEF spec for actual bits)
        payload[0] =(byte) langLength;

        //copy langbytes and textbytes into payload

        System.arraycopy(langBytes, 0, payload, 1,              langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;

    }

    @Override
    protected void onNewIntent(Intent intent){

        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag =intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        }
    }

    @Override
    public void onPause(){
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        WriteModeOn();

    }
    private void WriteModeOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this,pendingIntent,writingTagFilters, null);

    }
    private void WriteModeOff() {
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);

    }
}