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

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by meikai on 2019/09/28.
 */
public class ConditionLoopActivity extends AppCompatActivity {

    private static final String TAG = "NoConditionLoop";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_condition_loop);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_loop)
    public void onClick(View v) {

        //重复请求的次数 为3，则共请求 4次
        final int[] count = {0};
        final int MAX = 3;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持rxjava
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Observable<Translation> observable = request.findTranslation();

        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {


                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {

                        Log.e(TAG, "count = " + count[0]);
                        if (count[0] < MAX) {
                            count[0]++;
                            return Observable.just(1);
                        } else {
                            return Observable.empty();
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
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
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "外层， onComplete");
                    }
                });


//        Observable.interval(2, 1, TimeUnit.SECONDS)
//                .doOnNext(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//
//                        if (aLong == 4) {
//                            Observable.error(new Throwable("4次已经到了"));
//                            return;
//                        }
//
//                        Retrofit retrofit = new Retrofit.Builder()
//                                .baseUrl("http://fy.iciba.com/")
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持rxjava
//                                .build();
//
//                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//                        Observable<Translation> observable = request.getCall();
//
//                        observable.subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Observer<Translation>() {
//                                    @Override
//                                    public void onSubscribe(Disposable d) {
//                                        Log.e(TAG, "onSubscribe");
//                                    }
//
//                                    @Override
//                                    public void onNext(Translation translation) {
//                                        Log.e(TAG, "onNext");
//                                        translation.show();
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e(TAG, "onError, " + e.getMessage());
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//                                        Log.e(TAG, "onComplete");
//                                    }
//                                });
//
//                    }
//                }).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG, "外层， onSubscribe");
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                Log.e(TAG, "外层， aLong=" + aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "外层， onError, " + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "外层， onComplete");
//            }
//        });

    }


}
