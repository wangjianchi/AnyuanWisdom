package com.ayfp.anyuanwisdom.retrofit;

import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventDegree;
import com.ayfp.anyuanwisdom.bean.UserBean;
import com.ayfp.anyuanwisdom.view.contacts.bean.ContactsList;
import com.ayfp.anyuanwisdom.view.live.bean.LivePushUrlBean;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeDetail;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;

import java.util.List;

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


    @FormUrlEncoded
    @POST("api.php?app=afficheInterface&act=getIndexAffiche")
    Observable<AppResultData<List<NoticeListBean>>> getIndexAffiche(@Field("token") String token,
                                                                    @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("api.php?app=afficheInterface&act=getAfficheByLimit")
    Observable<AppResultData<List<NoticeListBean>>> getAfficheByLimit(@Field("token") String token,
                                                                      @Field("user_name") String user_name,
                                                                      @Field("start") int start,
                                                                      @Field("length") int length);

    @FormUrlEncoded
    @POST("api.php?app=afficheInterface&act=getAfficheDetail")
    Observable<AppResultData<NoticeDetail>> getAfficheDetail(@Field("token") String token,
                                                             @Field("user_name") String user_name,
                                                             @Field("affiche_id") int id);

    @FormUrlEncoded
    @POST("api.php?app=afficheInterface&act=getIndexAffiche")
    Observable<AppResultData<Object>> getIndexAffiche(@Field("token") String token,
                                                      @Field("is_read") int is_read,
                                                      @Field("affiche_id") int id);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=getEventCategory")
    Observable<AppResultData<List<EventCategory>>> getEventCategory(@Field("token") String token);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=getEventDegree")
    Observable<AppResultData<List<EventDegree>>> getEventDegree(@Field("token") String token);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=eventReport")
    Observable<AppResultData<Object>> eventReport(@Field("token") String token,
                                                  @Field("user_name") String user_name,
                                                  @Field("report_title") String report_title,
                                                  @Field("cate_id") int cate_id,
                                                  @Field("degree_id") int degred_id,
                                                  @Field("event_content") String event_content,
                                                  @Field("event_images") String event_images);

    @FormUrlEncoded
    @POST("api.php?app=addressBookInterface&act=getAddressBook")
    Observable<AppResultData<List<ContactsList>>> getAddressBook(@Field("token") String token,
                                                                 @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("api.php?app=liveInterface&act=getLivePushUrl")
    Observable<AppResultData<LivePushUrlBean>> getLivePushUrl(@Field("token") String token,
                                                              @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("api.php?app=userInterface&act=getUserMsg")
    Observable<AppResultData<UserBean>> getUserMsg(@Field("token") String token,
                                                              @Field("user_name") String user_name);
}