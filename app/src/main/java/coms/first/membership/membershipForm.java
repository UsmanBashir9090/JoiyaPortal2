package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class membershipForm extends AppCompatActivity {
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    public boolean form = false;
    EditText profileFullName, profileEmail, profilePhone, profileFathersName, profileCNIC, profileEducation, profileProfession, profileAddress, profileCity, profileTehsil,  profileDistrict, profileDivision;
    ImageView profileImageView, profilePicImage;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    String userId;
    DatabaseReference database;
    DatabaseReference memberDB;
    String uid;
    ProgressBar saveProgressBar;
    private TextView mDisplayDate;
    StorageReference storageReference;
    TextView profilePicText;
    private DatePickerDialog.OnDateSetListener onDateSetListener, mDateSetListener;
    Spinner spinnerProvince;
    Spinner spinnerDesignation;
    ArrayAdapter<CharSequence> adapterProvince;
    ArrayAdapter<CharSequence> adapterDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_form);
        Toast.makeText(this, "Form opened.", Toast.LENGTH_SHORT).show();

        spinnerDesignation = (Spinner) findViewById(R.id.Designation);
        adapterDesignation = ArrayAdapter.createFromResource(this, R.array.Designation, android.R.layout.simple_spinner_item);
        adapterDesignation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDesignation.setAdapter(adapterDesignation);


        spinnerProvince = (Spinner) findViewById(R.id.Province);
        adapterProvince = ArrayAdapter.createFromResource(this, R.array.Province, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapterProvince);



        profilePicImage = findViewById(R.id.imagePic);
        mDisplayDate = (TextView) findViewById(R.id.enterDOB);
        profileProfession = findViewById(R.id.enterProfession);
        profileFathersName = findViewById(R.id.enterFatherName);
        profileCNIC = findViewById(R.id.enterCNIC);
        profileEducation = findViewById(R.id.enterEducation);

        spinnerDesignation = (Spinner) findViewById(R.id.Designation);
        spinnerProvince = (Spinner) findViewById(R.id.Province);

 //       profileProvince = obj.getProvince();
        profileAddress = findViewById(R.id.enterAddress);
        profileCity = findViewById(R.id.enterCity);
        profileFullName = (EditText)findViewById(R.id.enterName);
        profileFullName.setSelection(profileFullName.getText().length());
        profileEmail = findViewById(R.id.enterEmail);
        profilePhone = findViewById(R.id.enterPhone);
        profileTehsil = findViewById(R.id.enterTehsil);
        profileDistrict = findViewById(R.id.enterDistrict);
        profileDivision = findViewById(R.id.enterDivision);
        saveBtn = findViewById(R.id.continueButton);
        profileEmail.setSelection(profileEmail.getText().length());
        profileProfession.setSelection(profileProfession.getText().length());
        profileEducation.setSelection(profileEducation.getText().length());
        profilePhone.setSelection(profilePhone.getText().length());
        profileFathersName.setSelection(profileFathersName.getText().length());
        profileAddress.setSelection(profileAddress.getText().length());
        profileCNIC.setSelection(profileCNIC.getText().length());
        profileTehsil.setSelection(profileTehsil.getText().length());
        profileDistrict.setSelection(profileDistrict.getText().length());
        profileDivision.setSelection(profileDivision.getText().length());


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        userId = user.getUid();

        storageReference = FirebaseStorage.getInstance().getReference();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

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
        fStore = FirebaseFirestore.getInstance();

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePicImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(membershipForm.this, "Failed to load image.", Toast.LENGTH_SHORT).show();
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        profilePicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });



        memberDB = FirebaseDatabase.getInstance().getReference("users");



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = profileFullName.getText().toString();
                final String email = profileEmail.getText().toString();
                final String phone = profilePhone.getText().toString();
                final String fathersName = profileFathersName.getText().toString();
                final String CNIC = profileCNIC.getText().toString().trim();
                final String education = profileEducation.getText().toString().trim();
                final String profession = profileProfession.getText().toString();
                final String designation = spinnerDesignation.getSelectedItem().toString();
                final String province = spinnerProvince.getSelectedItem().toString();
                final String address = profileAddress.getText().toString().trim();
                final String city = profileCity.getText().toString().trim();
                final String dob = mDisplayDate.getText().toString();
                final String tehsil = profileTehsil.getText().toString();
                final String district = profileDistrict.getText().toString();
                final String division = profileDivision.getText().toString();
                final String member;
                final String prov_app_name = "";
                final String prov_approval = "pending";
                final String pres_approval;
                if(designation == "Member") {
                    pres_approval = "Approved";
                    member = "1";
                } else{
                    pres_approval="pending";
                    member = "0";
                }
                Log.d(TAG1, "Profile Designation is " + designation + " Profile Province is " + province);

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
                if(TextUtils.isEmpty(tehsil)){
                    profileTehsil.setError("Tehsil is required");
                    profileTehsil.requestFocus();
                    return;
                }


                user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();
                String role = "user";
                String status = "Your Application is Pending";
                String status1 = "pending";

                final String status_province = status1 + "_" + province;
                final String status_member = prov_approval + "_" + member;
                memberData placeorder = new memberData(name, email, phone, fathersName, profession, dob, designation, education, address, city, CNIC, tehsil, district, division, province, role, getDateCurrentTimeZone(ServerValue.TIMESTAMP), status, status1,status_province, status_member, prov_approval, pres_approval, prov_app_name);
                memberDB.child(uid).setValue(placeorder);


                Toast.makeText(getApplicationContext(), "Form Submitted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(membershipForm.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


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

    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){

                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);
                Toast.makeText(membershipForm.this, "Profile Picture added successfully.", Toast.LENGTH_SHORT).show();

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        // upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");

        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(membershipForm.this, "Image Uploaded.", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePicImage);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(membershipForm.this, "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}