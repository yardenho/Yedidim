package com.example.yedidim;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yedidim.Model.Report;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;


public class AddingReportFragment extends Fragment {
    private AddingReportViewModel viewModel;
    private Button cancelBtn;
    private Button reportBtn;
    private EditText problemEt;
    private EditText noteEt;
    //****************************************
    double longitude;
    double latitude;
    //    FusedLocationProviderClient client;
    LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    boolean isGPSEnabled = false;
    Location location; // Location

    public AddingReportFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AddingReportViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adding_report, container, false);
        problemEt = view.findViewById(R.id.addingReport_et_problem);
        noteEt = view.findViewById(R.id.addingReport_et_notes);
        //******************************************************************************************
        //// to add the photo adding implementation !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //******************************************************************************************

        cancelBtn = view.findViewById(R.id.addingReport_btn_cancel);
        reportBtn = view.findViewById(R.id.addingReport_btn_report);
        ProgressBar pb = view.findViewById(R.id.addingReport_progressBar);
        pb.setVisibility(View.GONE);

//        viewModel.setUsername(AddingReportFragmentArgs.fromBundle(getArguments()).getUsername());
//        getCurrentLatitude();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                reportBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                Report report = new Report();
                report.setReportID(Report.getIdCounter());
                report.setProblem(problemEt.getText().toString());
                report.setNotes(noteEt.getText().toString());
                report.setUserName(viewModel.getUsername());


            }
        });


        return view;
    }

//    public double getCurrentLatitude() {
//
//        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//        }
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        Log.d("TAG", "LAT" + location.getLatitude());
//        return location.getLatitude();
//        }
    }
//
//    public double getCurrentLatitude() {
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        return location.getLongitude();
//    }

//}