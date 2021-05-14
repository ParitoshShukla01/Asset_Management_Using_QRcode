package com.kalaathon.aircraftassetmanagement.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private String empid;
    private String verify;

    public User(String name, String empid, String verify) {
        this.name = name;
        this.empid = empid;
        this.verify = verify;
    }

    public User() {

    }

    protected User(Parcel in) {
        name = in.readString();
        empid = in.readString();
        verify = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getVerify() {  return verify;  }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(empid);
        parcel.writeString(verify);
    }
}
