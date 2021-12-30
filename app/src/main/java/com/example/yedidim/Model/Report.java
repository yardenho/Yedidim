package com.example.yedidim.Model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Report {
    static private long idCounter = 0;
    @PrimaryKey
    @NonNull
    private long reportID;
    private String problem;
    private String notes;
    private String userName;
    //    private ImageView image; // כשנוסיף העלאת תמונה אולי נשנה את הסוג
    private double longitude;
    private double latitude;

    public Report(){
    }
    public Report(String problem, String notes, String u, ImageView iv, double longitude, double latitude){
        this.reportID= idCounter;
        this.problem=problem;
        this.notes=notes;
        this.userName=u;
//        this.image=iv;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    // setters
    public void setReportID(long reportID) {this.reportID = reportID;}
    public void setProblem(String p){
        this.problem = p;
    }
    public void setNotes(String n){
        this.notes = n;
    }
    //    public void setImage(ImageView iv){
//        this.image = iv;
//    }
    public void setLongitude(double lo) {this.longitude = lo;}
    public void setLatitude(double la) {this.latitude = la;}
    public void setUserName(String userName) {this.userName = userName;}
    static public void setIdCounter(long counter){ idCounter = counter;}


    //getters
    public String getUserName() {return userName;}
    public String getProblem() {return problem;}
    public String getNotes() {return notes;}
    public long getReportID() {return reportID;}
    //    public ImageView getImage() {return image;}
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    static public long getIdCounter(){return idCounter;}
}
