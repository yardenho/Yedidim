package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
        TextView problemTv = view.findViewById(R.id.viewReport_text_problem);
        TextView notesTv = view.findViewById(R.id.viewReport_text_notes);
        TextView vehicleBrandTv = view.findViewById(R.id.viewReport_text_vehicleBrand);
        TextView manufactureYearTv = view.findViewById(R.id.viewReport_text_manufactureYear);
        TextView fuelTypeTv = view.findViewById(R.id.viewReport_text_fuelType);
        TextView firstNameTv = view.findViewById(R.id.viewReport_text_firstName);
        TextView lastNameTv = view.findViewById(R.id.viewReport_text_lastName);
        TextView phoneNumberTv = view.findViewById(R.id.viewReport_text_phoneNumber);
        //*****************************************************
        //TODO: to add image references
        //*****************************************************

        viewModel.setUsername(ViewReportFragmentArgs.fromBundle(getArguments()).getUsername());
        viewModel.setReportId(ViewReportFragmentArgs.fromBundle(getArguments()).getReportID());

        Model.getInstance().getUserByUserName(viewModel.getUsername(), new Model.getUserByUserNameListener() {
            @Override
            public void onComplete(User user) {
                u=user;
            }
        });

        Model.getInstance().getReportByID(viewModel.getReportId(), new Model.getReportByReportIDListener() {
            @Override
            public void onComplete(Report report) {
                r=report;
            }
        });

        problemTv.setText(r.getProblem());
        notesTv.setText(r.getNotes());
        vehicleBrandTv.setText(u.getVehicleBrand());
        manufactureYearTv.setText(u.getManufactureYear());
        fuelTypeTv.setText(u.getFuelType());
        firstNameTv.setText(u.getFirstName());
        lastNameTv.setText(u.getLastName());
        phoneNumberTv.setText(u.getPhoneNumber());
        //*****************************************************
        //TODO: to add image references
        //*****************************************************

        return view;
    }
}