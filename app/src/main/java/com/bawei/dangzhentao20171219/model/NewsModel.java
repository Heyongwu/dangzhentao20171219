package com.bawei.dangzhentao20171219.model;

import com.bawei.dangzhentao20171219.bean.GouBean;
import com.bawei.dangzhentao20171219.bean.JiaBean;
import com.bawei.dangzhentao20171219.net.CGSB;
import com.bawei.dangzhentao20171219.net.RetrofitHelper;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DangByMySide on 2017/12/19.
 */

public class NewsModel implements NewsModelJK {
    @Override
    public void getNews(final CGSB<JiaBean> cgsb,String i,String j,String k) {
        Flowable<JiaBean> obserable = RetrofitHelper.getServiceAPI().getNews(i,j,k);//被观察者

        obserable.subscribeOn(Schedulers.io())//被观察者       Scheduler （调度器）

                .observeOn(AndroidSchedulers.mainThread())       //观察者 切换到主线程
                .subscribe(new Consumer<JiaBean>() {
                    @Override
                    public void accept(JiaBean jiaBean) throws Exception {
                        //调用成功的方法
                        cgsb.chengGong(jiaBean);
                    }
                });
    }

    @Override
    public void getNewss(final CGSB<GouBean> cgsb, String i, String k) {
        Flowable<GouBean> obserable = RetrofitHelper.getServiceAPI().getNewss(i,k);//被观察者

        obserable.subscribeOn(Schedulers.io())//被观察者       Scheduler （调度器）

                .observeOn(AndroidSchedulers.mainThread())       //观察者 切换到主线程
                .subscribe(new Consumer<GouBean>() {
                    @Override
                    public void accept(GouBean gouBean) throws Exception {
                        //调用成功的方法
                        cgsb.chengGong(gouBean);
                    }
                });
    }
}
