package com.example.yedidim.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity

public class User {
    @PrimaryKey
    @NonNull
    private String userName;
    private String vehicleBrand;
    private String manufactureYear;
    private String fuelType;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String carNumber;

    public User(){}

    public User(String userName, String vehicleBrand, String manufactureYear, String fuelType, String firstName, String lastName, String phoneNumber, String carNumber){
        this.userName= userName;
        this.vehicleBrand= vehicleBrand;
        this.manufactureYear= manufactureYear;
        this.fuelType = fuelType;
        this.firstName= firstName;
        this.lastName= lastName;
        this.phoneNumber=phoneNumber;
        this.carNumber = carNumber;
    }
    public String getUserName(){
        return userName;
    }
    public String getVehicleBrand(){
        return vehicleBrand;
    }
    public String getManufactureYear(){
        return manufactureYear;
    }
    public String getFuelType(){
        return fuelType;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getCarNumber(){return carNumber;}

    public void setVehicleBrand(String vb){
        this.vehicleBrand = vb;
    }
    public void setManufactureYear(String my){
        this.manufactureYear=my;
    }
    public void setFuelType(String ft){
        this.fuelType = ft;
    }
    public void setFirstName(String fn){
        this.firstName = fn;
    }
    public void setLastName(String ln){
        this.lastName = ln;
    }
    public void setPhoneNumber(String pn){
        this.phoneNumber = pn;
    }
    public void setUserName(String un) {this.userName = un;}
    public void setCarNumber(String carNumber){this.carNumber = carNumber;}

    static public User fromJson(Map<String, Object> json){
        Log.d("TAGs", "user from json");
        String username = (String)json.get("username");
        Log.d("TAGs", "email= " + username);
        if(username == null)
            return null;
        String password = (String)json.get("password");
        if(password == null)
            return null;
        // TODO: need to delete
        String firstName = (String)json.get("firstName");
        if(firstName == null)
            return null;
        String lastName = (String)json.get("lastName");
        if(lastName == null)
            return null;
        String phoneNumber = (String)json.get("phoneNumber");
        if(phoneNumber == null)
            return null;
        String carNumber = (String)json.get("carNumber");
        if(carNumber == null)
            return null;
        String vehicleBrand = (String)json.get("vehicleBrand");
        if(vehicleBrand == null)
            return null;
        String manufactureYear = (String)json.get("manufactureYear");
        if(manufactureYear == null)
            return null;
        String fuelType = (String)json.get("fuelType");
        if(fuelType == null)
            fuelType = "";
        Log.d("TAGs", "nothing is null");
        User user = new User(username, vehicleBrand, manufactureYear, fuelType, firstName, lastName, phoneNumber, carNumber);
        return user;
    }

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("username", userName);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("phoneNumber", phoneNumber);
        json.put("carNumber", carNumber);
        json.put("vehicleBrand", vehicleBrand);
        json.put("manufactureYear",manufactureYear);
        json.put("fuelType",fuelType);
        return json;
    }

}
