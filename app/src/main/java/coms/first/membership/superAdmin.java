package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import coms.first.membership.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class superAdmin extends AppCompatActivity {
    TextView editInfo, resetPass, userName;
    FirebaseUser user;
    FirebaseAuth fAuth;
    DatabaseReference database;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

        userName = findViewById(R.id.userName);


        fAuth = FirebaseAuth.getInstance();

        Button plcord = (Button) findViewById(R.id.orders);
        plcord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startplcord = new Intent(getApplicationContext(), pending_list.class);
                startActivity(startplcord);
            }
        });

        Button cord = (Button) findViewById(R.id.dispatched_orders);
        cord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startplcord = new Intent(getApplicationContext(), approved_list.class);
                startActivity(startplcord);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId= user.getUid();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot documentSnapshot) {
                userName.setText(documentSnapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.admin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login.class) );

                break;
            case R.id.generateCard:

                startActivity(new Intent(this, activityFacebook.class));
                break;

            case R.id.viewProfile:

                startActivity(new Intent(this, adminProfile.class));
                break;


        }


        return true;
    }

}