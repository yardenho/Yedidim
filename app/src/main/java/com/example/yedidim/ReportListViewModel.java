package com.example.yedidim;

import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Report;

import java.util.LinkedList;
import java.util.List;

public class ReportListViewModel extends ViewModel {

    private  String username;
    List<Report> reports = new LinkedList<Report>();


    public void setUserName(String u){username = u ;}
    public String getUserName(){return username;}
    public void setReports(List<Report> r){reports = r;}
    public List<Report> getReports(){return reports;}
}
