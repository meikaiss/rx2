package com.androidx.rx2.util;

import android.widget.Toast;

import com.androidx.rx2.App;

/**
 * Created by meikai on 2019/10/12.
 */
public class ToastUtil {

    public static void toast(String msg){
        Toast.makeText(App.getApplication(), msg, Toast.LENGTH_SHORT).show();
    }
}
