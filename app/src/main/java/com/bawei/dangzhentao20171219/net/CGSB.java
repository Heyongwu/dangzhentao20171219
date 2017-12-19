package com.bawei.dangzhentao20171219.net;

import retrofit2.Call;

/**
 * Created by DangByMySide on 2017/12/19.
 */

public interface CGSB<T> {
    void chengGong(T t);
    void shiBai(Call<T> call);
}
