package com.example.yedidim;

import androidx.lifecycle.ViewModel;

public class EditProfileViewModel extends ViewModel {
    private String userName;

    public String getUserName(){return userName;}
    public void setUserName(String u){userName = u;}

}
