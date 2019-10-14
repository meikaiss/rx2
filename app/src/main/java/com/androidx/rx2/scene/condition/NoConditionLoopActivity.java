package com.androidx.rx2.scene.condition;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidx.rx2.R;
import com.androidx.rx2.api.GetRequest_Interface;
import com.androidx.rx2.bean.Translation;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by meikai on 2019/09/28.
 */
public class NoConditionLoopActivity extends AppCompatActivity {

    private static final String TAG = "NoConditionLoop";

    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_no_condition_loop);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_loop)
    public void onClick(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持rxjava
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Observable<Translation> observable = request.findTranslation();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Translation translation) {
                        Log.e(TAG, "onNext");
                        translation.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError, " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

//    @OnClick(R.id.btn_loop)
    public void onClick2(View v) {


        Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        Log.e(TAG, "doOnNext. accept = " + aLong);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持rxjava
                                .build();

                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
                        Observable<Translation> observable = request.findTranslation();

                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.e(TAG, "onSubscribe");
                                    }

                                    @Override
                                    public void onNext(Translation translation) {
                                        Log.e(TAG, "onNext");
                                        translation.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "onError, " + e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.e(TAG, "onComplete");
                                    }
                                });

                    }
                }).subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null){
            disposable.dispose();
        }
    }
}
