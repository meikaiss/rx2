package com.androidx.rx2.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidx.rx2.R;
import com.androidx.rx2.common.ActivityStarter;
import com.androidx.rx2.condition.ConditionLoopActivity;
import com.androidx.rx2.condition.NoConditionLoopActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv1) //为什么要有R2，R貌似也可以
    public void onClick1(View v) {
        ActivityStarter.start(this, NoConditionLoopActivity.class);
    }

    @OnClick(R.id.tv2)
    public void onClick2(View v) {
        ActivityStarter.start(this, ConditionLoopActivity.class);
    }

}
