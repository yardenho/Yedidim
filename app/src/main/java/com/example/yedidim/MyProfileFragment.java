package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        vehicleBrandTv = view.findViewById(R.id.myProfile_text_firstName);
        manufactureYearTv = view.findViewById(R.id.myProfile_text_lastName);
        fuelTypeTv = view.findViewById(R.id.myProfile_text_phoneNumber);
        firstNameTv = view.findViewById(R.id.myProfile_text_vehicleBrand);
        lastNameTv = view.findViewById(R.id.myProfile_text_manufactureYear);
        phoneNumberTv = view.findViewById(R.id.myProfile_text_fuelType);
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
                                //TODO: check if it is back like it should
                                Navigation.findNavController(v).navigate(MyProfileFragmentDirections.actionGlobalMainScreenFragment());
                            }
                        });
                    }
                });


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
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_reports_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //TODO: קוד כפול ???
        switch (item.getItemId()) {
            case R.id.log_out_menu_LogOut:
                Navigation.findNavController(view).navigate(myReportsFragmentDirections.actionGlobalMainScreenFragment());
                return true;
            case R.id.myReportsmenu_myReport:
                Navigation.findNavController(view).navigate(myReportsFragmentDirections.actionGlobalMyReportsFragment(viewModel.getUsername()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}