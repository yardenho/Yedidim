package com.example.yedidim.Model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Report {
//    static private long idCounter = 0;  //TODO: אולי אין בזה צורך (לבדוק אם למחוק)
    @PrimaryKey
    @NonNull
    private String reportID;
    private String problem;
    private String notes;
    private String userName;
    //    private ImageView image; // כשנוסיף העלאת תמונה אולי נשנה את הסוג
    private double longitude;
    private double latitude;

    public Report(){
    }
    public Report(String problem, String notes, String u, ImageView iv, double longitude, double latitude){
//        this.reportID= idCounter;
        this.problem=problem;
        this.notes=notes;
        this.userName=u;
//        this.image=iv;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    // setters
    public void setReportID(String reportID) {this.reportID = reportID;}
    public void setProblem(String p){
        this.problem = p;
    }
    public void setNotes(String n){
        this.notes = n;
    }
    //    public void setImage(ImageView iv){
//        this.image = iv;  // TODO: need to set for image
//    }
    public void setLongitude(double lo) {this.longitude = lo;}
    public void setLatitude(double la) {this.latitude = la;}
    public void setUserName(String userName) {this.userName = userName;}
//    static public void addOneToIdCounter(){ idCounter +=1;}


    //getters
    public String getUserName() {return userName;}
    public String getProblem() {return problem;}
    public String getNotes() {return notes;}
    public String getReportID() {return reportID;}
    //    public ImageView getImage() {return image;}   // TODO: need to add get for image
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getLocation() {
        return "Ashdod";
        //TODO לחשב את המיקום ולהחזיר}
    }
//    static public long getIdCounter(){return idCounter;}

}
