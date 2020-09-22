package coms.first.membership;


public class memberData {
    public String name, email,status_member, fatherName, profession, profileDob, designation, education, address, city, phone, cnic, role, timestamp, status, status1, tehsil, district,division, province, status_province, provincial_approval, president_approval, prov_app_name;
     ;


     public memberData(){

     }

    public memberData(String name, String email, String phone, String fatherName, String profession, String profileDob, String designation, String education, String address, String city, String cnic, String tehsil, String district, String division, String province, String role, String timestamp, String status, String status1, String status_province, String status_member, String provincial_approval, String prov_app_name, String president_approval) {
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
        this.role =role;
        this.timestamp=timestamp;
        this.status=status;
        this.status1=status1;
        this.tehsil=tehsil;
        this.division=division;
        this.district=district;
        this.province = province;
        this.status_province = status_province;
        this.status_member = status_member;
        this.provincial_approval = provincial_approval;
        this.president_approval = president_approval;
        this.prov_app_name = prov_app_name;
     }

    public void setProvince(String province){this.province = province;}

    public String getProv_app_name() {return prov_app_name;}
    public String getProvincial_approval() {return provincial_approval;}
    public String getPresident_approval() {return president_approval;}
    public String getStatus_member() {return status_member;}
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
    public String getTehsil(){return tehsil;}
    public String getDivision(){return division;}
    public String getDistrict(){return district;}
    public String getProvince() {return province;}
    public String getStatus_province(){return status_province;}

    public String getProfileDob() {
        return profileDob;
    }

    public String getRole() {
        return role;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getStatus1() {
        return status1;
    }
}

