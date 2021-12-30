package com.example.yedidim;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class signUpFragment extends Fragment {

    private ProgressBar pb;

    public signUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signUpBtn = getView().findViewById(R.id.signUp_btn_signup);
        EditText userNameEt = getView().findViewById(R.id.signUp_et_username);
        EditText passwordEt = getView().findViewById(R.id.signUp_et_password);
        EditText vehicleBrandEt = getView().findViewById(R.id.signUp_et_vehicleBrand);
        EditText manufactureYearEt = getView().findViewById(R.id.signUp_et_manufYear);
        EditText fuelTypeEt = getView().findViewById(R.id.signUp_et_fuelType);
        EditText firstNameEt = getView().findViewById(R.id.signUp_et_firstName);
        EditText lastNameEt = getView().findViewById(R.id.signUp_et_lastName);
        EditText phoneNumberEt = getView().findViewById(R.id.signUp_et_phoneNumber);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                User user = new User();
                user.setFirstName(firstNameEt.getText().toString());
                user.setLastName(lastNameEt.getText().toString());
                user.setUserName(userNameEt.getText().toString());
                user.setPassword(passwordEt.getText().toString());
                user.setVehicleBrand(vehicleBrandEt.getText().toString());
                user.setManufactureYear(manufactureYearEt.getText().toString());
                user.setFuelType(fuelTypeEt.getText().toString());
                user.setPhoneNumber(phoneNumberEt.getText().toString());
                Model.getInstance().addNewUser(user, ()->{
                    signUpFragmentDirections.ActionSignUpFragmentToReportsListFragment action = signUpFragmentDirections.actionSignUpFragmentToReportsListFragment(user.getUserName());
                    Navigation.findNavController(v).navigate(action);
                });
            }
        });
        return view;
    }
}