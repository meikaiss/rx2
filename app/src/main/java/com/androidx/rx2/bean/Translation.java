package com.androidx.rx2.bean;

import android.util.Log;

/**
 * Created by meikai on 2019/09/28.
 */
public class Translation {
    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public String show() {
        String msg = "翻译内容为: "+content.out;
        Log.e("NoConditionLoop", msg);
        return msg;
    }
}
