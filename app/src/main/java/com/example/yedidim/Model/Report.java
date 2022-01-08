package com.example.yedidim.Model;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yedidim.MyApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Entity
public class Report {
//    static private long idCounter = 0;  //TODO: אולי אין בזה צורך (לבדוק אם למחוק)
    @PrimaryKey
    @NonNull
    private String reportID;
    private String problem;
    private String notes;
    private String userName;
    private String reportUrl="";

    //    private ImageView image; // כשנוסיף העלאת תמונה אולי נשנה את הסוג
    private double longitude;
    private double latitude;

    public Report(){
    }
    public Report(String id, String problem, String notes,String rUrl, String u, double longitude, double latitude){
        this.reportID= id;
        this.problem=problem;
        this.notes=notes;
        this.userName=u;
//        this.image=iv;
        this.longitude=longitude;
        this.latitude=latitude;
        this.reportUrl=rUrl;
    }
    public String getReportUrl(){return reportUrl;}
    public void setReportUrl(String rUrl){
        reportUrl=rUrl;
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
        Log.d("TAG", "city:" + cityName);
        return addresses.get(0).getAddressLine(0);
    }
//    static public long getIdCounter(){return idCounter;}

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
//        json.put("reportID", report.getReportID());   the reportID comes from the document name
        json.put("problem", getProblem());
        json.put("notes",getNotes());
        json.put("username",getUserName());
        json.put("longitude",getLongitude());
        json.put("latitude",getLatitude());
        json.put("reportUrl", reportUrl);
        // TODO: need to add photo
        // json.put("image", report.getImage());
        return json;
    }

    static public Report fromJson(String reportId, Map<String, Object> json){
        String id = reportId;
        String problem = (String)json.get("problem");
        if(problem == null)
            return null;
        String notes = (String)json.get("notes");
        if(notes == null)
            return null;
        String userName = (String)json.get("username");
        if(userName == null)
            return null;
        String reportUrl = (String)json.get("reportUrl");
            if(reportUrl==null)
                return null;
        //TODO: add photo
        double latitude = (double)json.get("latitude");
        double longitude = (double)json.get("longitude");
        Report report = new Report(id, problem, notes,reportUrl, userName, longitude, latitude);
        return report;
    }
}
