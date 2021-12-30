package com.example.yedidim;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import com.example.yedidim.Model.User;


public class EditReportFragment extends Fragment {
    private EditReportViewModel viewModel;
    private Report report;
    private User user; // לא חייב כי לא ניתן לשנות פרטים של משתמש אלא רק פרטי דיווח
    private EditText problem;
    private EditText notes;
    private EditText vehicleBrand;
    private EditText manufactureYear;
    private EditText fuelType;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private Button cancelBtn;
    private Button saveBtn;
    View view;


    public EditReportFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditReportViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel.setUserName(EditReportFragmentArgs.fromBundle(getArguments()).getUsername());

        viewModel.setReportID(EditReportFragmentArgs.fromBundle(getArguments()).getReportID());

        view = inflater.inflate(R.layout.fragment_edit_report, container, false);
        problem = view.findViewById(R.id.editReport_et_problem);
        notes = view.findViewById(R.id.editReport_et_notes);
        vehicleBrand = view.findViewById(R.id.editReport_et_vehicleBrand);
        manufactureYear = view.findViewById(R.id.editReport_et_manufactureYear);
        fuelType = view.findViewById(R.id.editReport_et_fuelType);
        firstName = view.findViewById(R.id.editReport_et_firstName);
        lastName = view.findViewById(R.id.editReport_et_lastName);
        phoneNumber = view.findViewById(R.id.editReport_et_phoneNumber);
        Model.getInstance().getUserByUserName(viewModel.getUserName(), (u) ->
        {
            user = u;
            showUserDetails();
        });

        Model.getInstance().getReportByID(viewModel.getReportID(), (r) -> {
            report = r;
            showReportDetails();
        });


        cancelBtn = view.findViewById(R.id.editReport_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        saveBtn = view.findViewById(R.id.editReport_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetails();
            }
        });
        return view;
    }

    private void showUserDetails() {
        vehicleBrand.setText(user.getVehicleBrand());
        manufactureYear.setText(user.getManufactureYear());
        fuelType.setText(user.getFuelType());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumber());
    }

    private void showReportDetails() {
        problem.setText(report.getProblem());
        notes.setText(report.getNotes());
    }

    private void setDetails() {
        report.setProblem(problem.getText().toString());
        report.setNotes(notes.getText().toString());
        //TODO set picture
    }

}