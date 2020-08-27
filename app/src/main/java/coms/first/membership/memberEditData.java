package coms.first.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class memberEditData extends AppCompatActivity {
    public String name, email, fatherName, profession, profileDob, designation, education, address, city, phone, cnic;
    ;


    public memberEditData(){

    }

    public memberEditData(String name, String email, String phone, String fatherName, String profession, String profileDob, String designation, String education, String address, String city, String cnic) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fatherName = fatherName;
        this.profession = profession;
        this.profileDob = profileDob;
        this.designation = designation;
        this.education = education;
        this.address = address;
        this.city = city;
        this.cnic = cnic;


    }

    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public String getCNIC() {return cnic;}
    public String getFatherName() {return fatherName;}
    public String getProfession() {return profession;}
    public String getEducation() {return education;}
    public String getDesignation() {return designation;}
    public String getAddress() {return address;}
    public String getCity() {return city;}

    public String getProfileDob() {
        return profileDob;
    }

}