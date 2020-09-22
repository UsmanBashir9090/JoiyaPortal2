package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class pending_list_details extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView fullName, Email, uid, Phone, fathersName, CNIC, address, city, profession, designation, education, status, timestamp, dob, viewCard, province;
    FirebaseUser user;
    DatabaseReference database, ref;
    String id;
    String adminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_list_details);


        // viewCard = findViewById(R.id.generateCard);
        province = findViewById(R.id.province);
        Phone = findViewById(R.id.textPhone);
        fullName = findViewById(R.id.textName);
        Email = findViewById(R.id.textEmail);
        CNIC = findViewById(R.id.profileCNIC);
        address = findViewById(R.id.profileAddress);
        city = findViewById(R.id.profileCity);
        profession = findViewById(R.id.profileProfession);
        designation = findViewById(R.id.profileDesignation);
        education = findViewById(R.id.profileEducation);
        fathersName = findViewById(R.id.fathersname);
        status = findViewById(R.id.status);
        timestamp = findViewById(R.id.timestamp);
        dob = findViewById(R.id.dob);
        uid = findViewById(R.id.uid);

        final String key = getIntent().getExtras().get("key").toString();

     /*   viewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = (new Intent(getApplicationContext(), viewCard.class));
                startActivity(intent);

            }
        }); */

        fullName.setText(getIntent().getStringExtra("name"));
        Email.setText(getIntent().getStringExtra("email"));
        city.setText(getIntent().getStringExtra("city"));
        Phone.setText(getIntent().getStringExtra("phone"));
        address.setText(getIntent().getStringExtra("address"));
        designation.setText(getIntent().getStringExtra("designation"));
        education.setText(getIntent().getStringExtra("education"));
        status.setText(getIntent().getStringExtra("status"));
        uid.setText(key);
        timestamp.setText(getIntent().getStringExtra("timestamp"));
        profession.setText(getIntent().getStringExtra("profession"));
        dob.setText(getIntent().getStringExtra("dob"));
        CNIC.setText(getIntent().getStringExtra("cnic"));
        fathersName.setText(getIntent().getStringExtra("fathername"));
        province.setText(getIntent().getStringExtra("province"));


        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(id);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot documentSnapshot) {
                adminName = documentSnapshot.child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void buttonaccept(View view) {
        final String key = getIntent().getExtras().get("key").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(key);
        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();

        final String status_province = "Approved_" + province.getText().toString();
        database = FirebaseDatabase.getInstance().getReference().child("users").child(id);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot documentSnapshot) {
                String role = documentSnapshot.child("role").getValue(String.class);
                Log.d(TAG, role);
                Log.d(TAG, designation.getText().toString().trim());
                if (role.equals("admin")){
                    if(designation.getText().toString() == "Member"){
                        ref.child("status1").setValue("Approved");
                        ref.child("provincial_approval").setValue("Approved");
                        ref.child("status_province").setValue(status_province);
                        ref.child("prov_app_name").setValue(adminName);
                        ref.child("status").setValue("You are now a Member");
                        Toast.makeText(getApplicationContext(), "Application Approved", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        ref.child("provincial_approval").setValue("Approved");
                        ref.child("status_member").setValue("Approved_0");
                        ref.child("status_province").setValue(status_province);
                        ref.child("prov_app_name").setValue(adminName);
                        Toast.makeText(getApplicationContext(), "Application sent to president for approval", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }  if(role.equals("superAdmin")){
                    ref.child("presidential_approval").setValue("Approved");
                    ref.child("status1").setValue("Approved");
                    ref.child("status_member").setValue("Complete_0");
                    ref.child("status").setValue("You are now a Member");
                    Toast.makeText(getApplicationContext(), "Application Approved", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void buttondecline(View view){
        final String key = getIntent().getExtras().get("key").toString();
        final String status_province = "Declined_" + province.getText().toString();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(key);
        ref.child("status1").setValue("Declined");
        ref.child("status_province").setValue(status_province);
        ref.child("status_member").setValue("Declined_0");
        ref.child("provincial_approval").setValue("Declined");
        ref.child("status").setValue("Application was declined by " + adminName);
    }

    //Need to add deleting user from authetication function
    public void buttondelete(View view){

        final String key = getIntent().getExtras().get("key").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(key);
        ref.removeValue();

    }

}