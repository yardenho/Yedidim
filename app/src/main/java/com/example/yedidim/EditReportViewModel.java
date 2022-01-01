package com.example.yedidim;

import androidx.lifecycle.ViewModel;

public class EditReportViewModel extends ViewModel {
    private String userName;
    private Long reportID;

    public String getUserName(){return userName;}
    public void setUserName(String u){
        userName=u;
    }
    public Long getReportID(){return reportID;}
    public void setReportID(Long r){ reportID = r;}
}
