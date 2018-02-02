package com.ayfp.anyuanwisdom.retrofit;

import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventConfig;
import com.ayfp.anyuanwisdom.bean.EventDegree;
import com.ayfp.anyuanwisdom.bean.Town;
import com.ayfp.anyuanwisdom.bean.UserBean;
import com.ayfp.anyuanwisdom.view.contacts.bean.ContactsList;
import com.ayfp.anyuanwisdom.view.contacts.bean.Person;
import com.ayfp.anyuanwisdom.view.live.bean.LivePushUrlBean;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeDetail;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;
import com.ayfp.anyuanwisdom.view.report.bean.EventBean;
import com.ayfp.anyuanwisdom.view.report.bean.EventListBean;
import com.ayfp.anyuanwisdom.view.sign.bean.SignAddress;
import com.ayfp.anyuanwisdom.view.sign.bean.SignStatusBean;

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
    @POST("api.php?app=userInterface&act=editPassword")
    Observable<AppResultData<Object>> editPassword(@Field("token") String token,
                                                   @Field("user_name") String user_name,
                                                   @Field("old_password") String old_password,
                                                   @Field("new_password") String new_password);


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
    @POST("api.php?app=afficheInterface&act=getAfficheUsers")
    Observable<AppResultData<List<NoticeDetail.ReadUsersBean>>> getAfficheUsers(@Field("token") String token,
                                                                                @Field("is_read") int is_read,
                                                                                @Field("affiche_id") int id);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=getEventCategory")
    Observable<AppResultData<List<EventCategory>>> getEventCategory(@Field("token") String token);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=getEventDegree")
    Observable<AppResultData<List<EventDegree>>> getEventDegree(@Field("token") String token);

    @FormUrlEncoded
    @POST("api.php?app=townVillageInterface&act=getTownOptions")
    Observable<AppResultData<List<Town>>> getTownOptions(@Field("token") String token);

    @FormUrlEncoded
    @POST("api.php?app=userInterface&act=getConfig")
    Observable<AppResultData<EventConfig>> getConfig(@Field("token") String token);

    @FormUrlEncoded
    @POST("api.php?app=townVillageInterface&act=getVillageOptions")
    Observable<AppResultData<List<Town>>> getVillageOptions(@Field("token") String token,
                                                            @Field("town_id") int id);


    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=eventReport")
    Observable<AppResultData<Object>> eventReport(@Field("token") String token,
                                                  @Field("user_name") String user_name,
                                                  @Field("report_title") String report_title,
                                                  @Field("cate_id") int cate_id,
                                                  @Field("degree_id") int degred_id,
                                                  @Field("event_content") String event_content,
                                                  @Field("event_images") String event_images,
                                                  @Field("town_id") int town_id,
                                                  @Field("village_id") int village_id,
                                                  @Field("house_number") String house_number,
                                                  @Field("status_id") int status_id);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=updateEventReport")
    Observable<AppResultData<Object>> updateEventReport(@Field("token") String token,
                                                  @Field("event_id") int event_id,
                                                  @Field("user_name") String user_name,
                                                  @Field("report_title") String report_title,
                                                  @Field("cate_id") int cate_id,
                                                  @Field("degree_id") int degred_id,
                                                  @Field("event_content") String event_content,
                                                  @Field("event_images") String event_images,
                                                  @Field("town_id") int town_id,
                                                  @Field("village_id") int village_id,
                                                  @Field("house_number") String house_number,
                                                  @Field("status_id") int status_id);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=getEventReportByLimit")
    Observable<AppResultData<List<EventListBean>>> getEventReportByLimit(@Field("token") String token,
                                                                         @Field("user_name") String user_name,
                                                                         @Field("start") int start,
                                                                         @Field("length") int length);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=deleteEventReport")
    Observable<AppResultData<Object>> deleteEventReport(@Field("token") String token,
                                                                 @Field("user_name") String user_name,
                                                        @Field("event_id") int event_id);

    @FormUrlEncoded
    @POST("api.php?app=eventReportInterface&act=getEventReportById")
    Observable<AppResultData<EventBean>> getEventReportById(@Field("token") String token,
                                                            @Field("user_name") String user_name,
                                                            @Field("event_id") int event_id);

    @FormUrlEncoded
    @POST("api.php?app=addressBookInterface&act=getAddressBook")
    Observable<AppResultData<List<ContactsList>>> getAddressBook(@Field("token") String token,
                                                                 @Field("user_name") String user_name);


    @FormUrlEncoded
    @POST("api.php?app=addressBookInterface&act=searchUser")
    Observable<AppResultData<List<Person>>> searchUser(@Field("token") String token,
                                                       @Field("user_name") String user_name,
                                                       @Field("search_word") String search_word);

    @FormUrlEncoded
    @POST("api.php?app=liveInterface&act=getLivePushUrl")
    Observable<AppResultData<LivePushUrlBean>> getLivePushUrl(@Field("token") String token,
                                                              @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("api.php?app=userInterface&act=getUserMsg")
    Observable<AppResultData<UserBean>> getUserMsg(@Field("token") String token,
                                                   @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("api.php?app=userInterface&act=editUserPortrait")
    Observable<AppResultData<UserBean>> editUserPortrait(@Field("token") String token,
                                                         @Field("user_name") String user_name,
                                                         @Field("portrait") String portrait);

    @FormUrlEncoded
    @POST("api.php?app=userInterface&act=editUserTel")
    Observable<AppResultData<Object>> editUserTel(@Field("token") String token,
                                                  @Field("user_name") String user_name,
                                                  @Field("tel") String tel);

    @FormUrlEncoded
    @POST("api.php?app=signInterface&act=getSignStatus")
    Observable<AppResultData<SignStatusBean>> getSignStatus(@Field("token") String token,
                                                            @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("api.php?app=signInterface&act=getAddressByLocation")
    Observable<AppResultData<SignAddress>> getAddressByLocation(@Field("token") String token,
                                                                @Field("location") String location);

    @FormUrlEncoded
    @POST("api.php?app=signInterface&act=signIn")
    Observable<AppResultData<Object>> signIn(@Field("token") String token,
                                             @Field("sign_id") int sign_id,
                                             @Field("content") String content,
                                             @Field("sign_in_address") String sign_in_address,
                                             @Field("sign_locate") String sign_locate,
                                             @Field("sign_in_imgs") String sign_in_imgs);

    @FormUrlEncoded
    @POST("api.php?app=signInterface&act=signOut")
    Observable<AppResultData<Object>> signOut(@Field("token") String token,
                                             @Field("sign_id") int sign_id,
                                             @Field("content") String content,
                                             @Field("sign_out_address") String sign_out_address,
                                             @Field("sign_locate") String sign_locate,
                                             @Field("sign_out_imgs") String sign_out_imgs);

    @FormUrlEncoded
    @POST("api.php?app=signInterface&act=uploadLocation")
    Observable<AppResultData<Object>> uploadLocation(@Field("token") String token,
                                                     @Field("user_name") String user_name,
                                                     @Field("sign_locate") String sign_locate);

    @FormUrlEncoded
    @POST("api.php?app=signInterface&act=getSignHistory")
    Observable<AppResultData<SignStatusBean>> getSignHistory(@Field("token") String token,
                                                     @Field("user_name") String user_name,
                                                     @Field("limit_time")String limit_time);
}
