package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.User;

public class MyProfileFragment extends Fragment {
    private MyProfileViewModel viewModel;
    TextView vehicleBrandTv;
    TextView manufactureYearTv;
    TextView fuelTypeTv;
    TextView firstNameTv;
    TextView lastNameTv ;
    TextView phoneNumberTv;
    TextView carNumberTv;
    Button editProfileBtn;
    Button deleteAccountBtn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        vehicleBrandTv = view.findViewById(R.id.myProfile_text_vehicleBrand);
        manufactureYearTv = view.findViewById(R.id.myProfile_text_manufactureYear);
        fuelTypeTv = view.findViewById(R.id.myProfile_text_fuelType);
        firstNameTv = view.findViewById(R.id.myProfile_text_firstName);
        lastNameTv = view.findViewById(R.id.myProfile_text_lastName);
        phoneNumberTv = view.findViewById(R.id.myProfile_text_phoneNumber);
        carNumberTv = view.findViewById(R.id.myProfile_text_carNumber);
        editProfileBtn = view.findViewById(R.id.myProfile_btn_editProfile);
        deleteAccountBtn = view.findViewById(R.id.myProfile_btn_deleteAccount);

        viewModel.setUsername(MyProfileFragmentArgs.fromBundle(getArguments()).getUsername());
        Model.getInstance().getUserByUserName(viewModel.getUsername(), new Model.getUserByUserNameListener() {
            @Override
            public void onComplete(User user) {
                setDetails(user);
            }
        });


        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileFragmentDirections.ActionMyProfileFragmentToEditProfileFragment action = MyProfileFragmentDirections.actionMyProfileFragmentToEditProfileFragment(viewModel.getUsername());
                Navigation.findNavController(v).navigate(action);
            }
        });

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getUserByUserName(viewModel.getUsername(), new Model.getUserByUserNameListener() {
                    @Override
                    public void onComplete(User user) {
                        Model.getInstance().deleteUser(user, new Model.deleteUserListener() {
                            @Override
                            public void onComplete() {

                            }
                        });
                    }
                });


            }
        });


        return view;
    }

    public void setDetails(User u){
        vehicleBrandTv.setText(u.getVehicleBrand());
        manufactureYearTv.setText(u.getManufactureYear());
        fuelTypeTv.setText(u.getFuelType());
        firstNameTv.setText(u.getFirstName());
        lastNameTv.setText(u.getLastName());
        phoneNumberTv.setText(u.getPhoneNumber());
        carNumberTv.setText(u.getCarNumber());
    }
}