package com.example.yedidim.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yedidim.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Entity
public class Report {
//    static private long idCounter = 0;  //TODO: אולי אין בזה צורך (לבדוק אם למחוק)
    final static String PROBLEM = "problem";
    final static String NOTES = "notes";
    final static String USERNAME = "username";
    final static String REPORT_URL = "reportUrl";
    final static String LATITUDE = "latitude";
    final static String LONGITUDE = "longitude";
    public final static String LAST_UPDATED = "lastUpdated";
    final static String REPORTS_LAST_UPDATE = "REPORTS_LAST_UPDATE";




    @PrimaryKey
    @NonNull
    private String reportID;
    private String problem;
    private String notes;
    private String userName;
    private String reportUrl="";
    private Long lastUpdated = new Long(0);

    //    private ImageView image; // כשנוסיף העלאת תמונה אולי נשנה את הסוג
    private double longitude;
    private double latitude;

    public Report(){
    }
    public Report(String id, String problem, String notes,String rUrl, String user, double longitude, double latitude, Long lastUpdated){
        this.reportID= id;
        this.problem=problem;
        this.notes=notes;
        this.userName=user;
//        this.image=iv;
        this.longitude=longitude;
        this.latitude=latitude;
        this.reportUrl=rUrl;
        this.lastUpdated = lastUpdated;
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
    public void setReportUrl(String rUrl){
        reportUrl=rUrl;
    }
//    static public void addOneToIdCounter(){ idCounter +=1;}
    public void setLastUpdated(Long lastUpdated){
        this.lastUpdated = lastUpdated;
    }

    //getters
    public String getUserName() {return userName;}
    public String getProblem() {return problem;}
    public String getNotes() {return notes;}
    public String getReportID() {return reportID;}
    public String getReportUrl(){return reportUrl;}
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getLocation() {

        //TODO לחשב את המיקום ולהחזיר
        Geocoder geocoder = new Geocoder(MyApplication.getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String cityName = addresses.get(0).getAddressLine(0);
        String cityName = addresses.get(0).getAddressLine(0);
        return addresses.get(0).getAddressLine(0);
    }
//    static public long getIdCounter(){return idCounter;}

    public Long getLastUpdated(){
        return lastUpdated;
    }

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
//        json.put("reportID", report.getReportID());   the reportID comes from the document name
        json.put(PROBLEM, problem);
        json.put(NOTES,notes);
        json.put(USERNAME,userName);
        json.put(LONGITUDE,longitude);
        json.put(LATITUDE,latitude);
        json.put(REPORT_URL, reportUrl);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    static public Report fromJson(String reportId, Map<String, Object> json){
        String id = reportId;
        String problem = (String)json.get(PROBLEM);
        if(problem == null)
            return null;
        String notes = (String)json.get(NOTES);
        String userName = (String)json.get(USERNAME);
        if(userName == null)
            return null;
        String reportUrl = (String)json.get(REPORT_URL);
        double latitude = (double)json.get(LATITUDE);
        double longitude = (double)json.get(LONGITUDE);
        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        Long lastUpdated = new Long(ts.getSeconds());
        Report report = new Report(id, problem, notes,reportUrl, userName, longitude, latitude, lastUpdated);
        return report;
    }

    static Long getLocalLastUpdated(){
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(REPORTS_LAST_UPDATE, 0);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date){
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(REPORTS_LAST_UPDATE, date);
        editor.commit();
    }
}
