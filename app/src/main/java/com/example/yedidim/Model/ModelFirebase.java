package com.example.yedidim.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getUsersList(Model.GetAllUsersListener listener) {
    }

    public void addNewUser(User user, Model.addNewUserListener listener) {
        // Create a new user
        Map<String, Object> json = new HashMap<>();
        json.put("userName", user.getUserName());
        json.put("firstName", user.getFirstName());
        json.put("lastName", user.getLastName());
        json.put("password", user.getPassword());
        json.put("phoneNumber", user.getPhoneNumber());
        json.put("carNumber", user.getCarNumber());
        json.put("vehicleBrand", user.getVehicleBrand());
        json.put("manufactureYear", user.getManufactureYear());
        json.put("fuelType", user.getFuelType());

        // Add a new document with a generated ID
        db.collection("users")
                .add(json)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }

    public void getUserByUserName(String userName, Model.getUserByUserNameListener listener) {
    }

    public void deleteUser(User user, Model.deleteUserListener listener) {
    }

    public void editUser(User u, Model.editUserListener listener) {
    }

    public void getReportsList(Model.GetAllReportsListener listener) {
    }

    public void addNewReport(Report report, Model.addNewReportListener listener) {
        // Create a new report
        Map<String, Object> json = new HashMap<>();
        json.put("reportID", report.getReportID());
        json.put("problem", report.getProblem());
        json.put("notes", report.getNotes());
        json.put("userName", report.getUserName());
        json.put("longitude", report.getLongitude());
        json.put("latitude", report.getLatitude());
        // TODO: need to add photo
        // json.put("image", report.getImage());

        // Add a new document with a generated ID
        db.collection("reports")
                .add(json)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }
    

    public void getReportByID(String reportID, Model.getReportByReportIDListener listener) {
    }

    public void deleteReport(Report report, Model.deleteReportListener listener) {
    }

    public void editReport(Report report, Model.editReportListener listener) {
    }
}
