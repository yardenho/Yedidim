package com.example.yedidim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        TextView vehicleBrandTv = view.findViewById(R.id.myProfile_text_vehicleBrand);
        TextView manufactureYearTv = view.findViewById(R.id.myProfile_text_manufactureYear);
        TextView fuelTypeTv = view.findViewById(R.id.myProfile_text_fuelType);
        TextView firstNameTv = view.findViewById(R.id.myProfile_text_firstName);
        TextView lastNameTv = view.findViewById(R.id.myProfile_text_lastName);
        TextView phoneNumberTv = view.findViewById(R.id.myProfile_text_phoneNumber);
        TextView carNumberTv = view.findViewById(R.id.myProfile_text_carNumber);
        Button editProfileBtn = view.findViewById(R.id.myProfile_btn_editProfile);
        Button deleteAccountBtn = view.findViewById(R.id.myProfile_btn_deleteAccount);




        return view;
    }
}