package com.michael.todolist.Model;

import java.util.ArrayList;

/**
 * Created by Michael on 2016/9/3.
 */
public class TypeUtils {
    private final String[] typeList = {"S","W","N","R","O"};
    private final String mType;
    private ArrayList<String> list;

    public TypeUtils(String type){
        mType = type;
    }

    public ArrayList<String> getList() {
        list = new ArrayList<String>();
        for(int i=0;i<typeList.length;i++){
            if(mType.equals(typeList[i])){
            }
            else {
                list.add(typeList[i]);
            }
        }
        return list;
    }
}
