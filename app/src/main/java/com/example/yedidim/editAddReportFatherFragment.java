package com.example.yedidim;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
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
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class editAddReportFatherFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE=1;
    static final int GET_FROM_GALLERY=2;
    private View view;
    private Bitmap bitmap;
    private ImageView addPhoto;
    private ImageView editPhoto;
    private boolean state;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_add_report_father, container, false);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            if(state)
                addPhoto.setImageBitmap(bitmap);
            else
                editPhoto.setImageBitmap(bitmap);
        }
        else if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                if(state)
                    addPhoto.setImageBitmap(bitmap);
                else
                    editPhoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.d("TAGS", "file not found");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void activateGPS(View v,Report report)
    {
        // check permission
        if(ActivityCompat.checkSelfPermission(MyApplication.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // permission granted
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    report.setLatitude(location.getLatitude());
                    report.setLongitude(location.getLongitude());
                    if(state)
                        Model.getInstance().addNewReport(report,()->{
                            Navigation.findNavController(v).navigateUp();
                        });
                    else
                        Model.getInstance().editReport(report,()->{
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

    public void save(View v, String reportID, EditText problemEt, EditText noteEt){
        Report report = new Report();
        Model.getInstance().getCurrentUser(new Model.getCurrentUserListener() {
            @Override
            public void onComplete(String userEmail) {
                report.setUserName(userEmail);
                report.setProblem(problemEt.getText().toString());
                report.setNotes(noteEt.getText().toString());
                if(reportID != null)
                    report.setReportID(reportID);
                if (bitmap != null) {
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    Model.getInstance().saveImage(bitmap, report.getUserName() + currentTime, url -> { // במקום מחרוזת קבועה מספר מזהה של דיווח
                        report.setReportUrl(url);
                        activateGPS(v, report);
                    });
                } else {
                    if(!state)
                        Model.getInstance().getReportByID(reportID, (r)-> {
                            report.setReportUrl(r.getReportUrl());
                            activateGPS(v, report);
                        });
                    else {
                        report.setReportUrl(null);
                        activateGPS(v, report);
                    }
                }
            }
        });
    }

    public void setState(boolean b){
        state = b;
    }

    public void setAddPhoto(ImageView addPhoto) {
        this.addPhoto = addPhoto;
    }

    public void setEditPhoto(ImageView editPhoto){
        this.editPhoto = editPhoto;
    }
    public void setFusedLocationProviderClient(FusedLocationProviderClient f){
        fusedLocationProviderClient = f;
    }

}