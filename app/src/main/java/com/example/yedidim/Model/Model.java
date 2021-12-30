package com.example.yedidim.Model;

import com.example.yedidim.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class Model {
    static  final private Model instance = new Model();
    private List<User> userList =new LinkedList<User>();
    private List<Malfunction> malfunctionList =new LinkedList<Malfunction>();

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

        MyApplication.executorService.execute(()->{
            AppLocalDB.db.userDao().insertAll(user);
            MyApplication.mainHandler.post(()->{
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



    private Model(){}

    public static Model getInstance(){return instance;}

    public interface GetAllMalfunctionsListener{
        void onComplete(List<Malfunction> data);
    }

    public void getMalfunctionsList(GetAllMalfunctionsListener listener){
        MyApplication.executorService.execute(()->{
            List <Malfunction> data = AppLocalDB.db.malfunctionDao().getAll();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });
    }

    public interface addNewMalfunctionListener{
        void onComplete();
    }

    public void addNewMalfunction(Malfunction malfunction,addNewMalfunctionListener listener){

        MyApplication.executorService.execute(()->{
            AppLocalDB.db.malfunctionDao().insertAll(malfunction);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface getMalfunctionByMalfunctionIDListener{
        void onComplete(Malfunction malfunction);
    }

    public void getStudentByID(String malfunctionID, getMalfunctionByMalfunctionIDListener listener)
    {
        MyApplication.executorService.execute(()->{
            Malfunction malfunction = AppLocalDB.db.malfunctionDao().getMalfunctionByID(malfunctionID);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(malfunction);
            });
        });
    }

    public interface deleteMalfunctionByMalfunctionIDListener{
        void onComplete();
    }

    public void deleteStudentByID(Malfunction malfunction,deleteMalfunctionByMalfunctionIDListener listener )
    {
        MyApplication.executorService.execute(()->{
            AppLocalDB.db.malfunctionDao().delete(malfunction);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public interface editMalfunctionListener{
        void onComplete();
    }

    public void editStudent(Malfunction malfunction,editMalfunctionListener listener){
        MyApplication.executorService.execute(()->{
            AppLocalDB.db.malfunctionDao().editMalfunction(malfunction);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }



}

