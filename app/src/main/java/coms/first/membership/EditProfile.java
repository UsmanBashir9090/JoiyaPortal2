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
import android.widget.Spinner;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class EditProfile extends AppCompatActivity {
    public static final String TAG1 = "TAG";
    TextView fullName, email, phone, fathersName, CNIC, address, city, profession, education, resetPass,  dob, tehsil, district, division;
    String timeStamp, role, shortStatus, longStatus;
    TextView textPic;
    ImageView profileImageView, imagePic;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    FirebaseStorage storage;
    Spinner spinnerProvince;
    Spinner spinnerDesignation;
    ArrayAdapter<CharSequence> adapterProvince;
    ArrayAdapter<CharSequence> adapterDesignation;

    DatabaseReference database;
    DatabaseReference memberDB;
    String uid;
    private DatePickerDialog.OnDateSetListener onDateSetListener,mDateSetListener;
    private TextView mDisplayDate;

    public static final String TAG = "TAG";
    public static final String TAG2 = TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        spinnerProvince = (Spinner) findViewById(R.id.editProvince);
        adapterProvince = ArrayAdapter.createFromResource(this, R.array.Province, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapterProvince);


        spinnerDesignation = (Spinner) findViewById(R.id.editDesignation);
        adapterDesignation = ArrayAdapter.createFromResource(this, R.array.Designation, android.R.layout.simple_spinner_item);
        adapterDesignation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDesignation.setAdapter(adapterDesignation);


        spinnerDesignation = (Spinner) findViewById(R.id.editDesignation);
        spinnerProvince = (Spinner) findViewById(R.id.editProvince);

        imagePic = findViewById(R.id.editImage);
        saveBtn = findViewById(R.id.saveButton);
        phone = findViewById(R.id.editPhone);
        fullName = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        fathersName = findViewById(R.id.editFatherName);
        CNIC = findViewById(R.id.editCNIC);
        address = findViewById(R.id.editAddress);
        city = findViewById(R.id.editCity);
        profession = findViewById(R.id.editProfession);

        tehsil = findViewById(R.id.editTehsil);
        division = findViewById(R.id.editDivision);
        district = findViewById(R.id.editDistrict);
        education = findViewById(R.id.editEducation);
        // dob = findViewById(R.id.profileDOB);
        mDisplayDate = (TextView) findViewById(R.id.editDOB);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        userId = user.getUid();

        storageReference = FirebaseStorage.getInstance().getReference();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        //Access the array kept in the string
        //Use int variables made to extract which array value of desig and prov was selected
        //figure out how to save "which spinner value was chosen by the user"


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot documentSnapshot) {
                phone.setText(documentSnapshot.child("phone").getValue(String.class));
                fullName.setText(documentSnapshot.child("name").getValue(String.class));
                email.setText(documentSnapshot.child("email").getValue(String.class));
                fathersName.setText(documentSnapshot.child("fatherName").getValue(String.class));
                CNIC.setText(documentSnapshot.child("cnic").getValue(String.class));
                education.setText(documentSnapshot.child("education").getValue(String.class));
                address.setText(documentSnapshot.child("address").getValue(String.class));
                profession.setText(documentSnapshot.child("profession").getValue(String.class));
                city.setText(documentSnapshot.child("city").getValue(String.class));
                mDisplayDate.setText(documentSnapshot.child("profileDob").getValue(String.class));
                tehsil.setText(documentSnapshot.child("tehsil").getValue(String.class));
                district.setText(documentSnapshot.child("district").getValue(String.class));
                division.setText(documentSnapshot.child("division").getValue(String.class));
                timeStamp = (documentSnapshot.child("timestamp").getValue(String.class));
                role = (documentSnapshot.child("role").getValue(String.class));
                shortStatus = (documentSnapshot.child("status1").getValue(String.class));
                longStatus = (documentSnapshot.child("status").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imagePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(EditProfile.this, android.R.style.Theme_Black, mDateSetListener, year, month, day);
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


        memberDB= FirebaseDatabase.getInstance().getReference("users");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Name = fullName.getText().toString();
                final String Email = email.getText().toString();
                final String Phone = phone.getText().toString();
                final String FathersName = fathersName.getText().toString();
                final String Cnic = CNIC.getText().toString().trim();
                final String Education = education.getText().toString().trim();
                final String Profession = profession.getText().toString();
                final String Designation = (String) spinnerDesignation.getSelectedItem().toString();
                final String Province = (String) spinnerProvince.getSelectedItem().toString();
                final String Address = address.getText().toString().trim();
                final String City = city.getText().toString().trim();
                final String Dob = mDisplayDate.getText().toString();
                final String Tehsil = tehsil.getText().toString();
                final String District = district.getText().toString();
                final String Division = division.getText().toString();
                final String time = timeStamp;
                final String Role = role;
                final String shortstatus = shortStatus;
                final String longstatus = longStatus;

                if (TextUtils.isEmpty(FathersName)) {
                    fathersName.setError("Father's Name is required");
                    fathersName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Name)) {
                    fullName.setError("Name is required");
                    fullName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Email)) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Phone)) {
                    phone.setError("Phone Number is required");
                    phone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Dob)) {
                    mDisplayDate.setError("Date of Birth is required");
                    mDisplayDate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Cnic)) {
                    CNIC.setError("CNIC is required");
                    CNIC.requestFocus();
                    return;
                }
                if (CNIC.length() != 13) {
                    CNIC.setError("CNIC length should be 13 digits");
                    CNIC.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Education)) {
                    education.setError("Education is required");
                    education.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Profession)) {
                    profession.setError("Profession is required");
                    profession.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Address)) {
                    address.setError("Address is required");
                    address.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(City)) {
                    city.setError("City is required");
                    city.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(Tehsil)){
                    tehsil.setError("Tehsil is required");
                    tehsil.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(Division)){
                    division.setError("Division is required");
                    division.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(District)){
                    district.setError("District is required");
                    district.requestFocus();
                    return;
                }


                user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();

                String status_province = shortstatus + "_" + Province;
                Log.d(TAG, Province);
                memberData placeorder = new memberData(Name, Email, Phone, FathersName, Profession, Dob, Designation, Education, Address, City, Cnic, Tehsil, District, Division, Province, Role, time, longstatus, shortstatus,  status_province);
                memberDB.child(uid).setValue(placeorder);
                Toast.makeText(getApplicationContext(), "Form Submitted Successfully", Toast.LENGTH_SHORT).show();
                 if(Role=="admin"|| Role=="superAdmin"){
                    Intent intent = new Intent(EditProfile.this, adminProfile.class);
                    startActivity(intent);
                    finish();
                } else {
                     Intent intent = new Intent(EditProfile.this, MainActivity.class);
                     startActivity(intent);
                     finish();
                 }


            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();


        StorageReference profileRef = storageReference.child("users/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imagePic);
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
                Toast.makeText(EditProfile.this, "Profile Picture added successfully.", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(EditProfile.this, "Image Uploaded.", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imagePic);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
