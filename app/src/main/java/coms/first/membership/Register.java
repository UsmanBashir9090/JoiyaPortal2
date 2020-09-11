package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {
    public static final String TAG = "Tag";
    public static final String TAG1 = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn, testButton;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
   // FirebaseFirestore fStore;
    String userID;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


      //  mFullName    = findViewById(R.id.Name);
        mEmail       = findViewById(R.id.email);
        mPassword    = findViewById(R.id.password);
      //  mPhone       = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn    = findViewById(R.id.LoginTEXT);
        fAuth        = FirebaseAuth.getInstance();
     //   fStore       = FirebaseFirestore.getInstance();
        progressBar  = findViewById(R.id.progressBar);


       // String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
       // database = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
       database = FirebaseDatabase.getInstance().getReference().child("users");

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
               // final String fullName = mFullName.getText().toString();
               // final String phone = mPhone.getText().toString();
                final String role="user";

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }





                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Please enter a valid email");
                    mEmail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    mPassword.requestFocus();
                    return;
                }
                if(password.length() < 8){
                    mPassword.setError("Password must be at least 8 characters");
                    mPassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase


                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role")
                                    .setValue("user").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(Register.this, membershipForm.class);
                                    startActivity(intent);
                                    Toast.makeText(Register.this, "Opening form.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
        }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        Log.d("KEY", "THIS IS A TEST MESSAGE HELLO THHERE");
        printKeyHash();
    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("coms.first.membership",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature: info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }




}