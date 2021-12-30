package com.example.yedidim.Model;

import com.example.yedidim.MyApplication;

import java.util.LinkedList;
import java.util.List;

///מחבר לנו בין הפרגמנטים לDATA
public class Model {

    static final private Model instance = new Model();

    private List<User> userList =new LinkedList<User>();
    private List<Report> ReportList =new LinkedList<Report>();



    private Model(){}

    public static Model getInstance(){
        return instance;
    }

    public interface GetAllUsersListener{
        void onComplete(List<User> data);

    }

    public void getUsersList(GetAllUsersListener listener){
        MyApplication.executorService.execute(()->{
            List <User> data = AppLocalDB.db.userDao().getAll();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });
    }

    public interface addNewUserListener{
        void onComplete();
    }

    public void addNewUser(User user,addNewUserListener listener){
            MyApplication.executorService.execute(() -> {
                AppLocalDB.db.userDao().insertAll(user);
                MyApplication.mainHandler.post(() -> {
                    listener.onComplete();
                });
            });

    }

    public interface getUserByUserNameListener{
        void onComplete(User user);
    }

    public void getUserByUserName(String userName, getUserByUserNameListener listener)
    {
        MyApplication.executorService.execute(()->{
            User user = AppLocalDB.db.userDao().getUserByUserName(userName);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(user);
            });
        });
    }

    public interface deleteUserListener{
        void onComplete();
    }


    public void deleteUser(User user,deleteUserListener listener )
    {
        MyApplication.executorService.execute(()->{
            AppLocalDB.db.userDao().delete(user);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface editUserListener{
        void onComplete();
    }

    // updating by key member of user which is userName
    public void editUser(User u,editUserListener listener){
        MyApplication.executorService.execute(()->{
            AppLocalDB.db.userDao().editUser(u);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }




    public interface GetAllReportsListener{
        void onComplete(List<Report> data);
    }

    public void getReportsList(GetAllReportsListener listener){
        MyApplication.executorService.execute(()->{
            List <Report> data = AppLocalDB.db.reportDao().getAll();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });
    }

    public interface addNewReportListener{
        void onComplete();
    }

    public void addNewReport(Report report,addNewReportListener listener){

        MyApplication.executorService.execute(()->{
            AppLocalDB.db.reportDao().insertAll(report);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface getReportByReportIDListener{
        void onComplete(Report report);
    }

    public void getReportByID(String reportID, getReportByReportIDListener listener)
    {
        MyApplication.executorService.execute(()->{
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
        MyApplication.executorService.execute(()->{
            AppLocalDB.db.reportDao().delete(report);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface editReportListener{
        void onComplete();
    }

    public void editReport(Report report,editReportListener listener){
        MyApplication.executorService.execute(()->{
            AppLocalDB.db.reportDao().editReport(report);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }


}

