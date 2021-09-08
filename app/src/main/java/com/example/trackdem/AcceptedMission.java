package com.example.trackdem;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AcceptedMission extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Cant Go Back Unless Mission is Completed")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .show();


    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in=getIntent();
        final int position=in.getIntExtra("acceptedMission",-1);

        setContentView(R.layout.activity_accepted_mission);
        final Button depBtn =(Button) findViewById(R.id.depBtn);
        final Button arrBtn =(Button) findViewById(R.id.arrBtn);
        final Button delBtn=(Button) findViewById(R.id.delBtn);
        final Button unitBtn=(Button) findViewById(R.id.unitBtn);

        final Button submitBtn=(Button) findViewById(R.id.subBtn);
        final Spinner driverSpinner=(Spinner) findViewById(R.id.driverSpinner);
        final Spinner assistantSpinner=(Spinner) findViewById(R.id.AssistantSpinner);
        final Spinner truckSpinner=(Spinner) findViewById(R.id.truckSpinner);
        final EditText RecipientName=(EditText) findViewById(R.id.RecepientField);
        final EditText Notes=(EditText) findViewById(R.id.notesField);

        final ArrayList<Missions> availableMissionsArray=new ArrayList();
        final ArrayList<String> employees=new ArrayList();
        final ArrayList<String> trucks=new ArrayList();
        final DatabaseReference employeeReference=FirebaseDatabase.getInstance().getReference("Employees");
        final DatabaseReference truckReference=FirebaseDatabase.getInstance().getReference("Trucks");
        final DatabaseReference missionsReference = FirebaseDatabase.getInstance().getReference("Missions");
        final String[] driverSelected = {""};
        final String[] truckSelected = {""};
        final String[] assistantSelected = {""};


        missionsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
int i=position+1;




                        availableMissionsArray.clear();
                        String missionId = dataSnapshot.child("Mission" + i).child("missionId").getValue(String.class);
                        String locationFrom = dataSnapshot.child("Mission" + i).child("from").getValue(String.class);
                        String locationTo = dataSnapshot.child("Mission" + i).child("to").getValue(String.class);
                        String description = dataSnapshot.child("Mission" + i).child("description").getValue(String.class);
                        String driverName = dataSnapshot.child("Mission" + i).child("driverName").getValue(String.class);
                        String status = dataSnapshot.child("Mission" + i).child("status").getValue(String.class);
                        String gpsLocation = dataSnapshot.child("Mission" + i).child("gps").getValue(String.class);
                        String notes = dataSnapshot.child("Mission"+ i).child("notes").getValue(String.class);
                        String assistantName = dataSnapshot.child("Mission" + i).child("assistantName").getValue(String.class);
                        String confirmedShipment = dataSnapshot.child("Mission" + i).child("confirmedShipment").getValue(String.class);

                        String cancelStatus = dataSnapshot.child("Mission" + i).child("cancelStatus").getValue(String.class);
                        String arrival= dataSnapshot.child("Mission" + i).child("arrival").getValue(String.class);
                        String departure= dataSnapshot.child("Mission" + i).child("departure").getValue(String.class);
                        String delivered= dataSnapshot.child("Mission" + i).child("delivering").getValue(String.class);
                        String unitAvailable= dataSnapshot.child("Mission" + i).child("unitAvailable").getValue(String.class);
                        Missions m1 = new Missions(missionId, locationFrom, locationTo, description, status, driverName, assistantName, gpsLocation, notes, confirmedShipment,departure,delivered,arrival,unitAvailable,cancelStatus);
                        availableMissionsArray.add(m1);



                    checkifCancelled();
                    //startRepeatingTask();
                    showInfo();
                    showSpinnerData();







                }
                else{
                    Toast.makeText(AcceptedMission.this, "No Available Data", Toast.LENGTH_SHORT).show();

                }
            }
            private final static int INTERVAL = 1000 * 5 *5;
            Handler mHandler = new Handler();

            Runnable mHandlerTask = new Runnable()
            {
                @Override
                public void run() {
                    checkifCancelled();
                    mHandler.postDelayed(mHandlerTask, INTERVAL);
                }
            };

            void startRepeatingTask()
            {
                mHandlerTask.run();
            }

            void stopRepeatingTask()
            {
                mHandler.removeCallbacks(mHandlerTask);
            }
            private void checkifCancelled() {
                if(availableMissionsArray.get(0).getCancelStatus().equals("Canceled"))//check if mission is canceled
                {
                    Toast.makeText(AcceptedMission.this, "Mission Cancelled", Toast.LENGTH_SHORT).show();
                    Intent startIntent = new Intent(getApplicationContext(), AvailableMissions.class);

                    startActivity(startIntent);
                    finish();
                }
                else if(availableMissionsArray.get(0).getConfirmedShipment().equals("Shipped"))//check if mission is canceled
                {
                    Toast.makeText(AcceptedMission.this, "Mission Completed", Toast.LENGTH_SHORT).show();
                    Intent startIntent = new Intent(getApplicationContext(), AvailableMissions.class);

                    startActivity(startIntent);
                    finish();
                }
            }

            private void showSpinnerData() {
                employeeReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        employees.clear();
                        for (DataSnapshot item: dataSnapshot.getChildren()){
                            employees.add(item.child("fullName").getValue(String.class));
                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AcceptedMission.this,R.layout.style_spinner,employees);
                        driverSpinner.setAdapter(arrayAdapter);
                        assistantSpinner.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                truckReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        trucks.clear();
                        for (DataSnapshot item: dataSnapshot.getChildren()){
                            trucks.add(item.child("Truck").getValue(String.class));
                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AcceptedMission.this,R.layout.style_spinner,trucks);
                        truckSpinner.setAdapter(arrayAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            private void showInfo(){
                final int i=position+1;
                if(!availableMissionsArray.get(0).getDeparted().equals("NA")){
                    depBtn.setText("Departed        " + availableMissionsArray.get(0).getDeparted());
                    depBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                }
                else {

                    depBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                            String dateTime = simpleDateFormat.format(calendar.getTime());
                            depBtn.setText("Departed        " + dateTime);
                            depBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                            missionsReference.child("Mission" + i).child("departure").setValue(dateTime.toString());
                        }
                    });
                }

                if(!availableMissionsArray.get(0).getArrival().equals("NA")){
                    arrBtn.setText("arrived     " + availableMissionsArray.get(0).getArrival());
                    arrBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                }
                else {
                    arrBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                            String dateTime = simpleDateFormat.format(calendar.getTime());
                            arrBtn.setText("arrived     " + dateTime);
                            arrBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                            missionsReference.child("Mission" + i).child("arrival").setValue(dateTime.toString());
                        }

                    });

                }
                if (!availableMissionsArray.get(0).getDelivered().equals("NA")){
                    delBtn.setText("delivered       " + availableMissionsArray.get(0).getDelivered());
                    delBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                }
                else {
                    delBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                            String dateTime = simpleDateFormat.format(calendar.getTime());
                            delBtn.setText("delivered       " + dateTime);
                            delBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                            missionsReference.child("Mission" + i).child("delivering").setValue(dateTime.toString());

                        }
                    });
                }
                if (!availableMissionsArray.get(0).getUnitAvailable().equals("NA")){
                    unitBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                }
                else {
                    unitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            unitBtn.setBackgroundColor(Color.parseColor("#00ff00"));
                            missionsReference.child("Mission" + i).child("unitAvailable").setValue("unit Available");

                        }
                    });
                }



                submitBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String ReciepientEnteredName=RecipientName.getText().toString().trim();
        final String NotesEntered= Notes.getText().toString();
        driverSelected[0] =driverSpinner.getSelectedItem().toString();
        assistantSelected[0] =assistantSpinner.getSelectedItem().toString();
        truckSelected[0] =truckSpinner.getSelectedItem().toString();
        if(ReciepientEnteredName.isEmpty()||NotesEntered.isEmpty()||truckSelected[0].isEmpty()||driverSelected[0].isEmpty()||assistantSelected[0].isEmpty()){
            new AlertDialog.Builder(AcceptedMission.this)
                    .setMessage("Please Fill All info")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();
        }
        else {
            if(driverSelected[0].equals(assistantSelected[0])){
                new AlertDialog.Builder(AcceptedMission.this)
                        .setMessage("Driver and assistant cant be same person")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .show();
            }
            else{
                missionsReference.child("Mission" + i).child("driverName").setValue(driverSelected[0]);
                missionsReference.child("Mission" + i).child("assistantName").setValue(assistantSelected[0]);
                missionsReference.child("Mission" + i).child("truck").setValue(truckSelected[0]);
                missionsReference.child("Mission" + i).child("notes").setValue(NotesEntered);
                missionsReference.child("Mission" + i).child("Recepient").setValue(ReciepientEnteredName);
                missionsReference.child("Mission" + i).child("confirmedShipment").setValue("Shipped");
            }//update database
        }
    }
});

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }
}