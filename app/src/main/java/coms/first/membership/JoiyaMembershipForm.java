/*
package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class JoiyaMembershipForm extends AppCompatActivity {
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    public boolean form = false;
    EditText profileFullName, profileEmail, profilePhone, profileFathersName, profileCNIC, profileEducation, profileProfession, profileAddress, profileCity, profileTehsil,  profileDistrict, profileDivision;
    String profileProvince, profileDesignation;
    ImageView profileImageView, profilePicImage;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference memberDB;
    String uid;
    ProgressBar saveProgressBar;
    private TextView mDisplayDate;
    TextView profilePicText;
    private DatePickerDialog.OnDateSetListener onDateSetListener, mDateSetListener;
    Spinner spinnerProvince, spinnerDesignation;
    ArrayAdapter<CharSequence> adapterProvince;
    ArrayAdapter<CharSequence> adapterDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joiya_membership_form);
        Toast.makeText(this, "Form opened.", Toast.LENGTH_SHORT).show();
/*
        final String[] designation = new String[1];
        spinnerDesignation = (Spinner) findViewById(R.id.Designation);
        adapterDesignation = ArrayAdapter.createFromResource(this, R.array.Designation, android.R.layout.simple_spinner_item);
        adapterDesignation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDesignation.setAdapter(adapterDesignation);
        spinnerDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " selected.", Toast.LENGTH_LONG).show();
                designation[0] = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(0) + " selected.", Toast.LENGTH_LONG).show();
                designation[0] = (String) adapterView.getItemAtPosition(0);
            }
        });
        final String[] province = new String[1];
        spinnerProvince = (Spinner) findViewById(R.id.Province);
        adapterProvince = ArrayAdapter.createFromResource(this, R.array.Province, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapterProvince);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " selected.", Toast.LENGTH_LONG).show();
                province[0] = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(0) + " selected.", Toast.LENGTH_LONG).show();
                province[0] = (String) adapterView.getItemAtPosition(0);
            }
        });

        final String[] designation= {""};
        final String[] province= {""};

        profilePicImage = findViewById(R.id.imageAddPic);
     //   profilePicText = findViewById(R.id.textAddPic);
        mDisplayDate = (TextView) findViewById(R.id.enterDOB);
        profileProfession = findViewById(R.id.enterProfession);
        profileFathersName = findViewById(R.id.enterFatherName);
        profileCNIC = findViewById(R.id.enterCNIC);
        profileEducation = findViewById(R.id.enterEducation);
        profileDesignation = designation[0];
        profileProvince = province[0];
        profileAddress = findViewById(R.id.enterAddress);
        profileCity = findViewById(R.id.enterCity);
        profileFullName = findViewById(R.id.enterName);
        profileEmail = findViewById(R.id.enterEmail);
        profilePhone = findViewById(R.id.enterPhone);
        profileTehsil = findViewById(R.id.enterTehsil);
        profileDistrict = findViewById(R.id.enterDistrict);
        profileDivision = findViewById(R.id.enterDivision);
        saveBtn = findViewById(R.id.continueButton);

        fAuth = FirebaseAuth.getInstance();


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(membershipForm.this, android.R.style.Theme_Black, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //   Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

*/
    //    memberDB = FirebaseDatabase.getInstance().getReference("users");

     /*   saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = profileFullName.getText().toString();
                final String email = profileEmail.getText().toString();
                final String phone = profilePhone.getText().toString();
                final String fathersName = profileFathersName.getText().toString();
                final String CNIC = profileCNIC.getText().toString().trim();
                final String education = profileEducation.getText().toString().trim();
                final String profession = profileProfession.getText().toString();
                final String designation = profileDesignation;
                final String province = profileProvince;
                final String address = profileAddress.getText().toString().trim();
                final String city = profileCity.getText().toString().trim();
                final String dob = mDisplayDate.getText().toString();
                final String tehsil = profileTehsil.getText().toString();
                final String district = profileDistrict.getText().toString();
                final String division = profileDivision.getText().toString();

                if (TextUtils.isEmpty(fathersName)) {
                    profileFathersName.setError("Father's Name is required");
                    profileFathersName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    profileFullName.setError("Name is required");
                    profileFullName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    profileEmail.setError("Email is required");
                    profileEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    profilePhone.setError("Phone Number is required");
                    profilePhone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(dob)) {
                    mDisplayDate.setError("Date of Birth is required");
                    mDisplayDate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(CNIC)) {
                    profileCNIC.setError("CNIC is required");
                    profileCNIC.requestFocus();
                    return;
                }
                if (CNIC.length() != 13) {
                    profileCNIC.setError("CNIC length should be 13 digits");
                    profileCNIC.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(education)) {
                    profileEducation.setError("Education is required");
                    profileEducation.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(profession)) {
                    profileProfession.setError("Profession is required");
                    profileProfession.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    profileAddress.setError("Address is required");
                    profileAddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    profileCity.setError("City is required");
                    profileCity.requestFocus();
                    return;
                }


                user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();
                String role = "user";
                String status = "Your Application is Pending";
                String status1 = "pending";

                memberData placeorder = new memberData(name, email, phone, fathersName, profession, dob, designation, education, address, city, CNIC, tehsil, district, division, province, role, getDateCurrentTimeZone(ServerValue.TIMESTAMP), status, status1);
                memberDB.child(uid).setValue(placeorder);

                Toast.makeText(getApplicationContext(), "Form Submitted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(membershipForm.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    }
    public  String getDateCurrentTimeZone(Map<String, String> timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone("Asia/karachi");
            //   calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aaa");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
}
*/