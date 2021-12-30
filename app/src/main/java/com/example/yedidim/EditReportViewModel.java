package com.example.yedidim;

import androidx.lifecycle.ViewModel;

public class EditReportViewModel extends ViewModel {
    private String userName;
    private String reportID;

    public String getUserName(){return userName;}
    public void setUserName(String u){
        userName=u;
    }
    public String getReportID(){return reportID;}
    public void setReportID(String r){ reportID = r;}
}
