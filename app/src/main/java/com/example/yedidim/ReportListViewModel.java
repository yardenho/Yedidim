package com.example.yedidim;

import androidx.lifecycle.ViewModel;

public class ReportListViewModel extends ViewModel {

    private  String username;

    public void setUserName(String u){username = u ;}
    public String getUserName(){return username;}
}
