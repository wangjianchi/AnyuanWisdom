package com.ayfp.anyuanwisdom.retrofit;

import com.ayfp.anyuanwisdom.utils.FastJsonConverterFactory;

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
    public static void  init(){
       mApi = getApiService();
       mClient = createClient();
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
}
