package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class splash extends AppCompatActivity {
    public static int SPLASH_TIME_OUT=3000;
    FirebaseAuth mAuth;
    DatabaseReference userdb;
    FirebaseUser user;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        userdb = FirebaseDatabase.getInstance().getReference().child("users");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() != null) {
                    user=FirebaseAuth.getInstance().getCurrentUser();
                    uid=user.getUid();
                    userdb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user=FirebaseAuth.getInstance().getCurrentUser();
                            uid=user.getUid();
                            String role = dataSnapshot.child(uid).child("role").getValue(String.class);
                            try {
                                if (role.equals("admin")) {
                                    Intent intent = new Intent(splash.this, admin.class);
                                    startActivity(intent);
                                } if (role.equals("superAdmin")) {
                                    Intent intent = new Intent(splash.this, superAdmin.class);
                                    startActivity(intent);
                                }
                                else {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                startActivity(new Intent(getApplicationContext(), Register.class));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                }
                else{
                    Intent intent = new Intent(splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }
}