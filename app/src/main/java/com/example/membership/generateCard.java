package com.example.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class generateCard extends AppCompatActivity {

    TextView fullName, Email, Phone, fathersName, CNIC, city, profession, designation, education, status, dob;
    Button back;
    ImageView cardSettings;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_card);

        cardSettings = findViewById(R.id.cardSettings);
        back = findViewById(R.id.backButton);
        dob = findViewById(R.id.cardDOB);
        Phone = findViewById(R.id.cardPhone);
        fullName = findViewById(R.id.cardName);
        Email = findViewById(R.id.cardEmail);
        CNIC = findViewById(R.id.cardCNIC);
        city = findViewById(R.id.cardCity);
        profession = findViewById(R.id.cardProfession);
        designation = findViewById(R.id.cardDesignation);
        education = findViewById(R.id.cardEducation);
        fathersName = findViewById(R.id.cardFathersName);
        status = findViewById(R.id.cardStatus);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        userId = user.getUid();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot documentSnapshot) {
                Phone.setText(documentSnapshot.child("phone").getValue(String.class));
                fullName.setText(documentSnapshot.child("name").getValue(String.class));
                Email.setText(documentSnapshot.child("email").getValue(String.class));
                fathersName.setText(documentSnapshot.child("fatherName").getValue(String.class));
                CNIC.setText(documentSnapshot.child("cnic").getValue(String.class));
                education.setText(documentSnapshot.child("education").getValue(String.class));
                designation.setText(documentSnapshot.child("designation").getValue(String.class));
                profession.setText(documentSnapshot.child("profession").getValue(String.class));
                city.setText(documentSnapshot.child("city").getValue(String.class));
                status.setText(documentSnapshot.child("status1").getValue(String.class));
                dob.setText(documentSnapshot.child("profileDob").getValue(String.class));
                fullName.setText(documentSnapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }
}