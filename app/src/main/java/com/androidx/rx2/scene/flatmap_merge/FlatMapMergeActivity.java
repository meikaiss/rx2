package com.androidx.rx2.scene.flatmap_merge;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidx.rx2.R;
import com.androidx.rx2.api.GetRequest_Interface;
import com.androidx.rx2.bean.Translation;
import com.androidx.rx2.util.ToastUtil;
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
 * 将注册成功的返回结果 通过 flatMap 转换为登录Observable
 * Created by meikai on 2019/10/12.
 */
public class FlatMapMergeActivity extends AppCompatActivity {

    private static final String TAG = "FlatMapMergeActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_flatmap_merge);


        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_register_login)
    public void onClick2(View v) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持rxjava
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Observable<Translation> observableRegister = request.findTranslation();

        GetRequest_Interface requestLogin = retrofit.create(GetRequest_Interface.class);
        Observable<Translation> observableLogin = requestLogin.findTranslation().subscribeOn(Schedulers.io());

        observableRegister
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
                    @Override
                    public ObservableSource<Translation> apply(Translation translation) throws Exception {
                        return observableLogin;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Translation translation) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtil.toast("跳转成功");
                    }
                });


    }


}
