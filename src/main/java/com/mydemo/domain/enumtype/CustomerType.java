package com.mydemo.domain.enumtype;

/**
 * Created by admin on 2017/6/13.
 */
public enum CustomerType {

    ADULT,CHILD,STUDENT,DISABLEDSOLDIERS;


    public String getDescription(){
        String description = null;
        switch (this){
            case ADULT:
                description = "成人";
                break;
            case CHILD:
                description = "小孩";
                break;
            case STUDENT:
                description = "学生";
                break;
            case DISABLEDSOLDIERS:
                description = "残疾军人、伤残人民警察";
                break;
        }
        return description;
    }
}
