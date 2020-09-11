package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Spinners{
    public static final String TAG = "TAG";
    public static final String TAG1 = TAG;
    String Designation;
    String Province;

    public Spinners(){



    }
    public void setDesignation(String a){
        if(a == null){
            Log.d(TAG1, "Null designation received");
        } else {
            this.Designation = a;
        }
    }
    public String getDesignation(){
        return this.Designation;
    }

    public void setProvince(String b){
        if(b == null){
            Log.d(TAG1, "Null designation received");
        } else {
            this.Province = b;
        }
    }
    public String getProvince(){
        return this.Province;
    }
}

