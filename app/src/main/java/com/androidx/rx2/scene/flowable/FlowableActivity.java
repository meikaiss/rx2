package com.androidx.rx2.scene.flowable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidx.rx2.R;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 无穷大的缓存池，直到上游OOM
 * BackpressureStrategy.BUFFER;
 * 保留池内的，丢弃新来的
 * BackpressureStrategy.DROP;
 * 保留新来的，丢弃池内的
 * BackpressureStrategy.LATEST;
 * 上游没有缓存，下游发现实现 onBackpressureXXX 操作
 * BackpressureStrategy.MISSING;
 * 缓存满时，招聘MissingBackpressureException
 * BackpressureStrategy.ERROR;
 * <p>
 * Flowable:
 * sdk创建的Flowable，可以通过 onBackpressureBuffer()、 onBackpressureDrop()、onBackpressureLatest() 三个操作来处理背压，
 * 默认为128缓存池的背压策略
 * <p>
 * Created by meikai on 2019/10/12.
 */
public class FlowableActivity extends AppCompatActivity {

    private static final String TAG = "FlowableActivity";

    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_flowable);


        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_flowable_request)
    public void request(View v) {
        if (subscription == null) {
            return;
        }

        subscription.request(128);

    }

    @OnClick(R.id.btn_flowable)
    public void onClick2(View v) {

        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "aLong=" + aLong);

//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError, t=" + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (subscription != null) {
            subscription.cancel();
        }
    }
}
