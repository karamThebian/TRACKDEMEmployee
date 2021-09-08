package com.example.trackdem;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button loginBtn = (Button) findViewById(R.id.loginBtn);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameField=(EditText) findViewById(R.id.usernameField);
                EditText passField=(EditText) findViewById(R.id.passField);
                final String userEnteredUsername=usernameField.getText().toString().trim();
                final String userEnteredPass= passField.getText().toString().trim();


                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Employees");
                Query checkSupervisor=reference.orderByChild("username").equalTo(userEnteredUsername);
                checkSupervisor.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String passwordFromDB=dataSnapshot.child(userEnteredUsername).child("password").getValue().toString();
                            if (passwordFromDB.equals(userEnteredPass)){
                                String nameFromDB=dataSnapshot.child(userEnteredUsername).child("fullName").getValue().toString();
                                String AcceptedMissionStatus=dataSnapshot.child(userEnteredUsername).child("acceptedMission").getValue().toString();
                                if(AcceptedMissionStatus.equals("No Current Mission")) {
                                    Intent startIntent = new Intent(getApplicationContext(), AvailableMissions.class);
                                    Toast.makeText(MainActivity.this, "Welcome Mr." + nameFromDB + "!", Toast.LENGTH_SHORT).show();
                                    startActivity(startIntent);
                                }
                                else{
                                    Intent startIntent = new Intent(getApplicationContext(), AcceptedMission.class);
                                    int position=Integer.parseInt(AcceptedMissionStatus);
                                    position--;
                                    startIntent.putExtra("acceptedMission",position);
                                    Toast.makeText(MainActivity.this, "Welcome Back Mr." + nameFromDB + "!", Toast.LENGTH_SHORT).show();
                                    startActivity(startIntent);
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this,"Invalid Username",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });














            }
        });
    }
}
