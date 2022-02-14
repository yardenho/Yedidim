package com.example.yedidim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import com.example.yedidim.Model.User;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ViewReportFragment extends Fragment {
    private viewReportViewModel viewModel;
    private User u; // dose it need to be here
    private Report r;
    View view;
    TextView problemTv;
    TextView notesTv;
    TextView vehicleBrandTv;
    TextView manufactureYearTv;
    TextView fuelTypeTv;
    TextView firstNameTv;
    TextView lastNameTv;
    TextView phoneNumberTv;
    ImageView photo;
    ImageButton mapBtn;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(viewReportViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_report, container, false);
        problemTv = view.findViewById(R.id.viewReport_text_problem);
        notesTv = view.findViewById(R.id.viewReport_text_notes);
        vehicleBrandTv = view.findViewById(R.id.viewReport_text_vehicleBrand);
        manufactureYearTv = view.findViewById(R.id.viewReport_text_manufactureYear);
        fuelTypeTv = view.findViewById(R.id.viewReport_text_fuelType);
        firstNameTv = view.findViewById(R.id.viewReport_text_firstName);
        lastNameTv = view.findViewById(R.id.viewReport_text_lastName);
        phoneNumberTv = view.findViewById(R.id.viewReport_text_phoneNumber);
        mapBtn = view.findViewById(R.id.viewReport_imageBtn_map);
        photo = view.findViewById(R.id.viewReport_iv_image);
        viewModel.setUsername(ViewReportFragmentArgs.fromBundle(getArguments()).getUsername());
        viewModel.setReportId(ViewReportFragmentArgs.fromBundle(getArguments()).getReportID());



//        Model.getInstance().getUserByUserName(viewModel.getUsername(), new Model.getUserByUserNameListener() {
//            @Override
//            public void onComplete(User user) {
//                u=user;
//                //TODO: need to decide what to if user is null
//                if(u == null)
//                    Log.d("TAG", "user is null");
//                updateUserDetailsDisplay(u);
//            }
//        });

        Model.getInstance().getReportByID(viewModel.getReportId(), new Model.getReportByReportIDListener() {
            @Override
            public void onComplete(Report report) {
                r=report;
                if(r == null)
                    Log.d("TAG", "report is null");
                updateReportDetailsDisplay(r);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(ViewReportFragmentDirections.actionViewReportFragmentToMapFragment(viewModel.getUsername(),viewModel.getReportId()));
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    public void updateUserDetailsDisplay(User u)
    {
        vehicleBrandTv.setText(u.getVehicleBrand());
        manufactureYearTv.setText(u.getManufactureYear());
        fuelTypeTv.setText(u.getFuelType());
        firstNameTv.setText(u.getFirstName());
        lastNameTv.setText(u.getLastName());
        phoneNumberTv.setText(u.getPhoneNumber());
    }

    public void updateReportDetailsDisplay(Report r)
    {
        Model.getInstance().getUserByUserName(r.getUserName(), new Model.getUserByUserNameListener() {
            @Override
            public void onComplete(User user) {
                u=user;
                //TODO: need to decide what to if user is null
                if(u == null)
                    Log.d("TAG", "user is null");
                updateUserDetailsDisplay(u);
            }
        });
        problemTv.setText(r.getProblem());
        notesTv.setText(r.getNotes());
//        try {
//            URL urlConnection = new URL(r.getReportUrl());
//            HttpURLConnection connection = (HttpURLConnection) urlConnection
//                    .openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            photo.setImageBitmap(myBitmap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    //*****************************************************
        //TODO: to add image assignment
        //*****************************************************
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_profile_menu, menu);
        inflater.inflate(R.menu.my_reports_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //TODO: קוד כפול ???
        switch (item.getItemId()) {
            case R.id.log_out_menu_LogOut:
                //TODO
                Navigation.findNavController(view).navigate(ViewReportFragmentDirections.actionGlobalMainScreenFragment());
                return true;
            case R.id.myProfileMenu_myProfile:
                Navigation.findNavController(view).navigate(ViewReportFragmentDirections.actionGlobalMyProfileFragment(viewModel.getUsername()));
                return true;
            case R.id.myReportsmenu_myReport:
                Navigation.findNavController(view).navigate(ViewReportFragmentDirections.actionGlobalMyReportsFragment(viewModel.getUsername()));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}