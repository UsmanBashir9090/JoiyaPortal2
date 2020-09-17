package coms.first.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.renderscript.Sampler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;



public class activityFacebook extends AppCompatActivity {
    TextView Name, Designation, Tehsil, District, Province, City;
    ImageView image;
    Button btnBack;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    FirebaseAuth fAuth;
    Uri uriPath;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userID;
    FirebaseUser user;
    DatabaseReference database;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        this.setTitle("APJQI Membership Card");

        Name = findViewById(R.id.testName);
        Designation = findViewById(R.id.testDesignation);
        Tehsil = findViewById(R.id.testTehsil);
        District = findViewById(R.id.testDistrict);
        Province = findViewById(R.id.testProvince);
        City = findViewById(R.id.testCity);
        image = findViewById(R.id.testPicture);

        btnBack = (Button) findViewById(R.id.btnBack);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            Toast.makeText(this, "Log in again.", Toast.LENGTH_SHORT).show();
            finish();
        }

        userID = user.getUid();

        database = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot documentSnapshot) {
                Name.setText(documentSnapshot.child("name").getValue(String.class));
                Designation.setText(documentSnapshot.child("designation").getValue(String.class));
                Tehsil.setText(documentSnapshot.child("tehsil").getValue(String.class));
                District.setText(documentSnapshot.child("district").getValue(String.class));
                Province.setText(documentSnapshot.child("province").getValue(String.class));
                City.setText(documentSnapshot.child("city").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //adding profile image to the imageView

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(image);
            }
        });
        // adding image field

/*
        StorageReference profileRef = storageReference.child("users/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(image);
            }
        });
*/

        //Init View

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

    }

    public void ScreenshotButton(View view) {

        View view1 = getWindow().getDecorView().getRootView();
        view1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);


        String filePath = Environment.getExternalStorageDirectory() + "/Download/" + Calendar.getInstance().getTime().toString() + ".jpg";
        File fileScreenshot = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(fileScreenshot);
        uriPath = uri;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileScreenshot));
        intent.setType("image/png");
        startActivity(Intent.createChooser(intent, "Share Image Via"));


    }


    private void screenshot() {
        Date date = new Date();
        CharSequence now = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        String filename = Environment.getExternalStorageDirectory() + "/Screenshot/" + now + ".JPG";

        View root = getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        Toast.makeText(this, "Creating Bitmap", Toast.LENGTH_SHORT).show();
        root.setDrawingCacheEnabled(false);

        File file = new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(this, "compressing bitmap", Toast.LENGTH_SHORT).show();
            out.flush();
            out.close();

            Uri uri = Uri.fromFile(file);
            uriPath = uri;
            Toast.makeText(this, "Starting intent", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*private void printKeyHash() {
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
*/

}

