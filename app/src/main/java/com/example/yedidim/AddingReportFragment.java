package com.example.yedidim;

import android.content.Intent;
import android.os.Bundle;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;


public class AddingReportFragment extends editAddReportFatherFragment {
    private Button cancelBtn;
    private Button reportBtn;
    private EditText problemEt;
    private EditText noteEt;
    private ImageButton photoIBtn;
    private ImageButton galleryBtn;
    private ProgressBar pb;

    public AddingReportFragment() {
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
        setAddPhoto(view.findViewById(R.id.addingReport_iv_photo));
        cancelBtn = view.findViewById(R.id.addingReport_btn_cancel);
        reportBtn = view.findViewById(R.id.addingReport_btn_report);
        pb = view.findViewById(R.id.addingReport_progressBar);
        pb.setVisibility(View.GONE);
        setState(true);
        // initialize fusedLocationProviderClient
        setFusedLocationProviderClient(LocationServices.getFusedLocationProviderClient(getActivity()));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        photoIBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        });

        galleryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, GET_FROM_GALLERY);
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                reportBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                if (problemEt.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    reportBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    problemEt.setError("Problem is required");
                    problemEt.requestFocus();
                }
                else{
                    save(view,null, problemEt, noteEt);

                }
            }
        });
        return view;
    }

    @Override
    public void failAction() {
        super.failAction();
        pb.setVisibility(View.GONE);
        reportBtn.setEnabled(true);
        cancelBtn.setEnabled(true);
        Toast.makeText(getActivity(), "failed to add the report, please try again", Toast.LENGTH_LONG).show();
    }
}
