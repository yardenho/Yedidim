package com.example.yedidim;

import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.User;

public class MyProfileViewModel extends ViewModel {
    private String username;


    public String getUsername(){return username;}
    public void setUsername(String u){username = u;}

}
