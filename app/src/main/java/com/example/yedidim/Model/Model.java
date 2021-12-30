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



}
