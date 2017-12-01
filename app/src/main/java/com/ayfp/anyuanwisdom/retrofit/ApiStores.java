package com.ayfp.anyuanwisdom.retrofit;

import com.ayfp.anyuanwisdom.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  16:18.
 * @description:
 */

public interface ApiStores {
    @FormUrlEncoded
    @POST("api.php?app=userInterface&act=login")
    Observable<AppResultData<UserBean>> login(@Field("token") String token,
                                              @Field("user_name") String user_name,
                                              @Field("password") String password);
}
