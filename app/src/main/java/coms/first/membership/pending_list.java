package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class pending_list extends AppCompatActivity {
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG1";
    ListView lv;
    FirebaseListAdapter adapter2;
    DatabaseReference databaseReference, database;
    String itemKey;
    FirebaseStorage fstorage;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    FirebaseUser user;
    String userId;
    String province;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_list);
        this.setTitle("Pending Requests");

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId= user.getUid();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                province = snapshot.child("province").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        lv=(ListView) findViewById(R.id.LV);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = databaseReference.orderByChild("status_province").equalTo("pending_Sindh");

        Log.d(TAG, "Province is " + province);

        FirebaseListOptions<memberData> options= new FirebaseListOptions.Builder<memberData>()
                .setLayout(R.layout.allmemberdata)
                .setQuery(query, memberData.class)
                .build();




        adapter2= new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                DatabaseReference itemRef = getRef(position);
                itemKey = itemRef.getKey();
                TextView name= v.findViewById(R.id.name);
                TextView email= v.findViewById(R.id.status);
                TextView phone= v.findViewById(R.id.phone);


                memberData user = (memberData) model;
                name.setText("Member Name: " + user.getName());
                phone.setText("Contact No. : " + user.getPhone());
                email.setText("Status: " + user.getStatus1());

            }
        };
        lv.setAdapter(adapter2);

        storageReference = FirebaseStorage.getInstance().getReference();

/*
        StorageReference profileRef = storageReference.child("users/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                Intent Deletepdate=  new Intent(pending_list.this, pending_list_details.class);
                memberData user= (memberData) adapterView.getItemAtPosition(position);
                DatabaseReference itemRef = adapter2.getRef(position);
                String itemKe = itemRef.getKey();
                Deletepdate.putExtra("email", user.getEmail());
                Deletepdate.putExtra("name", user.getName());
                Deletepdate.putExtra("key", itemKe);
                Deletepdate.putExtra("phone", user.getPhone());
                Deletepdate.putExtra("address", user.getAddress());
                Deletepdate.putExtra("city", user.getCity());
                Deletepdate.putExtra("fathername", user.getFatherName());
                Deletepdate.putExtra("cnic", user.getCNIC());
                Deletepdate.putExtra("designation", user.getDesignation());
                Deletepdate.putExtra("dob", user.getProfileDob());
                Deletepdate.putExtra("status", user.getStatus1());
                Deletepdate.putExtra("education", user.getEducation());
                Deletepdate.putExtra("profession", user.getProfession());
                Deletepdate.putExtra("timestamp", user.getTimestamp());
                Deletepdate.putExtra("province", user.getProvince());


                startActivity(Deletepdate);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter2.stopListening();
    }



}