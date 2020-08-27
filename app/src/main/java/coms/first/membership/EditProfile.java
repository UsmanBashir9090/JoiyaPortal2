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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
    TextView fullName, Email, Phone, fathersName, CNIC, address, city, profession, designation, education,role, resetPass, status,  dob, timeStamp, status1;
    TextView textPic;
    ImageView profileImageView, imagePic;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    FirebaseStorage storage;

    DatabaseReference database;
    DatabaseReference memberDB;
    String uid;
    private DatePickerDialog.OnDateSetListener onDateSetListener,mDateSetListener;
    private TextView mDisplayDate;

    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        textPic = findViewById(R.id.textAddPic);
        imagePic = findViewById(R.id.imageAddPic);
        saveBtn = findViewById(R.id.submitBtn);
        Phone = findViewById(R.id.profilePhoneNo);
        fullName = findViewById(R.id.profileFullName);
        Email = findViewById(R.id.profileEmailAddress);
        fathersName = findViewById(R.id.profileFathersName);
        CNIC = findViewById(R.id.profileCNIC);
        address = findViewById(R.id.profileAddress);
        city = findViewById(R.id.profileCity);
        profession = findViewById(R.id.profileProfession);
        designation = findViewById(R.id.profileDesignation);
        education = findViewById(R.id.profileEducation);
        // dob = findViewById(R.id.profileDOB);
        mDisplayDate = (TextView) findViewById(R.id.profileDOB);
        status = findViewById(R.id.status);
        status1 = findViewById(R.id.status1);
        role = findViewById(R.id.role);
        timeStamp = findViewById(R.id.timestamp);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        userId = user.getUid();

        storageReference = FirebaseStorage.getInstance().getReference();

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
                address.setText(documentSnapshot.child("address").getValue(String.class));
                designation.setText(documentSnapshot.child("designation").getValue(String.class));
                profession.setText(documentSnapshot.child("profession").getValue(String.class));
                city.setText(documentSnapshot.child("city").getValue(String.class));
                mDisplayDate.setText(documentSnapshot.child("profileDob").getValue(String.class));
                timeStamp.setText(documentSnapshot.child("timestamp").getValue(String.class));
                status.setText(documentSnapshot.child("status").getValue(String.class));
                role.setText(documentSnapshot.child("role").getValue(String.class));
                status1.setText(documentSnapshot.child("status1").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

/*
        memberDB= FirebaseDatabase.getInstance().getReference("users");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String profileName = fullName.getText().toString();
                final String profileEmail = Email.getText().toString();
                final String profilePhone = Phone.getText().toString();
                final String profileFathersName = fathersName.getText().toString();
                final String profileCnic = CNIC.getText().toString().trim();
                final String profileEducation = education.getText().toString().trim();
                final String profileProfession = profession.getText().toString();
                final String profileDesignation = designation.getText().toString().trim();
                final String profileAddress = address.getText().toString().trim();
                final String profileCity = city.getText().toString().trim();
                final String profileDob = mDisplayDate.getText().toString();
                final String timestamp = timeStamp.getText().toString();
                final String Status = status.getText().toString();
                final String Role = role.getText().toString();
                final String Status1 = status1.getText().toString();

                if (TextUtils.isEmpty(profileFathersName)) {
                    fathersName.setError("Father's Name is required");
                    fathersName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(profileName)) {
                    fullName.setError("Name is required");
                    fullName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(profileEmail)) {
                    Email.setError("Email is required");
                    Email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(profilePhone)) {
                    Phone.setError("Phone Number is required");
                    Phone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(profileDob)) {
                    mDisplayDate.setError("Date of Birth is required");
                    mDisplayDate.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(profileCnic)) {
                    CNIC.setError("CNIC is required");
                    CNIC.requestFocus();
                    return;
                }
                if (CNIC.length() != 13) {
                    CNIC.setError("CNIC length should be 13 digits");
                    CNIC.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(profileEducation)) {
                    education.setError("Education is required");
                    education.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(profileProfession)) {
                    profession.setError("Profession is required");
                    profession.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(profileDesignation)) {
                    designation.setError("Designation is required");
                    designation.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(profileAddress)) {
                    address.setError("Address is required");
                    address.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(profileCity)) {
                    city.setError("City is required");
                    city.requestFocus();
                    return;
                }


                user=FirebaseAuth.getInstance().getCurrentUser();
                uid=user.getUid();

                memberData placeorder = new memberData( profileName, profileEmail, profilePhone, profileFathersName, profileProfession, profileDob, profileDesignation, profileEducation, profileAddress, profileCity, profileCnic, Role, timestamp, Status, Status1);
                memberDB.child(uid).setValue(placeorder);

                Toast.makeText(getApplicationContext(), "Profile Updated.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        fStore = FirebaseFirestore.getInstance();

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imagePic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Failed to load image.", Toast.LENGTH_SHORT).show();
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        imagePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });


/*
        Intent data = getIntent();
        final String fullName = data.getStringExtra("fName");
        String email = data.getStringExtra("email");
        final String phone = data.getStringExtra("phone");

        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAddress);
        profilePhone = findViewById(R.id.profilePhoneNo);
        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveProfileInfo);
        storageReference = FirebaseStorage.getInstance().getReference();

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfile.this, "Some field(s) are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email", email);
                        edited.put("fName", profileFullName.getText().toString());
                        edited.put("phone", profilePhone.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile updated.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, "Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        // Toast.makeText(EditProfile.this, "Email is changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        profileEmail.setText(email);
        profileFullName.setText(fullName);
        profilePhone.setText(phone);

        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + phone);
    }
*/ /*
        @Override
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
                            Picasso.get().load(uri).into(profileImageView);

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
*/ /*
    }
    @Override
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
*/

    }
}
