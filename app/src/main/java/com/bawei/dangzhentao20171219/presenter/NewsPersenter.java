package com.bawei.dangzhentao20171219.presenter;

import com.bawei.dangzhentao20171219.bean.GouBean;
import com.bawei.dangzhentao20171219.bean.JiaBean;
import com.bawei.dangzhentao20171219.model.NewsModel;
import com.bawei.dangzhentao20171219.net.CGSB;
import com.bawei.dangzhentao20171219.view.INews;

import retrofit2.Call;

/**
 * Created by DangByMySide on 2017/12/19.
 */

public class NewsPersenter {
    private final NewsModel newsModel;
    private INews iNews;
    public NewsPersenter(INews iNews) {
        newsModel = new NewsModel();
        this.iNews=iNews;
    }
    public void getNews(){
        newsModel.getNews(new CGSB<JiaBean>() {
            @Override
            public void chengGong(final JiaBean jiaBean) {
                iNews.showBean(jiaBean);
            }

            @Override
            public void shiBai(Call<JiaBean> call) {

            }
        },"72","1","android");
    }
    public void getNewss(){
        newsModel.getNewss(new CGSB<GouBean>() {
            @Override
            public void chengGong(final GouBean gouBean) {
                iNews.showBeans(gouBean);
            }

            @Override
            public void shiBai(Call<GouBean> call) {

            }
        },"72","android");
    }

}
