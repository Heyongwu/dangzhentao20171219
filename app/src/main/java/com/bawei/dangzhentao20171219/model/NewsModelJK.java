package com.bawei.dangzhentao20171219.model;

import com.bawei.dangzhentao20171219.bean.GouBean;
import com.bawei.dangzhentao20171219.bean.JiaBean;
import com.bawei.dangzhentao20171219.net.CGSB;

/**
 * Created by DangByMySide on 2017/12/19.
 */

public interface NewsModelJK {
    void getNews(CGSB<JiaBean> cgsb,String i,String j,String k);
    void getNewss(CGSB<GouBean> cgsb, String i, String k);

}
