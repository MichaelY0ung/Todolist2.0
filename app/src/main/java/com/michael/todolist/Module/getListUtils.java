package com.michael.todolist.Module;

import com.michael.todolist.Model.ListUtils;

import java.util.ArrayList;

/**
 * Created by Michael on 2016/9/1.
 */
public class getListUtils {
    private static ArrayList<ListUtils> list;
    public static ArrayList<ListUtils> getList() {
        list = new ArrayList<ListUtils>();
        for(int i=0;i<50;i++) {
            ListUtils utils = new ListUtils();
            if(i%3==0) {
                utils.type = "S";
            }
            else if(i%3==1){
                utils.type = "W";
            }
            else{
                utils.type = "N";
            }
            utils.beginTime = "15:00";
            utils.endTime = "18:00";
            if(i<20){
                utils.status="done";
            }
            else if(i<40){
                utils.status="doing";
            }
            else{
                utils.status="later";
            }
            utils.title = ""+i+"                    ";
            utils.text = "22222";
            if(i%20==0) {
                utils.leader = 1;
                utils.important = true;
            }
            else{
                utils.leader = 0;
                utils.important = false;
            }
            list.add(utils);
        }
        return list;
    }
}
