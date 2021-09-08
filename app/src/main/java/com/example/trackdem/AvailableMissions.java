package com.example.trackdem;


import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;






        import android.content.res.Resources;
        import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
        import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvailableMissions extends AppCompatActivity {

     ListView myListView;

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to Sign Out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AvailableMissions.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_missions);
        final ArrayList<Missions> availableMissionsArray=new ArrayList();


        DatabaseReference missionsReference = FirebaseDatabase.getInstance().getReference("Missions");
        missionsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                int missionCount=(int) dataSnapshot.getChildrenCount();

                for (int i=1;i<=missionCount;i++){



                        String missionId = dataSnapshot.child("Mission" + i).child("missionId").getValue(String.class);
                        String locationFrom = dataSnapshot.child("Mission" + i).child("from").getValue(String.class);
                        String locationTo = dataSnapshot.child("Mission" + i).child("to").getValue(String.class);
                        String description = dataSnapshot.child("Mission" + i).child("description").getValue(String.class);
                        String driverName = dataSnapshot.child("Mission" + i).child("driverName").getValue(String.class);
                        String status = dataSnapshot.child("Mission" + i).child("status").getValue(String.class);
                        String gpsLocation = dataSnapshot.child("Mission" + i).child("gps").getValue(String.class);
                        String notes = dataSnapshot.child("Mission"+ i).child("notes").getValue(String.class);
                        String assistantName = dataSnapshot.child("Mission" + i).child("assistantName").getValue(String.class);
                    String cancelStatus = dataSnapshot.child("Mission" + i).child("cancelStatus").getValue(String.class);
                        String confirmedShipment = dataSnapshot.child("Mission" + i).child("confirmedShipment").getValue(String.class);
                    String arrival= dataSnapshot.child("Mission" + i).child("arrival").getValue(String.class);
                    String departure= dataSnapshot.child("Mission" + i).child("departure").getValue(String.class);
                    String delivered= dataSnapshot.child("Mission" + i).child("delivering").getValue(String.class);
                    String unitAvailable= dataSnapshot.child("Mission" + i).child("unitAvailable").getValue(String.class);
                    Missions m1 = new Missions(missionId, locationFrom, locationTo, description, status, driverName, assistantName, gpsLocation, notes, confirmedShipment,departure,delivered,arrival,unitAvailable,cancelStatus);
                        availableMissionsArray.add(m1);



                }
                showList();



                }
            else{
                    Toast.makeText(AvailableMissions.this, "No Available Data", Toast.LENGTH_SHORT).show();

                }
            }

            private void showList() {
                myListView =(ListView) findViewById(R.id.myListView);

                ItemAdapter itemAdapter= new ItemAdapter(AvailableMissions.this,availableMissionsArray);
                myListView.setAdapter(itemAdapter);

                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent showSelectedMission =new Intent(getApplicationContext(),SelectedMission.class);
                        showSelectedMission.putExtra("selectedMission",position);
                        startActivity(showSelectedMission);


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}









