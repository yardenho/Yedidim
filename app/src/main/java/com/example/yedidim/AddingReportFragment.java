package com.example.yedidim;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class AddingReportFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE=1;
    private AddingReportViewModel viewModel;
//    public Report report = new Report();
    private Button cancelBtn;
    private Button reportBtn;
    private EditText problemEt;
    private EditText noteEt;
    private ImageView photo;
    private ImageButton photoIBtn;
    // צריך להוסיף שדה של תמונה? - לצפות איך עושים הקלטה 11!!!!!!!!!!!!
    //****************************************

    FusedLocationProviderClient fusedLocationProviderClient;

    public AddingReportFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AddingReportViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adding_report, container, false);
        problemEt = view.findViewById(R.id.addingReport_et_problem);
        noteEt = view.findViewById(R.id.addingReport_et_notes);
        photoIBtn = view.findViewById(R.id.addingReport_ibtn_photo);
        photo = view.findViewById(R.id.addingReport_iv_photo);
        //******************************************************************************************
        //// to add the photo adding implementation !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //******************************************************************************************

        cancelBtn = view.findViewById(R.id.addingReport_btn_cancel);
        reportBtn = view.findViewById(R.id.addingReport_btn_report);
        ProgressBar pb = view.findViewById(R.id.addingReport_progressBar);
        pb.setVisibility(View.GONE);
        viewModel.setUsername(AddingReportFragmentArgs.fromBundle(getArguments()).getUsername());

        // TODO: צריך להחזיר את השורה הזאת כנסיים לממש את ה- GPS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        viewModel.setUsername(AddingReportFragmentArgs.fromBundle(getArguments()).getUsername());

        // initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        photoIBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

//            if(intent.resolveActivity(getActivity().getPackageManager()) != null){
//                getActivity().startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                reportBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                Report report = new Report();
//                report.setReportID(Report.getIdCounter());   // TODO: need to delete this line
                report.setProblem(problemEt.getText().toString());
                report.setNotes(noteEt.getText().toString());
                report.setUserName(viewModel.getUsername());
                activateGPS(report, v);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            photo.setImageBitmap(bitmap);
        }
    }

    public void activateGPS(Report report, View v)
    {
        // check permission
        // TODO: check for replacing MyApplication
        if(ActivityCompat.checkSelfPermission(MyApplication.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // permission granted
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    Log.d("TAG", "latitude" + location.getLatitude());
                    Log.d("TAG", "latitude" + location.getLongitude());
                    report.setLatitude(location.getLatitude());
                    report.setLongitude(location.getLongitude());
                    Model.getInstance().addNewReport(report,()->{
                        Navigation.findNavController(v).navigateUp();
                    });
                }
            });
        }
        else {
            // permission denied
            // TODO: check what does the number of the requestCode mean
            // TODO: check if what to do after requesting permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    }
