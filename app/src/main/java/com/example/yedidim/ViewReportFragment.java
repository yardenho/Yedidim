package com.example.yedidim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewReportFragment extends Fragment {

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








        return view;
    }
}