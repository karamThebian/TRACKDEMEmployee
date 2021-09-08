package com.example.trackdem;

import android.content.Intent;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedMission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_mission);
        final ArrayList<Missions> availableMissionsArray=new ArrayList();
        Intent in=getIntent();
        final int position=in.getIntExtra("selectedMission",-1);
        final TextView missionField=(TextView)findViewById(R.id.missionField);
        final TextView descriptionField=(TextView)findViewById(R.id.descriptionField);
        final TextView locationFromField=(TextView)findViewById(R.id.locationFromField);
        final TextView locationToField=(TextView)findViewById(R.id.locationToField);




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
                        String confirmedShipment = dataSnapshot.child("Mission" + i).child("confirmedShipment").getValue(String.class);
                        String arrival= dataSnapshot.child("Mission" + i).child("arrival").getValue(String.class);
                        String departure= dataSnapshot.child("Mission" + i).child("departure").getValue(String.class);
                        String delivered= dataSnapshot.child("Mission" + i).child("delivering").getValue(String.class);
                        String cancelStatus = dataSnapshot.child("Mission" + i).child("cancelStatus").getValue(String.class);
                        String unitAvailable= dataSnapshot.child("Mission" + i).child("unitAvailable").getValue(String.class);
                        Missions m1 = new Missions(missionId, locationFrom, locationTo, description, status, driverName, assistantName, gpsLocation, notes, confirmedShipment,departure,delivered,arrival,unitAvailable,cancelStatus);                        availableMissionsArray.add(m1);




                    }
                showInfo();







                }
                else{
                    Toast.makeText(SelectedMission.this, "No Available Data", Toast.LENGTH_SHORT).show();

                }
            }


private void showInfo(){
    missionField.setText("Mission: "+availableMissionsArray.get(position).getMissionId());
    descriptionField.setText("Description: "+availableMissionsArray.get(position).getDescription());
    locationFromField.setText("From: "+availableMissionsArray.get(position).getLocationFrom());
    locationToField.setText("To: "+availableMissionsArray.get(position).getLocationTo());
}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        Button acceptBtn=(Button)findViewById(R.id.acceptBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectedMission.this, "Mission Accepted", Toast.LENGTH_SHORT).show();
                Intent showAcceptedMission =new Intent(getApplicationContext(),AcceptedMission.class);
                showAcceptedMission.putExtra("acceptedMission",position);
                startActivity(showAcceptedMission);
            }
        });
    }
}
