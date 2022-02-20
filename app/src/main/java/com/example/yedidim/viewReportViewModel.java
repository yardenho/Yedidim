package com.example.yedidim;

import androidx.lifecycle.ViewModel;

public class viewReportViewModel extends ViewModel {
    private String reportId;

    public String getReportId(){
        return reportId;
    }
    public void setReportId(String reportId){
        this.reportId = reportId;
    }

}
