package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.User;


public class EditProfileFragment extends Fragment {
    private EditProfileViewModel viewModel;
    private User user;
    private EditText vehicleBrand;
    private EditText manufactureYear;
    private EditText carNumber;
    private EditText fuelType;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private Button saveBtn;
    private Button cancelBtn;
    View view;

    public EditProfileFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
    }
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
        viewModel.setUserName(EditProfileFragmentArgs.fromBundle(getArguments()).getUsername());
        Model.getInstance().getUserByUserName(viewModel.getUserName(), (u) ->
        {
            user = u;
            showUserDetails();
        });

        cancelBtn = view.findViewById(R.id.editProfile_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        saveBtn = view.findViewById(R.id.editProfile_btn_save);
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
        carNumber.setText(user.getCarNumber());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumber());
    }
    private void setDetails() {
        user.setVehicleBrand(vehicleBrand.getText().toString());
        user.setManufactureYear(manufactureYear.getText().toString());
        user.setFuelType(fuelType.getText().toString());
        user.setCarNumber(carNumber.getText().toString());
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setPhoneNumber(phoneNumber.getText().toString());
        Model.getInstance().editUser(user, ()->{
            Navigation.findNavController(view).navigateUp();
        });
    }


}