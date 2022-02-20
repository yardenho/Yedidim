package com.example.yedidim;

import androidx.lifecycle.ViewModel;

import com.example.yedidim.Model.Report;

public class EditReportViewModel extends ViewModel {
    public String problemSave;
    private Report report;

    public Report getReport(){return report;}
    public void setReport(Report r){ report = r;}
}
