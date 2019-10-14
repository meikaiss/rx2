package com.androidx.rx2.scene.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by meikai on 2019/09/28.
 */
public class ActivityStarter {

    public static void start(Context context, Class<? extends Activity> clz){
        Intent intent = new Intent(context, clz);

        context.startActivity(intent);
    }
}
