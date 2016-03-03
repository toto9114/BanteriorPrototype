package com.example.sony.banteriorprototype.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.begentgroup.xmlparser.XMLParser;
import com.example.sony.banteriorprototype.MyApplication;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.CommunityData;
import com.example.sony.banteriorprototype.data.InteriorData;
import com.example.sony.banteriorprototype.data.MyPageData;
import com.example.sony.banteriorprototype.data.MyWritingInfo;
import com.example.sony.banteriorprototype.data.ProductData;
import com.example.sony.banteriorprototype.data.ScrapData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sony on 2016-02-22.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }
    OkHttpClient mClient;
    static class CallbackObject<T>{
        Request request;
        T result;
        IOException exception;
        OnResultListener<T> listener;
    }
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
    private NetworkManager(){
        OkHttpClient.Builder builder  = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        File cachefile = new File(context.getExternalCacheDir(),"mycache");
        if(!cachefile.exists()){
            cachefile.mkdirs();
        }
        Cache cache = new Cache(cachefile,MAX_CACHE_SIZE);
        builder.cache(cache);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        mClient = builder.build();
    }

    private static final String[] USER_NAME = {"이지훈","주해찬","김민주","정윤지"};
    public static final int[] MAIN_INTERIOR_IMAGE = {R.drawable.modern_main1,
            R.drawable.modern_main2,
            R.drawable.modern_main3,
            R.drawable.modern_main4};
    private static final int[] DETAIL_INTERIOR_MODERN1 = {R.drawable.detail_modern1,
            R.drawable.detail_modern2,
            R.drawable.detail_modern3,
            R.drawable.detail_modern4};

    Handler mHandler = new Handler();

    public interface OnResultListener<T>{
        public void onSuccess(T result);
        public void onFailure(int code);
    }

    public void login(Context context, String userId, String password, final OnResultListener<String> listener){

    }
    public void signup(Context context,String userId, String name, String password, final OnResultListener<String> listener){

    }

    public void getMainInteriorData(final OnResultListener<List<InteriorData>> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<InteriorData> list = new ArrayList<InteriorData>();
                for(int i = 0 ; i< MAIN_INTERIOR_IMAGE.length; i++){
                    InteriorData data = new InteriorData();
                    data.interiorImage = MAIN_INTERIOR_IMAGE[i];
                    data.interiorType = i;
                    list.add(data);
                }
                listener.onSuccess(list);
            }
        },1000);
    }

    public void getDetailInteriorData(final OnResultListener<List<InteriorData>> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<InteriorData> list = new ArrayList<InteriorData>();
                for(int i = 0 ;i < 16 ; i++) {
                    InteriorData data = new InteriorData();
                    data.interiorImage = MAIN_INTERIOR_IMAGE[i % MAIN_INTERIOR_IMAGE.length];
                    list.add(data);
                }
                listener.onSuccess(list);
            }
        },1000);
    }

    public void getCommunityMain(final OnResultListener<List<CommunityData>> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<CommunityData> list = new ArrayList<CommunityData>();
                for(int i = 0 ; i< 10; i++){
                    CommunityData data = new CommunityData();
                    data.mainImage = MAIN_INTERIOR_IMAGE[i%MAIN_INTERIOR_IMAGE.length];
                    data.profileImage = R.drawable.profile_image;
                    list.add(data);
                }
                listener.onSuccess(list);
            }
        },1000);
    }
    public void getScrapData(final OnResultListener<List<ScrapData>> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ScrapData> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    ScrapData data = new ScrapData();
                    data.interiorImage = MAIN_INTERIOR_IMAGE[i%MAIN_INTERIOR_IMAGE.length];
                    data.profileImage = R.drawable.profile_image;
                    list.add(data);
                }
                listener.onSuccess(list);
            }
        }, 1000);
    }

    public void getMyWritingData(final OnResultListener<List<MyWritingInfo>> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<MyWritingInfo> list = new ArrayList<>();
                Random r = new Random();
                for (int i = 0; i < 10; i++) {
                    int random = r.nextInt(MAIN_INTERIOR_IMAGE.length);
                    MyWritingInfo data = new MyWritingInfo();
                    data.interiorImage = MAIN_INTERIOR_IMAGE[(i+random)%MAIN_INTERIOR_IMAGE.length];
                    data.profileImage = R.drawable.profile_image;
                    list.add(data);
                }
                listener.onSuccess(list);
            }
        }, 1000);
    }
    public void getProductData(final OnResultListener<List<ProductData>> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ProductData> list = new ArrayList<>();
                for (int i = 0; i < DETAIL_INTERIOR_MODERN1.length; i++) {
                    ProductData data = new ProductData();
                    data.productImage = DETAIL_INTERIOR_MODERN1[i];
                    list.add(data);
                }
                listener.onSuccess(list);
            }
        }, 1000);
    }

    public void getMypage(final OnResultListener<MyPageData> listener){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random r= new Random();
                MyPageData data = new MyPageData();
                data.setName(USER_NAME[r.nextInt(USER_NAME.length)]);
                data.scrapCount = "스크랩:"+r.nextInt(30);
                data.myPostCount = "내가 쓴 글"+r.nextInt(20);
                listener.onSuccess(data);
            }
        },500);
    }
}
