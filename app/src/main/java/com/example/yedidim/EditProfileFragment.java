package com.example.yedidim;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.User;

public class EditProfileFragment extends Fragment {
    private EditText vehicleBrand;
    private EditText manufactureYear;
    private EditText carNumber;
    private EditText fuelType;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private Button saveBtn;
    private Button cancelBtn;
    private View view;
    private ProgressBar pb;

    public EditProfileFragment() {
    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        vehicleBrand = view.findViewById(R.id.editProfile_et_vehicleBrand);
        manufactureYear = view.findViewById(R.id.editProfile_et_manufYear);
        carNumber = view.findViewById(R.id.editProfile_et_carNumber);
        fuelType = view.findViewById(R.id.editProfile_et_fuelType);
        firstName = view.findViewById(R.id.editProfile_et_firstName);
        lastName = view.findViewById(R.id.editProfile_et_lastName);
        phoneNumber = view.findViewById(R.id.editProfile_et_phoneNumber);
        saveBtn = view.findViewById(R.id.editProfile_btn_save);
        cancelBtn = view.findViewById(R.id.editProfile_btn_cancel);
        pb = view.findViewById(R.id.editProfile_progressBar);
        pb.setVisibility(View.GONE);

        Model.getInstance().getCurrentUser(new Model.getCurrentUserListener() {
            @Override
            public void onComplete(String userEmail) {
                Model.getInstance().getUserByUserName(userEmail, (u) ->
                {
                    User user = u;
                    showUserDetails(user);
                });
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                saveBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                if(!checkConditions()) {
                    setDetails();
                }
                else {
                    pb.setVisibility(View.GONE);
                    saveBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                }
            }
        });

        return view;
    }

    private void showUserDetails(User user) {
        vehicleBrand.setText(user.getVehicleBrand());
        manufactureYear.setText(user.getManufactureYear());
        fuelType.setText(user.getFuelType());
        carNumber.setText(user.getCarNumber());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumber());
    }
    private void setDetails() {
        User user = new User();
        Model.getInstance().getCurrentUser(new Model.getCurrentUserListener() {
            @Override
            public void onComplete(String userEmail) {
                user.setUserName(userEmail);
                user.setVehicleBrand(vehicleBrand.getText().toString());
                user.setManufactureYear(manufactureYear.getText().toString());
                user.setFuelType(fuelType.getText().toString());
                user.setCarNumber(carNumber.getText().toString());
                user.setFirstName(firstName.getText().toString());
                user.setLastName(lastName.getText().toString());
                user.setPhoneNumber(phoneNumber.getText().toString());
                Model.getInstance().editUser(user, (success)->{
                    if(success)
                        Navigation.findNavController(view).navigateUp();
                    else{
                        pb.setVisibility(View.GONE);
                        saveBtn.setEnabled(true);
                        cancelBtn.setEnabled(true);
                    }
                });
            }
        });
    }

    private boolean checkConditions(){
        if(firstName.getText().toString().equals("")){
            firstName.setError("First name is required");
            firstName.requestFocus();
            return true;
        }
        if(lastName.getText().toString().equals("")){
            lastName.setError("Last name is required");
            lastName.requestFocus();
            return true;
        }
        if(phoneNumber.getText().toString().equals("")){
            phoneNumber.setError("Phone number is required");
            phoneNumber.requestFocus();
            return true;
        }
        if(carNumber.getText().toString().equals("")){
            carNumber.setError("Car number is required");
            carNumber.requestFocus();
            return true;
        }
        if(vehicleBrand.getText().toString().equals("")){
            vehicleBrand.setError("Vehicle brand is required");
            vehicleBrand.requestFocus();
            return true;
        }
        if(manufactureYear.getText().toString().equals("")){
            manufactureYear.setError("Manufacture year is required");
            manufactureYear.requestFocus();
            return true;
        }
        return false;
    }
}