package com.example.yedidim;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.yedidim.Model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class EditReportFragment extends editAddReportFatherFragment {
    private EditReportViewModel viewModel;
    private EditText problem;
    private EditText notes;
    private Button cancelBtn;
    private Button saveBtn;
    private ImageView photo;
    private View view;
    private ProgressBar pb;

    public EditReportFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditReportViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String reportId = EditReportFragmentArgs.fromBundle(getArguments()).getReportID();

        view = inflater.inflate(R.layout.fragment_edit_report, container, false);
        problem = view.findViewById(R.id.editReport_et_problem);
        notes = view.findViewById(R.id.editReport_et_notes);
        photo = view.findViewById(R.id.editReport_iv_photo);
        setEditPhoto(view.findViewById(R.id.editReport_iv_photo));
        ImageButton cameraBtn = view.findViewById(R.id.editReport_btn_camera);
        ImageButton galleryBtn = view.findViewById(R.id.editReport_btn_gallery);
        ImageButton deleteBtn = view.findViewById(R.id.editReport_btn_delete);
        pb = view.findViewById(R.id.editReport_progressBar);
        pb.setVisibility(View.VISIBLE);
        setFusedLocationProviderClient(LocationServices.getFusedLocationProviderClient(getActivity()));
        setState(false);

        Model.getInstance().getReportByID(reportId, (r) -> {
            viewModel.setReport(r);
            showReportDetails();
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.setImageResource(R.drawable.car);
                viewModel.getReport().setReportUrl(null);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);
            }
        });

        cancelBtn = view.findViewById(R.id.editReport_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        saveBtn = view.findViewById(R.id.editReport_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                saveBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                if(!checkCondition()) {
                    save(v, viewModel.getReport().getReportID(),problem, notes);
                }
                else{
                    pb.setVisibility(View.GONE);
                    saveBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                }
            }
        });
        return view;
    }

    private void showReportDetails() {
        problem.setText(viewModel.getReport().getProblem());
        notes.setText(viewModel.getReport().getNotes());
        String url = viewModel.getReport().getReportUrl();
        if (url != null && !url.equals("")) {
            Picasso.get().load(url).placeholder(R.drawable.car).into(photo);
        } else {
            photo.setImageResource(R.drawable.car);
        }
        pb.setVisibility(View.INVISIBLE);
    }

    private boolean checkCondition(){
        if(problem.getText().toString().equals("")){
            problem.setError("problem is required");
            problem.requestFocus();
            return true;
        }
        return false;
    }

}