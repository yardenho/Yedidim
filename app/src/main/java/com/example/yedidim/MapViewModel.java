package com.example.yedidim;

import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private String reportID;

    public void setReportID(String r){ reportID = r;}
    public String getReportID(){return reportID;}
}
