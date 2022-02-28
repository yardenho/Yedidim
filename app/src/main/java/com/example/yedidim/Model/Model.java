package com.example.yedidim.Model;

import android.graphics.Bitmap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.yedidim.MyApplication;
import java.util.List;

///מחבר לנו בין הפרגמנטים לDATA
public class Model {

    static final private Model instance = new Model();
    private ModelFirebase modelFirebase = new ModelFirebase();
    private MutableLiveData<List<Report>> reportsListLd = new MutableLiveData<List<Report>>();
    private MutableLiveData<List<Report>> userReportsListLd = new MutableLiveData<List<Report>>();
    //האובייקט שכולם יאזינו אליו
    private MutableLiveData<LoadingState> reportsListLoadingState = new MutableLiveData<LoadingState>();

    public enum LoadingState{
        loading,
        loaded
    }

    private Model(){
        reportsListLoadingState.setValue(LoadingState.loaded);
        reloadReportsList();
    }
    public static Model getInstance(){
        return instance;
    }

    //לא יהיה SET כי אף אחד לא נוגע בו, המודל יהיה זה שאחראי לתקשורת אז הוא זה שישלוט בו, ניתן לתת אפשרות לעידכון דאטא אבל כרגע היחיד שמשנה הוא המודל
    public LiveData<LoadingState> getReportsListLoadingState(){ return reportsListLoadingState;}


    public interface saveImageListener{
        void onComplete(String url);
    }
    public void saveImage(Bitmap bitmap,String name, saveImageListener listener) {
        modelFirebase.saveImage(bitmap,name, listener);
    }


    public interface addNewUserListener{
        void onComplete(boolean ifSuccess);
    }

    public void addNewUser(User user,String password, addNewUserListener listener){
        modelFirebase.addNewUser(user, password, listener);
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
        modelFirebase.getUserByUserName(userName, listener);
    }


    public interface getCurrentUserListener{
        void onComplete(String userEmail);
    }

    public void getCurrentUser(getCurrentUserListener listener)
    {
        modelFirebase.getCurrentUser(listener);
    }

    public interface editUserListener{
        void onComplete(boolean ifSuccess);
    }

    // updating by key member of user which is userName
    public void editUser(User u,editUserListener listener){
        modelFirebase.editUser(u, listener);
    }


    public interface GetAllReportsListener{
        void onComplete(List<Report> data);
    }

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
                    if(r.getIsDeleted())// if the report is deleted in the firebase, delete him from the cache
                        AppLocalDB.db.reportDao().delete(r);
                    if(r.getLastUpdated() > lLastUpdate){
                        lLastUpdate = r.getLastUpdated();
                    }
                }
                Report.setLocalLastUpdated(lLastUpdate);
                //return all records to the caller
                List<Report> repList = AppLocalDB.db.reportDao().getAll();
                reportsListLd.postValue(repList);
                reloadUserReportsList();
                reportsListLoadingState.postValue(LoadingState.loaded);// סיום הטעינה

            });

        });
    }

    public LiveData<List<Report>> getAllReports(){
        return reportsListLd;
    }

    public interface addNewReportListener{
        void onComplete(boolean ifSuccess);
    }

    public void addNewReport(Report report,addNewReportListener listener){
        modelFirebase.addNewReport(report, (success)->{
            reloadReportsList();
            listener.onComplete(success);
        });
    }

    public interface getReportByReportIDListener{
        void onComplete(Report report);
    }

    public void getReportByID(String reportID, getReportByReportIDListener listener)
    {
//        modelFirebase.getReportByID(reportID, listener);
        MyApplication.executorService.execute(()-> {
            Report report = AppLocalDB.db.reportDao().getReportByID(reportID);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(report);
            });
        });

    }

    public interface deleteReportListener{
        void onComplete();
    }

    public void deleteReport(Report report,deleteReportListener listener )
    {
        report.setIsDeleted(true);
        modelFirebase.editReport(report,(success)->{
            if (success) {
                reloadReportsList();
                listener.onComplete();
            }
        });
    }

    public interface editReportListener{
        void onComplete(boolean ifSuccess);
    }

    public void editReport(Report report,editReportListener listener){
        modelFirebase.editReport(report, (success)->{
            if(success) {
                reloadReportsList();
                listener.onComplete(true);
            }
        });
    }

    public LiveData<List<Report>> getAllUserReports(){
        return userReportsListLd;
    }

    public void reloadUserReportsList() {
        modelFirebase.getCurrentUser(new getCurrentUserListener() {
            @Override
            public void onComplete(String userEmail) {
                MyApplication.executorService.execute(()-> {
                    List<Report> userRepList = AppLocalDB.db.reportDao().getMyReports(userEmail);
                    userReportsListLd.postValue(userRepList);
                });
            }
        });
    }
}

