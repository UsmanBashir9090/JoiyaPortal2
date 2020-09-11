package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class approved_list_details extends AppCompatActivity {

    TextView fullName, Email, uid, Phone, fathersName, CNIC, address, city, profession, designation, education, status, timestamp, dob;
    FirebaseUser user;
    DatabaseReference database, ref;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_list_details);



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
    }



}