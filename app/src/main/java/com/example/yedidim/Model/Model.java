package com.example.yedidim.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yedidim.MyApplication;

import java.util.LinkedList;
import java.util.List;

///מחבר לנו בין הפרגמנטים לDATA
public class Model {

    static final private Model instance = new Model();
    ModelFirebase modelFirebase = new ModelFirebase();

    private List<User> userList =new LinkedList<User>();
    private List<Report> ReportList =new LinkedList<Report>();



    private Model(){
        reportsListLoadingState.setValue(LoadingState.loaded);
        reloadReportsList();
    }

    public enum LoadingState{
        loading,
        loaded
    }

    //האובייקט שכולם יאזינו אליו
    MutableLiveData<LoadingState> reportsListLoadingState = new MutableLiveData<LoadingState>();
    //לא יהיה SET כי אף אחד לא נוגע בוף המודל יהיה זה שאחראי לתקשורת אז הוא זה שישלוט בו, ניתן לתת אפשרות לעידכון דאטא אבל כרגע היחיד שמשנה הוא המודל
    public LiveData<LoadingState> getReportsListLoadingState(){ return reportsListLoadingState;}


    public static Model getInstance(){
        return instance;
    }

    public interface saveImageListener{
        void onComplete(String url);
    }
    public void saveImage(Bitmap bitmap,String name, saveImageListener listener) {
        modelFirebase.saveImage(bitmap,name, listener);
    }

    public interface GetAllUsersListener{
        void onComplete(List<User> data);

    }

    public void getUsersList(GetAllUsersListener listener){
        // TODO: this belong to Firebase
        modelFirebase.getUsersList(listener);
        // TODO: this belong to ROOM
//        MyApplication.executorService.execute(()->{
//            List <User> data = AppLocalDB.db.userDao().getAll();
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete(data);
//            });
//        });
    }

    public interface addNewUserListener{
        void onComplete();
    }

    public void addNewUser(User user,String password, addNewUserListener listener){
        // TODO: this belong to Firebase
        modelFirebase.addNewUser(user, password, listener);

        // TODO: this belong to ROOM

//            MyApplication.executorService.execute(() -> {
//                AppLocalDB.db.userDao().insertAll(user);
//                MyApplication.mainHandler.post(() -> {
//                    listener.onComplete();
//                });
//            });
    }

    public interface loginUserListener{
        void onComplete(boolean success);
    }

    public void loginUser(String email, String password, loginUserListener listener) {
        modelFirebase.loginUser(email, password, listener);
    }

    public interface logOutUserListener{
        void onComplete();
    }

    public void logOutUser(logOutUserListener listener)
    {
        modelFirebase.logOutUser(listener);
    }

    public interface getUserByUserNameListener{
        void onComplete(User user);
    }

    public void getUserByUserName(String userName, getUserByUserNameListener listener)
    {
        // TODO: this belong to Firebase
        modelFirebase.getUserByUserName(userName, listener);

        // TODO: this belong to ROOM

//        MyApplication.executorService.execute(()->{
//            User user = AppLocalDB.db.userDao().getUserByUserName(userName);
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete(user);
//            });
//        });
    }

    public interface deleteUserListener{
        void onComplete();
    }

    public interface getCurrentUserListener{
        void onComplete(String userEmail);
    }

    public void getCurrentUser(getCurrentUserListener listener)
    {
        modelFirebase.getCurrentUser(listener);
    }

    public void deleteUser(User user,deleteUserListener listener )
    {
        // TODO: this belong to Firebase

        modelFirebase.deleteUser(user, listener);
        // TODO: this belong to ROOM
//
//        MyApplication.executorService.execute(()->{
//            AppLocalDB.db.userDao().delete(user);
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete();
//            });
//        });
    }

    public interface editUserListener{
        void onComplete();
    }

    // updating by key member of user which is userName
    public void editUser(User u,editUserListener listener){
        // TODO: this belong to Firebase

        modelFirebase.editUser(u, listener);
        // TODO: this belong to ROOM

//        MyApplication.executorService.execute(()->{
//            AppLocalDB.db.userDao().editUser(u);
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete();
//            });
//        });
    }


    public interface GetAllReportsListener{
        void onComplete(List<Report> data);
    }

//    public void getReportsList(GetAllReportsListener listener){
//        // TODO: this belong to Firebase
//
////        modelFirebase.getReportsList(listener);
//        // TODO: this belong to ROOM
////        MyApplication.executorService.execute(()->{
////            List <Report> data = AppLocalDB.db.reportDao().getAll();
////            MyApplication.mainHandler.post(()->{
////                listener.onComplete(data);
////            });
////        });
//    }

    //TODO: - NEW ! - using in the live data
    MutableLiveData<List<Report>> reportsListLd = new MutableLiveData<List<Report>>();
    public void reloadReportsList(){
        reportsListLoadingState.setValue(LoadingState.loading); //התחלת הטעינה
        //get local last update
        Long localLastUpdate = Report.getLocalLastUpdated();
        //get all reports records since local last update from firebase
        modelFirebase.getReportsList(localLastUpdate,(list)->{

            MyApplication.executorService.execute(()->{
                //update local last update date
                //add new record to the local db
                Long lLastUpdate = new Long(0);
                for(Report r : list) {
                    AppLocalDB.db.reportDao().insertAll(r);
                    if(r.getIsDeleted())// if the report is deleted in the firebase, delete hom from the cache
                        AppLocalDB.db.reportDao().delete(r);
                    if(r.getLastUpdated() > lLastUpdate){
                        lLastUpdate = r.getLastUpdated();
                    }
                }
                Report.setLocalLastUpdated(lLastUpdate);
                //return all records to the caller
                List<Report> repList = AppLocalDB.db.reportDao().getAll();
                reportsListLd.postValue(repList);
                reportsListLoadingState.postValue(LoadingState.loaded);// סיום הטעינה
            });

        });
    }

    public LiveData<List<Report>> getAllReports(){
        return reportsListLd;
    }

    public interface addNewReportListener{
        void onComplete();
    }

    public void addNewReport(Report report,addNewReportListener listener){
        // TODO: this belong to Firebase

//        modelFirebase.addNewReport(report, listener);
        //TODO: related to live data, used: when there is a new report this will let know the list to refresh
        modelFirebase.addNewReport(report, ()->{
            reloadReportsList();
            listener.onComplete();
        });

        // TODO: this belong to ROOM
//        report.setReportID("1");
//        MyApplication.executorService.execute(()->{
//            AppLocalDB.db.reportDao().insertAll(report);
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete();    // TODO: no need to return reportID in ROOM
////                Report.addOneToIdCounter();   // TODO: need to delete
//            });
//        });
    }

    public interface getReportByReportIDListener{
        void onComplete(Report report);
    }

    public void getReportByID(String reportID, getReportByReportIDListener listener)
    {
        // TODO: this belong to Firebase

        modelFirebase.getReportByID(reportID, listener);
        // TODO: this belong to ROOM

//        MyApplication.executorService.execute(()->{
//            Report report = AppLocalDB.db.reportDao().getReportByID(reportID);
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete(report);
//            });
//        });
    }

    public interface deleteReportListener{
        void onComplete();
    }

    public void deleteReport(Report report,deleteReportListener listener )
    {
        modelFirebase.deleteReport(report,()->{
            reloadReportsList();
            listener.onComplete();
        });


        // TODO: this belong to Firebase
        //זה מה שהיה לפני ששיניתי- להחזיר את שורה מתחת אם מה שני מנסה לא עובד לי
//        modelFirebase.deleteReport(report, listener);
        // TODO: this belong to ROOM

//        MyApplication.executorService.execute(()->{
//            AppLocalDB.db.reportDao().delete(report);
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete();
//            });
//        });
    }

    public interface editReportListener{
        void onComplete();
    }

    public void editReport(Report report,editReportListener listener){
        modelFirebase.editReport(report, ()->{
            reloadReportsList();
            listener.onComplete();
        });


        // TODO: this belong to Firebase
        //זה מה שהיה לפני ששיניתי- להחזיר את השורה מתחת  אם מה שאני מנסה לא עובד לי
//        modelFirebase.editReport(report, listener);
        // TODO: this belong to ROOM

//        MyApplication.executorService.execute(()->{
//            AppLocalDB.db.reportDao().editReport(report);
//            MyApplication.mainHandler.post(()->{
//                listener.onComplete();
//            });
//        });
    }

    //get only reports that belong to a specific user
    public interface GetUserReportsListener{
        void onComplete(List<Report> data);
    }


//    public void getUserReportsList(String username, GetUserReportsListener listener){
//        // TODO: this belong to Firebase
//        modelFirebase.getUserReportsList(username, listener);
//
//        // TODO: this belong to ROOM
////        MyApplication.executorService.execute(()->{
////            List <Report> data = AppLocalDB.db.reportDao().getMyReports(username);
////            MyApplication.mainHandler.post(()->{
////                listener.onComplete(data);
////            });
////        });
//    }

    MutableLiveData<List<Report>> userReportsListLd = new MutableLiveData<List<Report>>();

    public LiveData<List<Report>> getAllUserReports(){
        return userReportsListLd;
    }

    public void reloadUserReportsList(){
        reportsListLoadingState.setValue(LoadingState.loading);//התחלת הטעינה - אני הוספתי לחשוב אם צריך

        //get local last update
        Long localLastUpdate = Report.getLocalLastUpdated();
        //get all reports records since local last update from firebase
        modelFirebase.getUserReportsList(localLastUpdate,(list)->{

            MyApplication.executorService.execute(()->{
                //update local last update date
                //add new record to the local db
                Long lLastUpdate = new Long(0);
                for(Report r : list) {

                    AppLocalDB.db.reportDao().insertAll(r);
                    if(r.getIsDeleted()) // if the report is deleted in the firebase, delete hom from the cache
                        AppLocalDB.db.reportDao().delete(r);
                    if(r.getLastUpdated() > lLastUpdate){
                        lLastUpdate = r.getLastUpdated();
                    }
                }
                Report.setLocalLastUpdated(lLastUpdate);
                //return all records to the caller
                modelFirebase.getCurrentUser(new getCurrentUserListener() {
                    @Override
                    public void onComplete(String userEmail) {
                        List<Report> userRepList = AppLocalDB.db.reportDao().getMyReports(userEmail);
                        userReportsListLd.postValue(userRepList);
                        reportsListLoadingState.postValue(LoadingState.loaded); // סיום הטעינה - אני הוספתי לחשוב אם צריך
                    }
                });
            });

        });
    }


}

