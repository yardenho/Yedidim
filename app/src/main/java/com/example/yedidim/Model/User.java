package com.example.yedidim.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class User {
    @PrimaryKey
    @NonNull
    private String userName;
    private String password;
    private String vehicleBrand;
    private String manufactureYear;
    private String fuelType;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public User(){}

    public User(String userName, String password, String vehicleBrand, String manufactureYear, String fuelType, String firstName, String lastName, String phoneNumber){
        this.userName= userName;
        this.password= password;
        this.vehicleBrand= vehicleBrand;
        this.manufactureYear= manufactureYear;
        this.fuelType = fuelType;
        this.firstName= firstName;
        this.lastName= lastName;
        this.phoneNumber=phoneNumber;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
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
    public void setPassword(String password) {this.password = password;}
}
