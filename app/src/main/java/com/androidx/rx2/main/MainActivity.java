package com.androidx.rx2.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidx.rx2.R;
import com.androidx.rx2.scene.common.ActivityStarter;
import com.androidx.rx2.scene.condition.ConditionLoopActivity;
import com.androidx.rx2.scene.condition.NoConditionLoopActivity;
import com.androidx.rx2.scene.flatmap_merge.FlatMapMergeActivity;
import com.androidx.rx2.scene.flowable.FlowableActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv1)
    public void onClick1(View v) {
        ActivityStarter.start(this, NoConditionLoopActivity.class);
    }

    @OnClick(R.id.tv2)
    public void onClick2(View v) {
        ActivityStarter.start(this, ConditionLoopActivity.class);
    }

    @OnClick(R.id.tv3)
    public void onClick3(View v) {
        ActivityStarter.start(this, FlatMapMergeActivity.class);
    }

    @OnClick(R.id.tv4)
    public void onClick4(View v) {
        ActivityStarter.start(this, FlowableActivity.class);
    }


}
