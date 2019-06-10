package com.manojbhadane.rxdemo1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Demostation of CompositeDisposable
 * If there are multiple observer we need use CompositeDisposable to disponse those all
 * No need dispose() one by one observer
 * Just add all Observers to CompositeDisposable and call clear method of CompositeDisposable to clear all at once
 */
public class MainActivity2 extends AppCompatActivity {

    private String SampleStr = "Sample of RxDemo2";

    private Observable<String> mObservable;
    private DisposableObserver<String> mObserver;
    private DisposableObserver<String> mObserver2;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private TextView mTxtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtName = (TextView) findViewById(R.id.txt);

        mObservable = Observable.just(SampleStr);
        mObservable.subscribeOn(Schedulers.io());
        mObservable.observeOn(AndroidSchedulers.mainThread());

        /**
         * Observer 1
         */
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

        mCompositeDisposable.add(mObserver);
        mObservable.subscribe(mObserver);

        /**
         * Observer 2
         */
        mObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity2.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mCompositeDisposable.add(mObserver2);
        mObservable.subscribe(mObserver2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /**
         * To avoid below we will use mCompositeDisposable.clear()
         */
//        mObserver.dispose();
//        mObserver2.dispose();

        /**
         * Useage of composit disposable
         */
        mCompositeDisposable.clear();
    }
}
