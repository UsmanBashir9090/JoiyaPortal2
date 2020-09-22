package coms.first.membership;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView fullName, status_Province, Email, Phone, fathersName, CNIC, address, city, profession, designation, education, resetPass, status, timestamp, dob, editInfo, tehsil, district, division, province;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button changeProfileImage ;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;
    DatabaseReference database;
    String status_province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Profile");

        tehsil = findViewById(R.id.profileTeshil);
        district = findViewById(R.id.profileDistrict);
        division = findViewById(R.id.profileDivision);
        province = findViewById(R.id.profileProvince);
        profileImage = findViewById(R.id.profilePicture);
        editInfo = findViewById(R.id.editInfo);
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
        resetPass = findViewById(R.id.resetpassword);

        //   profileImage = findViewById(R.id.profileImage);
        //   changeProfileImage = findViewById(R.id.changeProfile);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        StorageReference profileRef = storageReference.child("users/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            Toast.makeText(this, "Log in again.", Toast.LENGTH_SHORT).show();
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
                address.setText(documentSnapshot.child("address").getValue(String.class));
                designation.setText(documentSnapshot.child("designation").getValue(String.class));
                profession.setText(documentSnapshot.child("profession").getValue(String.class));
                city.setText(documentSnapshot.child("city").getValue(String.class));
                status.setText(documentSnapshot.child("status1").getValue(String.class));
                timestamp.setText(documentSnapshot.child("timestamp").getValue(String.class));
                dob.setText(documentSnapshot.child("profileDob").getValue(String.class));
                tehsil.setText(documentSnapshot.child("tehsil").getValue(String.class));
                district.setText(documentSnapshot.child("district").getValue(String.class));
                division.setText(documentSnapshot.child("division").getValue(String.class));
                province.setText(documentSnapshot.child("province").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditProfile.class);
                startActivity(intent);
            }
        });


        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetPassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter New Password at least 8 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();

            }
        });



   /*     changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open gallery
           Intent i = new Intent(view.getContext(),EditProfile.class);
           i.putExtra("fName", fullName.getText().toString());
           i.putExtra("email", Email.getText().toString());
           i.putExtra("phone", Phone.getText().toString());
           startActivity(i);


            //  Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             //   startActivityForResult(openGalleryIntent, 1000);

            }
        });   */


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.menuUser:

                startActivity(new Intent(this, activityFacebook.class));

                break;


            }

            return true;
        }




  /*  public void logout (View view){

            FirebaseAuth.getInstance().signOut(); //logout
            startActivity(new Intent(getApplication(), Login.class));
            finish();

        }  */

    }



