package com.mydemo.controller;

import java.util.Map;

/**
 * Created by admin on 2017/6/17.
 */
public class BaseController {

    protected void putStatus(boolean flag,Map<String,String> map){
        if (flag){
            map.put("status","success");
            map.put("message","操作成功");
        }else{
            map.put("status","error");
            map.put("message","删除错误,请联系管理员。");
        }
    }
}
