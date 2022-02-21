package com.example.yedidim.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.HashMap;
import java.util.Map;

@Entity

public class User {
    final static String USERNAME = "username";
    final static String FIRSTNAME = "firstName";
    final static String LASTNAME = "lastName";
    final static String PHONENUMBER = "phoneNumber";
    final static String CARNUMBER = "carNumber";
    final static String VEHICLEBRAND = "vehicleBrand";
    final static String MANUFACTUREYEAR = "manufactureYear";
    final static String FUELTYPE = "fuelType";
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

    public void setVehicleBrand(String vehicleBrand){
        this.vehicleBrand = vehicleBrand;
    }
    public void setManufactureYear(String manufactureYear){
        this.manufactureYear=manufactureYear;
    }
    public void setFuelType(String fuelType){
        this.fuelType = fuelType;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setUserName(String userName) {this.userName = userName;}
    public void setCarNumber(String carNumber){this.carNumber = carNumber;}

    static public User fromJson(Map<String, Object> json){
        String username = (String)json.get(USERNAME);
        if(username == null)
            return null;
        String firstName = (String)json.get(FIRSTNAME);
        if(firstName == null)
            return null;
        String lastName = (String)json.get(LASTNAME);
        if(lastName == null)
            return null;
        String phoneNumber = (String)json.get(PHONENUMBER);
        if(phoneNumber == null)
            return null;
        String carNumber = (String)json.get(CARNUMBER);
        if(carNumber == null)
            return null;
        String vehicleBrand = (String)json.get(VEHICLEBRAND);
        if(vehicleBrand == null)
            return null;
        String manufactureYear = (String)json.get(MANUFACTUREYEAR);
        if(manufactureYear == null)
            return null;
        String fuelType = (String)json.get(FUELTYPE);
        if(fuelType == null)
            fuelType = "";
        User user = new User(username, vehicleBrand, manufactureYear, fuelType, firstName, lastName, phoneNumber, carNumber);
        return user;
    }

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, userName);
        json.put(FIRSTNAME, firstName);
        json.put(LASTNAME, lastName);
        json.put(PHONENUMBER, phoneNumber);
        json.put(CARNUMBER, carNumber);
        json.put(VEHICLEBRAND, vehicleBrand);
        json.put(MANUFACTUREYEAR,manufactureYear);
        json.put(FUELTYPE,fuelType);
        return json;
    }
}
