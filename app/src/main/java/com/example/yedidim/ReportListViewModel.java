package com.example.yedidim;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;

import java.util.LinkedList;
import java.util.List;

public class ReportListViewModel extends ViewModel {

    private  String username;
//    List<Report> reports = new LinkedList<Report>();

    LiveData<List<Report>> reports = Model.getInstance().getAllReports();


    public void setUserName(String u){username = u ;}
    public String getUserName(){return username;}

//    public void setReports(LiveData<List<Report>> r){reports = r;} // אליאב הוריד אצלו, אמר שאין פונקציית סט
    public LiveData<List<Report>> getReports(){return reports;}
}
