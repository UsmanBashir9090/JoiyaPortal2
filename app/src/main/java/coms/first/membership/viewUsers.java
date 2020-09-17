package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class viewUsers extends AppCompatActivity {
    ListView lv;
    FirebaseListAdapter adapter2;
    DatabaseReference databaseReference;
    String itemKey;
    StorageReference storageReference;
    FirebaseUser user;
    String id;
    String province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        this.setTitle("All Users");

        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();

        lv = (ListView) findViewById(R.id.LV2);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


        Query query = databaseReference.orderByChild("name").equalTo("pending_Sindh");
    }
}