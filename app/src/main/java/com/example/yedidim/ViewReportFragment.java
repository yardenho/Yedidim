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
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ViewReportFragment extends Fragment {
    private viewReportViewModel viewModel;
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

        viewModel.setReportId(ViewReportFragmentArgs.fromBundle(getArguments()).getReportID());


        String reportId = ViewReportFragmentArgs.fromBundle(getArguments()).getReportID();
        Model.getInstance().getReportByID(reportId, new Model.getReportByReportIDListener() {
            @Override
            public void onComplete(Report report) {
                if(report != null) {
                    updateReportDetailsDisplay(report);
                }
            }
        });


        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(ViewReportFragmentDirections.actionViewReportFragmentToMapFragment(viewModel.getReportId()));
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

    public void updateReportDetailsDisplay(Report r) {
        Model.getInstance().getUserByUserName(r.getUserName(), new Model.getUserByUserNameListener() {
            @Override
            public void onComplete(User user) {
                //TODO: need to decide what to if user is null
                if (user != null) {
                    updateUserDetailsDisplay(user);
                }
            }
        });
        problemTv.setText(r.getProblem());
        notesTv.setText(r.getNotes());
        String url = r.getReportUrl();
        if (url != null && !url.equals("")) {
            Picasso.get().load(url).placeholder(R.drawable.car).into(photo);
        } else {
            photo.setImageResource(R.drawable.car);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_profile_menu, menu);
        inflater.inflate(R.menu.my_reports_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);

    }
}