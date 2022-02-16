package com.example.yedidim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.User;


public class signUpFragment extends Fragment {

    private Button signUpBtn;
    private ProgressBar pb;
    private EditText userNameEt;
    private EditText passwordEt;
    private EditText vehicleBrandEt;
    private EditText manufactureYearEt;
    private EditText fuelTypeEt;
    private EditText firstNameEt;
    private EditText lastNameEt;
    private EditText phoneNumberEt;
    private EditText carNumberEt;

    public signUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpBtn = view.findViewById(R.id.signUp_btn_signup);
        userNameEt = view.findViewById(R.id.signUp_et_username);
        passwordEt = view.findViewById(R.id.signUp_et_password);
        vehicleBrandEt = view.findViewById(R.id.signUp_et_vehicleBrand);
        manufactureYearEt = view.findViewById(R.id.signUp_et_manuYear);
        fuelTypeEt = view.findViewById(R.id.signUp_et_fuelType);
        firstNameEt = view.findViewById(R.id.signUp_et_firstName);
        lastNameEt = view.findViewById(R.id.signUp_et_lastName);
        phoneNumberEt = view.findViewById(R.id.signUp_et_phoneNumber);
        carNumberEt = view.findViewById(R.id.signUp_et_carNumber);
        Button backBtn = view.findViewById(R.id.signUp_btn_back);
        pb = view.findViewById(R.id.signUp_progressBar);
        pb.setVisibility(View.GONE);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                if (checkDetails()) {
                    registerUser(v);
                }
                else {
                    pb.setVisibility(View.GONE);
                    signUpBtn.setEnabled(true);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
        return view;
    }

    private void registerUser(View v)
    {
        String password = passwordEt.getText().toString().trim();
        User user = new User();
        user.setFirstName(firstNameEt.getText().toString().trim());
        user.setLastName(lastNameEt.getText().toString().trim());
        user.setUserName(userNameEt.getText().toString().trim());
        user.setPassword(passwordEt.getText().toString().trim());  // need to remove
        user.setVehicleBrand(vehicleBrandEt.getText().toString().trim());
        user.setManufactureYear(manufactureYearEt.getText().toString().trim());
        user.setFuelType(fuelTypeEt.getText().toString().trim());
        user.setPhoneNumber(phoneNumberEt.getText().toString().trim());
        user.setCarNumber(carNumberEt.getText().toString().trim());
        Model.getInstance().addNewUser(user, password, () -> {
            signUpFragmentDirections.ActionSignUpFragmentToReportsListFragment action = signUpFragmentDirections.actionSignUpFragmentToReportsListFragment(user.getUserName());
            Navigation.findNavController(v).navigate(action);
        });
    }

    private boolean checkDetails()
    {
        String email = userNameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String firstName = firstNameEt.getText().toString().trim();
        String lastName = lastNameEt.getText().toString().trim();
        String phoneNumber = phoneNumberEt.getText().toString().trim();
        String vehicleBrand = vehicleBrandEt.getText().toString().trim();
        String manufactureYear = manufactureYearEt.getText().toString().trim();
        String fuelType = fuelTypeEt.getText().toString().trim();
        String carNumber = carNumberEt.getText().toString().trim();
        if(firstName.isEmpty()) {
            firstNameEt.setError("first name is required");
            firstNameEt.requestFocus();
            return false;
        }

        if(lastName.isEmpty()) {
            lastNameEt.setError("last name is required");
            lastNameEt.requestFocus();
            return false;
        }

        if(phoneNumber.isEmpty()) {
            phoneNumberEt.setError("phone number is required");
            phoneNumberEt.requestFocus();
            return false;
        }

        if(email.isEmpty()) {
            userNameEt.setError("email is required");
            userNameEt.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userNameEt.setError("Please provide valid email");
            userNameEt.requestFocus();
            return false;
        }

        if(password.isEmpty()) {
            passwordEt.setError("password is required");
            passwordEt.requestFocus();
            return false;
        }
        if(password.length() < 6) {
            passwordEt.setError("password should be at least 6 characters");
            passwordEt.requestFocus();
            return false;
        }

        if(vehicleBrand.isEmpty()) {
            vehicleBrandEt.setError("vehicle brand is required");
            vehicleBrandEt.requestFocus();
            return false;
        }

        if(manufactureYear.isEmpty()) {
            manufactureYearEt.setError("manufacture year is required");
            manufactureYearEt.requestFocus();
            return false;
        }

        if(carNumber.isEmpty()) {
            carNumberEt.setError("car number is required");
            carNumberEt.requestFocus();
            return false;
        }
        return true;
    }
}