package com.example.yedidim;

import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Report;

public class EditReportViewModel extends ViewModel {
    private String userName;
    private Report report;

    public String getUserName(){return userName;}
    public void setUserName(String u){
        userName=u;
    }
    public Report getReport(){return report;}
    public void setReport(Report r){ report = r;}
}
