package com.kalaathon.aircraftassetmanagement.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Records implements Parcelable {

    private String date,empid,operation,status;

    protected Records(Parcel in) {
        date = in.readString();
        empid = in.readString();
        operation = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(empid);
        dest.writeString(operation);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Records> CREATOR = new Creator<Records>() {
        @Override
        public Records createFromParcel(Parcel in) {
            return new Records(in);
        }

        @Override
        public Records[] newArray(int size) {
            return new Records[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Creator<Records> getCREATOR() {
        return CREATOR;
    }

    public Records(String date, String empid, String operation, String status) {
        this.date = date;
        this.empid = empid;
        this.operation = operation;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Records{" +
                "date='" + date + '\'' +
                ", empid='" + empid + '\'' +
                ", operation='" + operation + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Records() {
    }
}
