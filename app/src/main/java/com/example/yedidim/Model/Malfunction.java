package com.example.yedidim.Model;


import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Malfunction {
    @PrimaryKey
    @NonNull
    private int IDMalfunction;
    private String problem;
    private String notes;
    private User user;
    private ImageView image; // כשנוסיף העלאת תמונה אולי נשנה את הסוג

    public Malfunction(){
    }
    public Malfunction(int ID, String problem, String notes, User u, ImageView iv){
        this.IDMalfunction= ID;
        this.problem=problem;
        this.notes=notes;
        this.user=u;
        this.image=iv;
    }
    public void setProblem(String p){
        this.problem = p;
    }
    public void setNotes(String n){
        this.notes = n;
    }
    public void setImage(ImageView iv){
        this.image = iv;
    }
}