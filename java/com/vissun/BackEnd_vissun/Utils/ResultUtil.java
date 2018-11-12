package com.vissun.BackEnd_vissun.Utils;


import com.vissun.BackEnd_vissun.Bean.Result;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date： Create in 10:57 2018/1/24
 */
public class ResultUtil {

    public static Result success(Object object){
        Result result=new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(object);
        return result;
    }
    public static Result success1(){
        Result result=new Result();
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    public static Result error(Integer code, String msg, Object data){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


}
