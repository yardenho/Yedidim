package com.example.yedidim.Model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    static  final private Model instance = new Model();
    private List<User> userList =new LinkedList<User>();
    private List<Malfunction> malfunctionList =new LinkedList<Malfunction>();

}
