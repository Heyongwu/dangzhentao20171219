package com.bawei.dangzhentao20171219.net;

import com.bawei.dangzhentao20171219.bean.GouBean;
import com.bawei.dangzhentao20171219.bean.JiaBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DangByMySide on 2017/12/19.
 */

public interface ServiceAPI {
    @GET(API.JIA)
    public Flowable<JiaBean> getNews(@Query("uid") String i,@Query("pid") String j,@Query("source") String k);
    @GET(API.GOU)
    public Flowable<GouBean> getNewss(@Query("uid") String i, @Query("source") String k);
}
