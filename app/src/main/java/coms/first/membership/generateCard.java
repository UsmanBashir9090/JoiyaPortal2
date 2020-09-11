package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
/*
public class generateCard extends AppCompatActivity {

    TextView fullName, Email, Phone, fathersName, CNIC, city, profession, designation, education, status, dob;
    Button back, check;
    ImageView cardImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    DatabaseReference database;
    StorageReference storageReference;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_card);

       // cardSettings = findViewById(R.id.cardSettings);
        cardImage = findViewById(R.id.cardImage);
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
        check = findViewById(R.id.checkButton);

        storageReference = FirebaseStorage.getInstance().getReference();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        userId = user.getUid();

      //  database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

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


        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(cardImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(generateCard.this, "Failed to load image.", Toast.LENGTH_SHORT).show();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activityFacebook.class));
                Toast.makeText(generateCard.this, "opening share to facebook page", Toast.LENGTH_SHORT).show();
            }
        });

    }

}*/