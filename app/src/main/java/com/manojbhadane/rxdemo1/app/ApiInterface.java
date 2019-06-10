package com.manojbhadane.rxdemo1.app;

import com.manojbhadane.rxdemo1.model.SampleModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Observable<SampleModel> getSample(@Url String url);
}
