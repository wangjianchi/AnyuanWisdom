package com.ayfp.anyuanwisdom.retrofit;

import com.ayfp.anyuanwisdom.utils.FastJsonConverterFactory;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  16:25.
 * @description:
 */

public class RetrofitService {
    private static final String BASE_URL = "http://www.etiyi.com.cn/fp/admin/";
    public static final String TOKEN = "b93c9171a764df76";
    public static final int SUCCESS = 200;
    public static final int PAGE_SIZE = 20;
    private static ApiStores mApi;
    private static OkHttpClient mClient;
    public static Map<Integer,String> mErrorCode;
    public static void  init(){
       mApi = getApiService();
       mClient = createClient();
       initErrorCode();
    }
    private static ApiStores getApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getmClient())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
       ApiStores api = retrofit.create(ApiStores.class);
        return  api;
    }
    private static OkHttpClient createClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
        return client;
    }

    public static ApiStores getApi() {
        if (mApi == null){
            return getApiService();
        }
        return mApi;
    }

    private static OkHttpClient getmClient() {
        if (mClient == null){
            return createClient();
        }
        return mClient;
    }

    private static void initErrorCode(){
        mErrorCode = new HashMap<>();
        mErrorCode.put(1,"登录账号不能为空");
        mErrorCode.put(2,"用户不存在");
        mErrorCode.put(11001,"密码不能为空");
        mErrorCode.put(12002,"密码只能有数字和字母组成");
        mErrorCode.put(12003,"密码长度为6-20");
        mErrorCode.put(12001,"旧密码不能为空");
        mErrorCode.put(12002,"旧密码只能有数字和字母组成");
        mErrorCode.put(12003,"旧密码长度为6-20");
        mErrorCode.put(12004,"新密码不能为空");
        mErrorCode.put(12005,"新密码只能有数字和字母组成");
        mErrorCode.put(12006,"新密码长度为6-20");
        mErrorCode.put(15001,"电话号码不能为空");
        mErrorCode.put(22001,"查询开始位置大于等于0");
        mErrorCode.put(22002,"查询条数大于0");
        mErrorCode.put(23001,"公告ID大于0");
        mErrorCode.put(23002,"获取条数大于0");
        mErrorCode.put(24001,"公告ID大于0");
        mErrorCode.put(24002,"阅读状态只能是0或1");
        mErrorCode.put(33001,"事件主题不能为空");
        mErrorCode.put(33002,"事件分类ID大于0");
        mErrorCode.put(33003,"事件程度ID大于0");
        mErrorCode.put(33004,"事件内容不能为空");
        mErrorCode.put(33005,"事件图片不能超过5张");
        mErrorCode.put(33006,"乡镇不能为空");
        mErrorCode.put(33007,"村不能为空");
        mErrorCode.put(43001,"用户不能为空");
        mErrorCode.put(62001,"先选择乡镇");
        mErrorCode.put(72001,"经纬度不能为空");
        mErrorCode.put(73001,"签到记录ID不能为空");
        mErrorCode.put(73002,"工作内容不能为空");
        mErrorCode.put(73003,"签到地址不能为空");
        mErrorCode.put(73004,"签到定位不能为空");
        mErrorCode.put(73005,"签到图片不能为空");
        mErrorCode.put(74001,"签到记录ID不能为空");
        mErrorCode.put(74002,"工作内容不能为空");
        mErrorCode.put(74003,"签到地址不能为空");
        mErrorCode.put(74004,"签到定位不能为空");
        mErrorCode.put(74005,"签到图片不能为空");
//        mErrorCode.put(75001,"签到历史为空");
    }
}
