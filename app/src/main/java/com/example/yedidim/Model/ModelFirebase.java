package com.example.yedidim.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getUsersList(Model.GetAllUsersListener listener) {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<User> usersList = new LinkedList<User>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        Map<String, Object> json = doc.getData();
                        User user = new User();
                        user.setUserName((String)json.get("username"));
                        user.setUserName((String)json.get("password"));
                        user.setUserName((String)json.get("firstName"));
                        user.setUserName((String)json.get("lastName"));
                        user.setUserName((String)json.get("phoneNumber"));
                        user.setUserName((String)json.get("carNumber"));
                        user.setUserName((String)json.get("vehicleBrand"));
                        user.setUserName((String)json.get("manufactureYear"));
                        user.setUserName((String)json.get("fuelType"));
                        usersList.add(user);
                    }
                }
                else { }
                listener.onComplete(usersList);
            }
        });
    }

    public void addNewUser(User user, Model.addNewUserListener listener) {
        // Create a new user
        Map<String, Object> json = new HashMap<>();
        json.put("username", user.getUserName());
        json.put("firstName", user.getFirstName());
        json.put("lastName", user.getLastName());
        json.put("password", user.getPassword());
        json.put("phoneNumber", user.getPhoneNumber());
        json.put("carNumber", user.getCarNumber());
        json.put("vehicleBrand", user.getVehicleBrand());
        json.put("manufactureYear", user.getManufactureYear());
        json.put("fuelType", user.getFuelType());

        // Add a new document with a username as the ID
        db.collection("users").document(user.getUserName()).set(json)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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
        DocumentReference docRef = db.collection("users").document(userName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            // TODO: need to handle cases were student returns as null
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> json = document.getData();
                        User user = new User();
                        user.setUserName((String)json.get("username"));
                        user.setPassword((String)json.get("password"));
                        user.setFirstName((String)json.get("firstName"));
                        user.setLastName((String)json.get("lastName"));
                        user.setPhoneNumber((String)json.get("phoneNumber"));
                        user.setCarNumber((String)json.get("carNumber"));
                        user.setVehicleBrand((String)json.get("vehicleBrand"));
                        user.setManufactureYear((String)json.get("manufactureYear"));
                        user.setFuelType((String)json.get("fuelType"));
                        listener.onComplete(user);
                    } else {
                        listener.onComplete(null);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }

    public void deleteUser(User user, Model.deleteUserListener listener) {
    }

    public void editUser(User user, Model.editUserListener listener) {
        // update user's details
        Map<String, Object> json = new HashMap<>();
        json.put("username", user.getUserName());
        json.put("firstName", user.getFirstName());
        json.put("lastName", user.getLastName());
        json.put("password", user.getPassword());
        json.put("phoneNumber", user.getPhoneNumber());
        json.put("carNumber", user.getCarNumber());
        json.put("vehicleBrand", user.getVehicleBrand());
        json.put("manufactureYear", user.getManufactureYear());
        json.put("fuelType", user.getFuelType());

        // update an existing document document with a username as the ID
        db.collection("users").document(user.getUserName()).set(json)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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

    public void getReportsList(Model.GetAllReportsListener listener) {
        db.collection("reports").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Report> reportsList = new LinkedList<Report>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        Map<String, Object> json = doc.getData();
                        Report report = new Report();
                        report.setReportID((String)doc.getId());
                        report.setProblem((String)json.get("problem"));
                        report.setNotes((String)json.get("notes"));
                        report.setUserName((String)json.get("username"));
                        report.setLatitude((double)json.get("latitude"));
                        report.setLongitude((double)json.get("longitude"));
                        reportsList.add(report);
                    }
                }
                else { }
                listener.onComplete(reportsList);
            }
        });
    }

    public void addNewReport(Report report, Model.addNewReportListener listener) {
        // Create a new report
        Map<String, Object> json = new HashMap<>();
//        json.put("reportID", report.getReportID());   the reportID comes from the document name
        json.put("problem", report.getProblem());
        json.put("notes", report.getNotes());
        json.put("username", report.getUserName());
        json.put("longitude", report.getLongitude());
        json.put("latitude", report.getLatitude());
        // TODO: need to add photo
        // json.put("image", report.getImage());

        // Add a new document

        //TODO: check that value of method works
        db.collection("reports").add(json)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onComplete();
                        // TODO: get reportID with documentReference.getId()
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
        DocumentReference docRef = db.collection("reports").document(reportID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            // TODO: need to handle cases were student returns as null
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> json = document.getData();
                        Report report = new Report();
                        report.setReportID(document.getId());   // TODO: check if it works
                        report.setUserName((String)json.get("username"));
                        report.setProblem((String)json.get("problem"));
                        report.setNotes((String)json.get("notes"));
                        report.setLongitude((double)json.get("longitude"));  // TODO: check how to cast here
                        report.setLatitude((double)json.get("latitude"));  // TODO: check how to cast here
                        listener.onComplete(report);
                    } else {
                        listener.onComplete(null);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }

    public void deleteReport(Report report, Model.deleteReportListener listener) {
    }

    public void editReport(Report report, Model.editReportListener listener) {
        // edit a report
        Map<String, Object> json = new HashMap<>();
        json.put("reportID", report.getReportID());
        json.put("problem", report.getProblem());
        json.put("notes", report.getNotes());
        json.put("username", report.getUserName());
        json.put("longitude", report.getLongitude());
        json.put("latitude", report.getLatitude());
        // TODO: need to add photo
        // json.put("image", report.getImage());

        // edit an existing document
        db.collection("reports").document(report.getReportID()).set(json)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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

    public void getUserReportsList(String username, Model.GetUserReportsListener listener) {
        db.collection("reports").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Report> reportsList = new LinkedList<Report>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        Map<String, Object> json = doc.getData();
                        if(((String)json.get("username")).equals(username)) {
                            Report report = new Report();
                            report.setReportID((String) doc.getId());
                            report.setProblem((String) json.get("problem"));
                            report.setNotes((String) json.get("notes"));
                            report.setUserName((String) json.get("username"));
                            report.setLatitude((double) json.get("latitude"));
                            report.setLongitude((double) json.get("longitude"));
                            reportsList.add(report);
                        }
                    }
                }
                else { }
                listener.onComplete(reportsList);
            }
        });
    }
}
