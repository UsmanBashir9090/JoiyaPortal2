package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class viewCard extends AppCompatActivity {

    TextView fullName, Email, Phone, fathersName, CNIC, city, profession, designation, education, status, uid, dob;
    Button back;
    ImageView cardSettings;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    DatabaseReference database, ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        uid = findViewById(R.id.userID);
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
        uid = findViewById(R.id.uid);

       //   final String key = getIntent().getExtras().get("key").toString();
     //   ref = FirebaseDatabase.getInstance().getReference().child("users").child(key);

        fullName.setText(getIntent().getStringExtra("name"));
        Email.setText(getIntent().getStringExtra("email"));
        city.setText(getIntent().getStringExtra("city"));
        Phone.setText(getIntent().getStringExtra("phone"));
        designation.setText(getIntent().getStringExtra("designation"));
        education.setText(getIntent().getStringExtra("education"));
        status.setText(getIntent().getStringExtra("status"));
      //  uid.setText(key);
        profession.setText(getIntent().getStringExtra("profession"));
        dob.setText(getIntent().getStringExtra("dob"));
        CNIC.setText(getIntent().getStringExtra("cnic"));
        fathersName.setText(getIntent().getStringExtra("fathername"));

    }
}