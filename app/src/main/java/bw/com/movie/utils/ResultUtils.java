package bw.com.movie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bw.com.movie.api.ApiService;
import bw.com.movie.app.Myapp;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ResultUtils {


    private final Retrofit retrofit;

    //静态类实现单例
    private static final class Instance{

        public static final ResultUtils _INSTANCE=new ResultUtils();
    }
    //单例
    public static ResultUtils getInstance(){

        return Instance._INSTANCE;
    }


    //网络请求数据
    private ResultUtils(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(BuildOkhttpClick())
                .build();
    }
    private OkHttpClient BuildOkhttpClick(){
        //拦截器
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return  new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拿到请求
                Request original = chain.request();
                //取出sp中存入的userid, sessionid
                SharedPreferences sharedPreferences = Myapp.getApplication().getSharedPreferences("xiuxiu", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("key", "");
                String sessionId = sharedPreferences.getString("keyone", "");
                //重新构造请求
                Request.Builder requestBuilder = original.newBuilder();
                //放入请求的参数
                requestBuilder.method(original.method(), original.body());
                //添加userId, sessionId;
                if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(sessionId)){
                    requestBuilder.addHeader("userId", userId);
                    requestBuilder.addHeader("sessionId", sessionId);
                }
                //打包
                Request request = requestBuilder.build();
                //返回response响应
                return chain.proceed(request);
            }
        })

                .readTimeout(5000,TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .build();

    }
    public Retrofit getRetrofit(){
        return  retrofit;
    }
    public  <T> T Create(Class<T> clazz){
        return retrofit.create(clazz);
    }


}
