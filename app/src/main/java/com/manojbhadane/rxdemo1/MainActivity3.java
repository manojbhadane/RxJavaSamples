package com.manojbhadane.rxdemo1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manojbhadane.easyretro.EasyRetro;
import com.manojbhadane.genericadapter.GenericAdapter;
import com.manojbhadane.rxdemo1.app.ApiInterface;
import com.manojbhadane.rxdemo1.databinding.ActivityMain3ListitemBinding;
import com.manojbhadane.rxdemo1.model.Data;
import com.manojbhadane.rxdemo1.model.SampleModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Demostration of Retrofit
 */
public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = MainActivity3.class.getName();

    private Disposable disposable;

    private RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        ApiInterface apiService = EasyRetro.setServices(ApiInterface.class);

        apiService.getSample("https://raw.githubusercontent.com/manojbhadane/TempRepo/master/sample.json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SampleModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(SampleModel sampleModel) {
                        Log.e(TAG, "onNext: " + sampleModel.getData().size());
                        setAdapter(sampleModel);
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

        disposable.dispose();
    }

    public void setAdapter(SampleModel model) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(new GenericAdapter<Data, ActivityMain3ListitemBinding>(this, model.getData()) {
            @Override
            public int getLayoutResId() {
                return R.layout.activity_main3_listitem;
            }

            @Override
            public void onBindData(Data model, int position, ActivityMain3ListitemBinding dataBinding) {
                dataBinding.txt.setText(model.getName());
            }

            @Override
            public void onItemClick(Data model, int position) {

            }
        });
    }
}
