package com.example.yedidim;

import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Report;
import com.example.yedidim.Model.User;

public class viewReportViewModel extends ViewModel {
    private String username;
    private String reportId;


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

}
