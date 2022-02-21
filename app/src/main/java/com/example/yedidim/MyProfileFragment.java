package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.User;

public class MyProfileFragment extends Fragment {
    TextView vehicleBrandTv;
    TextView manufactureYearTv;
    TextView fuelTypeTv;
    TextView firstNameTv;
    TextView lastNameTv ;
    TextView phoneNumberTv;
    TextView carNumberTv;
    Button editProfileBtn;
    View view;
    ProgressBar pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        vehicleBrandTv = view.findViewById(R.id.myProfile_text_vehicleBrand);
        manufactureYearTv = view.findViewById(R.id.myProfile_text_manufactureYear);
        fuelTypeTv = view.findViewById(R.id.myProfile_text_fuelType);
        firstNameTv = view.findViewById(R.id.myProfile_text_firstName);
        lastNameTv = view.findViewById(R.id.myProfile_text_lastName);
        phoneNumberTv = view.findViewById(R.id.myProfile_text_phoneNumber);
        carNumberTv = view.findViewById(R.id.myProfile_text_carNumber);
        editProfileBtn = view.findViewById(R.id.myProfile_btn_editProfile);
        pb = view.findViewById(R.id.myProfile_progressBar);
        pb.setVisibility(View.VISIBLE);

        Model.getInstance().getCurrentUser(new Model.getCurrentUserListener() {
            @Override
            public void onComplete(String userEmail) {
                Model.getInstance().getUserByUserName(userEmail, new Model.getUserByUserNameListener() {
                    @Override
                    public void onComplete(User user) {
                        setDetails(user);
                    }
                });
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @NonNull NavDirections action = MyProfileFragmentDirections.actionMyProfileFragmentToEditProfileFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });

        setHasOptionsMenu(true);
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
        pb.setVisibility(View.GONE);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_reports_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);
    }

}