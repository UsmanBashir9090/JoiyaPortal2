package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    EditText profileFullName, profileEmail, profilePhone, profileFathersName, profileCNIC, profileEducation, profileProfession, profileDesignation, profileAddress, profileCity;
    ImageView profileImageView, profilePicImage;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    DatabaseReference memberDB;
    String uid;
    ProgressBar saveProgressBar;
    private TextView mDisplayDate;
    TextView profilePicText;
    private DatePickerDialog.OnDateSetListener onDateSetListener,mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joiya_membership_form);
        Toast.makeText(this, "Form opened.", Toast.LENGTH_SHORT).show();

        profilePicImage = findViewById(R.id.imageAddPic);
        profilePicText = findViewById(R.id.textAddPic);
        mDisplayDate = (TextView) findViewById(R.id.profileDOB);
        profileProfession = findViewById(R.id.profileProfession);
        profileFathersName = findViewById(R.id.profileFathersName);
        profileCNIC = findViewById(R.id.profileCNIC);
        profileEducation = findViewById(R.id.profileEducation);
        profileDesignation = findViewById(R.id.profileDesignation);
        profileAddress = findViewById(R.id.profileAddress);
        profileCity = findViewById(R.id.profileCity);
        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAddress);
        profilePhone = findViewById(R.id.profilePhoneNo);
        saveBtn = findViewById(R.id.submit_h);

        fAuth = FirebaseAuth.getInstance();



       mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(JoiyaMembershipForm.this, android.R.style.Theme_Black, mDateSetListener, year,month,day );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                 //   Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                    String date = month +"/" + day +"/" + year ;
                    mDisplayDate.setText(date);
                }
            };

/*

        memberDB= FirebaseDatabase.getInstance().getReference("users");

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
                final String designation = profileDesignation.getText().toString().trim();
                final String address = profileAddress.getText().toString().trim();
                final String city = profileCity.getText().toString().trim();
                final String dob = mDisplayDate.getText().toString();


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
                if (TextUtils.isEmpty(designation)) {
                    profileDesignation.setError("Designation is required");
                    profileDesignation.requestFocus();
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


                user=FirebaseAuth.getInstance().getCurrentUser();
                uid=user.getUid();
                String role="user";
                String status="Your Application is Pending";
                String status1="pending";

                memberData placeorder = new memberData( name, email, phone, fathersName, profession, dob, designation, education, address, city, CNIC, role, getDateCurrentTimeZone(ServerValue.TIMESTAMP), status, status1);
                memberDB.child(uid).setValue(placeorder);

                Toast.makeText(getApplicationContext(), "Form Submitted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JoiyaMembershipForm.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
*/
      /*  StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePicImage);
            }
        });

        profilePicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

*/

      /*  profilePicText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        }); */

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

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){

                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);
                Toast.makeText(JoiyaMembershipForm.this, "Profile Picture added successfully.", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(JoiyaMembershipForm.this, "Image Uploaded.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(JoiyaMembershipForm.this, "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/
}