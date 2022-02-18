package com.example.yedidim;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;

import java.util.LinkedList;
import java.util.List;

public class MyReportsViewModel extends ViewModel {

    private LiveData<List<Report>> myReports = Model.getInstance().getAllUserReports();

    public void setMyReports(LiveData<List<Report>>data){myReports = data;}
    public LiveData<List<Report>> getMyReports(){ return myReports;}
}
