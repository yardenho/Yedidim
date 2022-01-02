package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import com.example.yedidim.Model.User;


public class ViewReportFragment extends Fragment {
    private viewReportViewModel viewModel;
    private User u; // dose it need to be here
    private Report r;

    TextView problemTv;
    TextView notesTv;
    TextView vehicleBrandTv;
    TextView manufactureYearTv;
    TextView fuelTypeTv;
    TextView firstNameTv;
    TextView lastNameTv;
    TextView phoneNumberTv;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(viewReportViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_report, container, false);
        problemTv = view.findViewById(R.id.viewReport_text_problem);
        notesTv = view.findViewById(R.id.viewReport_text_notes);
        vehicleBrandTv = view.findViewById(R.id.viewReport_text_vehicleBrand);
        manufactureYearTv = view.findViewById(R.id.viewReport_text_manufactureYear);
        fuelTypeTv = view.findViewById(R.id.viewReport_text_fuelType);
        firstNameTv = view.findViewById(R.id.viewReport_text_firstName);
        lastNameTv = view.findViewById(R.id.viewReport_text_lastName);
        phoneNumberTv = view.findViewById(R.id.viewReport_text_phoneNumber);
        //*****************************************************
        //TODO: to add image references
        //*****************************************************

        viewModel.setUsername(ViewReportFragmentArgs.fromBundle(getArguments()).getUsername());
        viewModel.setReportId(ViewReportFragmentArgs.fromBundle(getArguments()).getReportID());
        Log.d("TAG", "username: " + viewModel.getUsername());
        Log.d("TAG", "reportID: " + viewModel.getReportId());


        Model.getInstance().getUserByUserName(viewModel.getUsername(), new Model.getUserByUserNameListener() {
            @Override
            public void onComplete(User user) {
                u=user;
                if(u == null)
                    Log.d("TAG", "user is null");
                updateUserDetailsDisplay(u);
            }
        });

        Model.getInstance().getReportByID(viewModel.getReportId(), new Model.getReportByReportIDListener() {
            @Override
            public void onComplete(Report report) {
                r=report;
                if(r == null)
                    Log.d("TAG", "report is null");
                updateReportDetailsDisplay(r);
            }
        });

        return view;
    }

    public void updateUserDetailsDisplay(User u)
    {
        vehicleBrandTv.setText(u.getVehicleBrand());
        manufactureYearTv.setText(u.getManufactureYear());
        fuelTypeTv.setText(u.getFuelType());
        firstNameTv.setText(u.getFirstName());
        lastNameTv.setText(u.getLastName());
        phoneNumberTv.setText(u.getPhoneNumber());
    }

    public void updateReportDetailsDisplay(Report r)
    {
        problemTv.setText(r.getProblem());
        notesTv.setText(r.getNotes());
        //*****************************************************
        //TODO: to add image assignment
        //*****************************************************
    }
}