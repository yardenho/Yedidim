package com.example.yedidim;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;

import android.net.Uri;
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
import android.widget.TextView;


import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddingReportFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE=1;
    static final int GET_FROM_GALLERY=2;
    private AddingReportViewModel viewModel;
    private Button cancelBtn;
    private Button reportBtn;
    private EditText problemEt;
    private EditText noteEt;
    private ImageView photo;
    private ImageButton photoIBtn;
    private ImageButton galleryBtn;
    Bitmap bitmap;    // maybe we need to put it in view model??
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
        galleryBtn = view.findViewById(R.id.addingReport_ib_gallery);
        photo = view.findViewById(R.id.addingReport_iv_photo);
        cancelBtn = view.findViewById(R.id.addingReport_btn_cancel);
        reportBtn = view.findViewById(R.id.addingReport_btn_report);
        ProgressBar pb = view.findViewById(R.id.addingReport_progressBar);
        pb.setVisibility(View.GONE);
        TextView problemError = view.findViewById(R.id.addingReport_problemErrorMessage);
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

        galleryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, GET_FROM_GALLERY);
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (problemEt.getText().toString().equals("")) {
                    problemError.setVisibility(View.VISIBLE);
                }
                else{
                    pb.setVisibility(View.VISIBLE);
                    reportBtn.setEnabled(false);
                    cancelBtn.setEnabled(false);
                    Report report = new Report();
                    report.setProblem(problemEt.getText().toString());
                    report.setNotes(noteEt.getText().toString());
                    report.setUserName(viewModel.getUsername());
                    if (bitmap != null) {
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        Model.getInstance().saveImage(bitmap, report.getUserName() + currentTime, url -> { // במקום מחרוזת קבועה מספר מזהה של דיווח
                            report.setReportUrl(url);
                            activateGPS(report, v);
                        });
                    } else {
                        report.setReportUrl(null);
                        activateGPS(report, v);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            photo.setImageBitmap(bitmap);
        }
        else if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = (Bitmap)MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.d("TAGS", "file not found");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
