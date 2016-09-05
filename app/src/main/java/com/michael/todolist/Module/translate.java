package com.michael.todolist.Module;

import com.michael.todolist.R;

/**
 * Created by Michael on 2016/9/1.
 */
public class translate {
    public static String cardTitle(String status) {
        if(status.equals("doing")){
            return "正在进行中";
        }
        else if(status.equals("done")) {
            return "已完成";
        }
        else if(status.equals("later")){
            return "尚未开始";
        }
        else if(status.equals("noleader")){
            return "";
        }
        else {
            return "未完成";
        }
    }
    public static int typeChange(String type){
        switch (type){
            case "S":
                return R.drawable.type_sport;
            case "W":
                return R.drawable.type_work;
            case "N":
                return R.drawable.type_note;
            case "R":
                return R.drawable.type_reminder;
            case "O":
                return R.drawable.type_other;
            default:
                return 0;
        }
    }
    public static String typeNameChange(String type){
        switch (type){
            case "S":
                return "Sports";
            case "W":
                return "Work";
            case "N":
                return "Notes";
            case "R":
                return "Reminders";
            case "O":
                return "Others";
            default:
                return null;
        }
    }
}
