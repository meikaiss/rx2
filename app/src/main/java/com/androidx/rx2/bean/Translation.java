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
    public void show() {
        Log.e("NoConditionLoop", content.out );
    }
}
