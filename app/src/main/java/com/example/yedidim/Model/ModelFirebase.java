package com.example.yedidim.Model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.google.firebase.firebaseStorage.FirebaseStorage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ModelFirebase {
    final static String REPORTS = "reports";
    final static String USERS = "users";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getUsersList(Model.GetAllUsersListener listener) {
        db.collection(USERS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<User> usersList = new LinkedList<User>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        //TODO: - NOTE: moved to user class as fromJson function
//                        Map<String, Object> json = doc.getData();
//                        User user = new User();
//                        user.setUserName((String)json.get("username"));
//                        user.setUserName((String)json.get("password"));
//                        user.setUserName((String)json.get("firstName"));
//                        user.setUserName((String)json.get("lastName"));
//                        user.setUserName((String)json.get("phoneNumber"));
//                        user.setUserName((String)json.get("carNumber"));
//                        user.setUserName((String)json.get("vehicleBrand"));
//                        user.setUserName((String)json.get("manufactureYear"));
//                        user.setUserName((String)json.get("fuelType"));
                        User user = User.fromJson(doc.getData());
                        if(user != null)
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
        //TODO: NOTE: moved to the user class as - toJson function
//        Map<String, Object> json = new HashMap<>();
//        json.put("username", user.getUserName());
//        json.put("firstName", user.getFirstName());
//        json.put("lastName", user.getLastName());
//        json.put("password", user.getPassword());
//        json.put("phoneNumber", user.getPhoneNumber());
//        json.put("carNumber", user.getCarNumber());
//        json.put("vehicleBrand", user.getVehicleBrand());
//        json.put("manufactureYear", user.getManufactureYear());
//        json.put("fuelType", user.getFuelType());

        // Add a new document with a username as the ID
        db.collection(USERS).document(user.getUserName()).set(user.toJson())
                .addOnSuccessListener((successListener)-> {
                    listener.onComplete();

                })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());

                });
    }

    public void getUserByUserName(String userName, Model.getUserByUserNameListener listener) {
        DocumentReference docRef = db.collection(USERS).document(userName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            // TODO: need to handle cases were student returns as null
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //TODO: NOTE - moved to the user class as fromJson function
//                        Map<String, Object> json = document.getData();
//                        User user = new User();
//                        user.setUserName((String)json.get("username"));
//                        user.setPassword((String)json.get("password"));
//                        user.setFirstName((String)json.get("firstName"));
//                        user.setLastName((String)json.get("lastName"));
//                        user.setPhoneNumber((String)json.get("phoneNumber"));
//                        user.setCarNumber((String)json.get("carNumber"));
//                        user.setVehicleBrand((String)json.get("vehicleBrand"));
//                        user.setManufactureYear((String)json.get("manufactureYear"));
//                        user.setFuelType((String)json.get("fuelType"));
                        User user = User.fromJson(document.getData());
                        if(user != null)
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
        //TODO: when we delete the user - to delete all his reports too ?
        db.collection(USERS).document(user.getUserName())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onComplete(); // TODO:????
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document ", e);
                    }
                });
    }

    public void editUser(User user, Model.editUserListener listener) {
        // update user's details
        //TODO: NOTE: moved to the user class as - toJson function

//        Map<String, Object> json = new HashMap<>();
//        json.put("username", user.getUserName());
//        json.put("firstName", user.getFirstName());
//        json.put("lastName", user.getLastName());
//        json.put("password", user.getPassword());
//        json.put("phoneNumber", user.getPhoneNumber());
//        json.put("carNumber", user.getCarNumber());
//        json.put("vehicleBrand", user.getVehicleBrand());
//        json.put("manufactureYear", user.getManufactureYear());
//        json.put("fuelType", user.getFuelType());

        // update an existing document document with a username as the ID
        db.collection(USERS).document(user.getUserName()).set(user.toJson())
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

    public void getReportsList(Long since, Model.GetAllReportsListener listener) {
        db.collection(REPORTS).whereGreaterThanOrEqualTo(Report.LAST_UPDATED,new Timestamp(since, 0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Report> reportsList = new LinkedList<Report>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        Report r = Report.fromJson(doc.getId(), doc.getData());
                        if(r != null)
                            reportsList.add(r);
                    }
                }
                else { }
                listener.onComplete(reportsList);
            }
        });
    }

    public void addNewReport(Report report, Model.addNewReportListener listener) {
        // Create a new report
        //TODO: note: moved to the report class - called toJson
//        Map<String, Object> json = new HashMap<>();
////        json.put("reportID", report.getReportID());   the reportID comes from the document name
//        json.put("problem", report.getProblem());
//        json.put("notes", report.getNotes());
//        json.put("username", report.getUserName());
//        json.put("longitude", report.getLongitude());
//        json.put("latitude", report.getLatitude());
//        // TODO: need to add photo
//        // json.put("image", report.getImage());

        // Add a new document

        //TODO: check that value of method works
        Task<DocumentReference> ref = db.collection(REPORTS).add(report.toJson());
        ref.addOnSuccessListener((successListener)-> {
            listener.onComplete();
            Log.d( "TAGs", ref.getResult().getId());
            // TODO: get reportID with documentReference.getId()
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());

                });

        /*  try
        db.collection(REPORTS).add(report.toJson())
                .addOnSuccessListener((successListener)-> {
                    listener.onComplete();
                    // TODO: get reportID with documentReference.getId()
                })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());

                });

         */
    }
    

    public void getReportByID(String reportID, Model.getReportByReportIDListener listener) {
        DocumentReference docRef = db.collection(REPORTS).document(reportID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            // TODO: need to handle cases were student returns as null
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //TODO: moved to report class as fromJson
//                        Map<String, Object> json = document.getData();
//                        Report report = new Report();
//                        report.setReportID(document.getId());   // TODO: check if it works
//                        report.setUserName((String)json.get("username"));
//                        report.setProblem((String)json.get("problem"));
//                        report.setNotes((String)json.get("notes"));
//                        report.setLongitude((double)json.get("longitude"));  // TODO: check how to cast here
//                        report.setLatitude((double)json.get("latitude"));  // TODO: check how to cast here
                        Report r = Report.fromJson(document.getId(), document.getData());
                        if(r != null)
                            listener.onComplete(r);
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
        db.collection(REPORTS).document(report.getReportID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onComplete(); //TODO: ????
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document ", e);
                    }
                });
    }

    public void editReport(Report report, Model.editReportListener listener) {
        // edit a report
        //TODO - moved to report class as toJson
//        Map<String, Object> json = new HashMap<>();
//        json.put("reportID", report.getReportID());
//        json.put("problem", report.getProblem());
//        json.put("notes", report.getNotes());
//        json.put("username", report.getUserName());
//        json.put("longitude", report.getLongitude());
//        json.put("latitude", report.getLatitude());
        // TODO: need to add photo
        // json.put("image", report.getImage());

        // edit an existing document
        db.collection(REPORTS).document(report.getReportID()).set(report.toJson())
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

    public void getUserReportsList(String username, Long since, Model.GetUserReportsListener listener) {
        db.collection(REPORTS).whereGreaterThanOrEqualTo(Report.LAST_UPDATED,new Timestamp(since, 0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Report> reportsList = new LinkedList<Report>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        Map<String, Object> json = doc.getData();
                        if(((String)json.get("username")).equals(username)) {
                            //TODO:note- moved to the report class as fromJson
//                            Report report = new Report();
//                            report.setReportID((String) doc.getId());
//                            report.setProblem((String) json.get("problem"));
//                            report.setNotes((String) json.get("notes"));
//                            report.setUserName((String) json.get("username"));
//                            report.setLatitude((double) json.get("latitude"));
//                            report.setLongitude((double) json.get("longitude"));
                            Report report = Report.fromJson(doc.getId(), doc.getData());
                            if(report != null)
                                reportsList.add(report);
                        }
                    }
                }
                else { }
                listener.onComplete(reportsList);
            }
        });
    }
    public void saveImage(Bitmap bitmap,String name, Model.saveImageListener listener) {
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("report/"+ name+".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
            Uri downloadUrl = uri;
            listener.onComplete(downloadUrl.toString());
        }));

    }
}
