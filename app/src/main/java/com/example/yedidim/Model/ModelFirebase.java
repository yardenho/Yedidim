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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuth; // for authentication with firebase
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.Map;

public class ModelFirebase {
    final static String REPORTS = "reports";
    final static String USERS = "users";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();

    public void getCurrentUser(Model.getCurrentUserListener listener)
    {
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currUser == null)
            listener.onComplete(null);
        else {
            listener.onComplete(currUser.getEmail());
        }
    }

    public void addNewUser(User user, String password, Model.addNewUserListener listener) {
        // Create a new user
        mAuth.createUserWithEmailAndPassword(user.getUserName(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Add a new document with a username as the ID
                            db.collection(USERS).document(user.getUserName()).set(user.toJson())
                                    .addOnSuccessListener((successListener)-> {
                                        listener.onComplete(true);
                                    })
                                    .addOnFailureListener((e)-> {
                                        Log.d("TAG", e.getMessage());
                                        //TODO: delete the user from the authentication
                                    });
                        }
                        else {
                            listener.onComplete(false);
                        }
                    }
                });
    }

    public void loginUser(String email, String password, Model.loginUserListener listener)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            currUser = FirebaseAuth.getInstance().getCurrentUser();
                            listener.onComplete(true);
                        }
                        else
                        {
                            listener.onComplete(false);
                        }
                    }
                });
    }

    public void logOutUser(Model.logOutUserListener listener) {
        FirebaseAuth.getInstance().signOut();
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        listener.onComplete();
    }

    public void getUserByUserName(String userName, Model.getUserByUserNameListener listener) {
        DocumentReference docRef = db.collection(USERS).document(userName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
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

    public void editUser(User user, Model.editUserListener listener) {
        // update user's details
        // update an existing document document with a username as the ID
        db.collection(USERS).document(user.getUserName()).set(user.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                        listener.onComplete(false);
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
                listener.onComplete(reportsList);
            }
        });
    }

    public void addNewReport(Report report, Model.addNewReportListener listener) {
        Task<DocumentReference> ref = db.collection(REPORTS).add(report.toJson());
        ref.addOnSuccessListener((successListener)-> {
            listener.onComplete(true);
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                    listener.onComplete(false);
                });
    }
    

    public void getReportByID(String reportID, Model.getReportByReportIDListener listener) {
        DocumentReference docRef = db.collection(REPORTS).document(reportID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
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

    public void editReport(Report report, Model.editReportListener listener) {

        // edit an existing document
        db.collection(REPORTS).document(report.getReportID()).set(report.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                        listener.onComplete(false);
                    }
                });
    }

    public void getUserReportsList(Long since, Model.GetUserReportsListener listener) {
        db.collection(REPORTS).whereGreaterThanOrEqualTo(Report.LAST_UPDATED,new Timestamp(since, 0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Report> reportsList = new LinkedList<Report>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        currUser = FirebaseAuth.getInstance().getCurrentUser();
                        if(currUser != null) {
                            String userEmail = currUser.getEmail();
                            Map<String, Object> json = doc.getData();
                            if (((String) json.get("username")).equals(userEmail)) {
                                Report report = Report.fromJson(doc.getId(), doc.getData());
                                if (report != null)
                                    reportsList.add(report);
                            }
                        }
                    }
                }
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
