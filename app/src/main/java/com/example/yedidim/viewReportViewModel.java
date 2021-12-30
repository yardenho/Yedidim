package com.example.yedidim;

import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Report;
import com.example.yedidim.Model.User;

public class viewReportViewModel extends ViewModel {
    private String username;
    private String reportId;
    private User user; // dose it need to be here
    private Report report;


    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getReportId(){
        return reportId;
    }

    public void setReportId(String reportId){
        this.reportId = reportId;
    }

    public User getUser(){return user;}
    public void setUser(User u){user = u;}

    public Report getReport(){return report;}
    public void setReport(Report r){report = r;}

}
