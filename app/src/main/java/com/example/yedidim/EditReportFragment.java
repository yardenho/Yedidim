package com.example.yedidim;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.yedidim.Model.User;


public class EditReportFragment extends Fragment {
    private EditReportViewModel viewModel;
    private EditText problem;
    private EditText notes;
    private TextView vehicleBrand;
    private TextView manufactureYear;
    private TextView fuelType;
    private TextView firstName;
    private TextView lastName;
    private TextView phoneNumber;
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

        String reportId = EditReportFragmentArgs.fromBundle(getArguments()).getReportID();

        view = inflater.inflate(R.layout.fragment_edit_report, container, false);
        problem = view.findViewById(R.id.editReport_et_problem);
        notes = view.findViewById(R.id.editReport_et_notes);
        vehicleBrand = view.findViewById(R.id.editReport_text_vehicleBrand);
        manufactureYear = view.findViewById(R.id.editReport_text_manufactureYear);
        fuelType = view.findViewById(R.id.editReport_text_fuelType);
        firstName = view.findViewById(R.id.editReport_text_firstName);
        lastName = view.findViewById(R.id.editReport_text_lastName);
        phoneNumber = view.findViewById(R.id.editReport_text_phoneNumber);


        Model.getInstance().getReportByID(reportId, (r) -> {
            viewModel.setReport(r);
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
                //TODO: to add here edit the report
                Model.getInstance().editReport(viewModel.getReport(), new Model.editReportListener() {
                    @Override
                    public void onComplete() {
                        Model.getInstance().reloadUserReportsList(viewModel.getUserName());//for updating the list of the user reports
                        Navigation.findNavController(view).navigateUp();
                    }
                });
            }
        });
        return view;
    }

    private void showUserDetails(User user) {
        vehicleBrand.setText(user.getVehicleBrand());
        manufactureYear.setText(user.getManufactureYear());
        fuelType.setText(user.getFuelType());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumber());
    }

    private void showReportDetails() {
        problem.setText(viewModel.getReport().getProblem());
        notes.setText(viewModel.getReport().getNotes());
        //TODO: add image
        Model.getInstance().getUserByUserName(viewModel.getReport().getUserName(), (u) ->
        {
            showUserDetails(u);
        });


    }

    private void setDetails() {
        viewModel.getReport().setProblem(problem.getText().toString());
        viewModel.getReport().setNotes(notes.getText().toString());
        //TODO set picture
    }

}