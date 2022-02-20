package com.example.yedidim;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import java.util.List;

public class ReportListViewModel extends ViewModel {
    LiveData<List<Report>> reports = Model.getInstance().getAllReports();
    public LiveData<List<Report>> getReports(){return reports;}
}
