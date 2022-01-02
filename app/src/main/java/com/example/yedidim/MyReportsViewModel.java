package com.example.yedidim;

import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Report;

import java.util.LinkedList;
import java.util.List;

public class MyReportsViewModel extends ViewModel {
    private String username;
    private List<Report> myReports = new LinkedList<Report>();    //TODO: to do new ? ????


    public void setUsername(String u){username = u;}
    public String getUsername(){return username;}

    public void setMyReports(List<Report>data){myReports = data;}
    public List<Report> getMyReports(){ return myReports;}
}
