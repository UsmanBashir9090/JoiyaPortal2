package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class approved_list extends AppCompatActivity {
    ListView lv;
    FirebaseListAdapter adapter2;
    DatabaseReference databaseReference, db;
    String itemKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_list);
        this.setTitle("Approved Requests");

        lv=(ListView) findViewById(R.id.LV);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = databaseReference.orderByChild("status1").equalTo("Approved");

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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                Intent DeleteUpdate=  new Intent(approved_list.this, approved_list_details.class);
                memberData user= (memberData) adapterView.getItemAtPosition(position);
                DatabaseReference itemRef = adapter2.getRef(position);
                String itemKey = itemRef.getKey();
                DeleteUpdate.putExtra("email", user.getEmail());
                DeleteUpdate.putExtra("name", user.getName());
                DeleteUpdate.putExtra("key", itemKey);
                DeleteUpdate.putExtra("phone", user.getPhone());
                DeleteUpdate.putExtra("address", user.getAddress());
                DeleteUpdate.putExtra("city", user.getCity());
                DeleteUpdate.putExtra("fathername", user.getFatherName());
                DeleteUpdate.putExtra("cnic", user.getCNIC());
                DeleteUpdate.putExtra("designation", user.getDesignation());
                DeleteUpdate.putExtra("dob", user.getProfileDob());
                DeleteUpdate.putExtra("status", user.getStatus1());
                DeleteUpdate.putExtra("education", user.getEducation());
                DeleteUpdate.putExtra("profession", user.getProfession());
                DeleteUpdate.putExtra("timestamp", user.getTimestamp());


                startActivity(DeleteUpdate);
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