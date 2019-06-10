package com.manojbhadane.rxdemo1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Demostation of DisposableObserver
 * In this we dont need explicit instance of Disposable
 * In MainActivity we create explicit Disposabel instance
 * In DisposableObserver we can call dispose() method with instance of DisposableObserver
 */
public class MainActivity1 extends AppCompatActivity {

    private String SampleStr = "Sample of RxDemo2";

    private Observable<String> mObservable;
    private DisposableObserver<String> mObserver;

    private TextView mTxtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtName = (TextView) findViewById(R.id.txt);

        mObservable = Observable.just(SampleStr);
        mObservable.subscribeOn(Schedulers.io());
        mObservable.observeOn(AndroidSchedulers.mainThread());

        mObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                mTxtName.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mObservable.subscribe(mObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * Dispose method with disposable observer
         */
        mObserver.dispose();
    }
}
