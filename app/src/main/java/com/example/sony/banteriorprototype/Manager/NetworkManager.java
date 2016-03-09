package com.example.sony.banteriorprototype.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.sony.banteriorprototype.MyApplication;
import com.example.sony.banteriorprototype.R;
import com.example.sony.banteriorprototype.data.Community.Community;
import com.example.sony.banteriorprototype.data.Community.CommunityResult;
import com.example.sony.banteriorprototype.data.Interior.Interior;
import com.example.sony.banteriorprototype.data.Interior.InteriorResult;
import com.example.sony.banteriorprototype.data.MainPage.Main;
import com.example.sony.banteriorprototype.data.MainPage.MainData;
import com.example.sony.banteriorprototype.data.Mypage.MyPageScrap;
import com.example.sony.banteriorprototype.data.Mypage.MyPost;
import com.example.sony.banteriorprototype.data.Mypage.MyProfile;
import com.example.sony.banteriorprototype.data.Mypage.MyProfileData;
import com.example.sony.banteriorprototype.data.PostTypeResult;
import com.example.sony.banteriorprototype.data.Search.Search;
import com.example.sony.banteriorprototype.data.Survey.Survey;
import com.example.sony.banteriorprototype.data.Survey.SurveyResult;
import com.google.gson.Gson;

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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        File cachefile = new File(context.getExternalCacheDir(), "mycache");
        if (!cachefile.exists()) {
            cachefile.mkdirs();
        }
        Cache cache = new Cache(cachefile, MAX_CACHE_SIZE);
        builder.cache(cache);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        try {
            disableCertificateValidation(context, builder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mClient = builder.build();
    }

    static void disableCertificateValidation(Context context, OkHttpClient.Builder builder) throws IOException {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.site);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            sc.init(null, tmf.getTrustManagers(), null);
            builder.sslSocketFactory(sc.getSocketFactory());
            builder.hostnameVerifier(hv);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


    public void cancelAll() {
        mClient.dispatcher().cancelAll();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFailure(Request request, int code, Throwable cause);
    }

    private static final int MESSAGE_SUCCESS = 0;
    private static final int MESSAGE_FAILURE = 1;

    static class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CallbackObject object = (CallbackObject) msg.obj;
            Request request = object.request;
            OnResultListener listener = object.listener;
            switch (msg.what) {
                case MESSAGE_SUCCESS:
                    listener.onSuccess(request, object.result);
                    break;
                case MESSAGE_FAILURE:
                    listener.onFailure(request, -1, object.exception);
                    break;
            }
        }
    }

    Handler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class CallbackObject<T> {
        Request request;
        T result;
        IOException exception;
        OnResultListener<T> listener;
    }

    public void cancelAll(Object tag) {

    }


    private static final String BASE_URL_FORMAT = "https://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com";

    public Request setSurvey(Context context, final OnResultListener<SurveyResult> listener) {

        String url = String.format(BASE_URL_FORMAT + "/preferences");

        final CallbackObject<SurveyResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Survey data = gson.fromJson(response.body().string(), Survey.class);
                callbackObject.result = data.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request getMypage(Context context, final OnResultListener<MyProfileData> listener) {

        String url = String.format(BASE_URL_FORMAT + "/mypages");

        final CallbackObject<MyProfileData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                MyProfile data = gson.fromJson(response.body().string(), MyProfile.class);
                callbackObject.result = data.result.mypageData;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request setMyProfile(Context context, String imageUrl, final OnResultListener<PostTypeResult> listener) {

        String url = String.format(BASE_URL_FORMAT + "/mypages");

        RequestBody body = new FormBody.Builder()
                .add("photo_url", imageUrl)
                .build();

        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .post(body)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request login(Context context, String email, String password, final OnResultListener<PostTypeResult> listener) {

        String url = String.format(BASE_URL_FORMAT + "/members/login");

        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("password", password)
                .build();


        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request signupUser(Context context, String name, String email, String password, final OnResultListener<PostTypeResult> listener) {

        String url = String.format(BASE_URL_FORMAT + "/members");

        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();
        RequestBody body = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .build();
        Request request = new Request.Builder().url(url)
                .post(body)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request getMyScrap(Context context, final OnResultListener<MyPageScrap> listener) {

        String url = String.format(BASE_URL_FORMAT + "/scraps");

        final CallbackObject<MyPageScrap> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                MyPageScrap data = gson.fromJson(response.body().string(), MyPageScrap.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request doScrap(Context context, int postId, final OnResultListener<PostTypeResult> listener) {

        String url = String.format(BASE_URL_FORMAT + "/scraps");

        RequestBody body = new FormBody.Builder()
                .add("post_id",""+postId)
                .build();

        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request getMyPost(Context context, final OnResultListener<MyPost> listener) {

        String url = String.format(BASE_URL_FORMAT + "/myposts");

        final CallbackObject<MyPost> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                MyPost data = gson.fromJson(response.body().string(), MyPost.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request setOrder(Context context,int postId,String address, String phone, int monthPrice, String payMethod, int period, final OnResultListener<PostTypeResult> listener) {

        String url = String.format(BASE_URL_FORMAT + "/members");

        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();
        RequestBody body = new FormBody.Builder()
                .add("postId", ""+postId)
                .add("address", address)
                .add("phone", phone)
                .add("monthPrice",""+monthPrice)
                .add("payMethod",payMethod)
                .add("period",""+period)
                .build();
        Request request = new Request.Builder().url(url)
                .post(body)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String GET_MAIN_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/mainpages";
    public Request getMainpage(Context context, final OnResultListener<MainData> listener) {
        String url = String.format(GET_MAIN_URL);

        final CallbackObject<MainData> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Main data = gson.fromJson(response.body().string(), Main.class);
                callbackObject.result = data.result.mainData;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String GET_COMMUNITY_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/posts/1";
    public Request getCommunityPost(Context context, final OnResultListener<CommunityResult> listener) {
        String url = String.format(GET_COMMUNITY_URL);

        final CallbackObject<CommunityResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Community data = gson.fromJson(response.body().string(), Community.class);
                callbackObject.result = data.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String GET_COMMUNITY_LIST_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/posts?page=1";
    public Request getCommunityPostList(Context context, final OnResultListener<CommunityResult> listener) {
        String url = String.format(GET_COMMUNITY_LIST_URL);

        final CallbackObject<CommunityResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Community data = gson.fromJson(response.body().string(), Community.class);
                callbackObject.result = data.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String GET_INTERIOR_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/posts/1?category";

    public Request getInteriorPost(Context context, String category, final OnResultListener<InteriorResult> listener) {

        String url = String.format(GET_INTERIOR_URL);

        final CallbackObject<InteriorResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Interior data = gson.fromJson(response.body().string(), Interior.class);
                callbackObject.result = data.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String GET_INTERIOR_LIST_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/posts?category=1&page=1";
    public Request getInteriorPostList(Context context, final OnResultListener<InteriorResult> listener) {

        String url = String.format(GET_INTERIOR_LIST_URL);

        final CallbackObject<InteriorResult> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Interior data = gson.fromJson(response.body().string(), Interior.class);
                callbackObject.result = data.result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String GET_HASHTAG_RESULT_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/tags?tag=%s&page=1";
    public Request getHashTagResult(Context context, String keyword, final OnResultListener<Search> listener) throws UnsupportedEncodingException {

        String url = String.format(GET_HASHTAG_RESULT_URL, URLEncoder.encode(keyword, "utf-8"));

        final CallbackObject<Search> callbackObject = new CallbackObject<>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Search data = gson.fromJson(response.body().string(), Search.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String SET_COMMENT_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/posts/1/replies?page=1";
    public Request setComment(Context context, int postId, String comment,final OnResultListener<PostTypeResult> listener) throws UnsupportedEncodingException {

        String url = String.format(SET_COMMENT_URL,postId,URLEncoder.encode(comment,"utf-8"));

        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();
        RequestBody body = new FormBody.Builder()
                .add("reply_content","" )
                .build();
        Request request = new Request.Builder().url(url)
                .post(body)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }


    private static final String DELETE_REPLY_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/posts/%s/replies/%s";
    public Request delReply(Context context, int postId,int replyId,final OnResultListener<PostTypeResult> listener) {

        String url = String.format(DELETE_REPLY_URL,postId,replyId);

        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String DELETE_POST_URL = "http://ec2-52-79-116-69.ap-northeast-2.compute.amazonaws.com/posts?post_id";
    public Request delPost(Context context, int postId, final OnResultListener<PostTypeResult> listener) {

        String url = String.format(DELETE_POST_URL,postId);

        final CallbackObject<PostTypeResult> callbackObject = new CallbackObject<>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PostTypeResult data = gson.fromJson(response.body().string(), PostTypeResult.class);
                callbackObject.result = data;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }
//    private static final String BASE_URL_FORMAT = "https://openapi.naver.com/v1/search/movie.xml?target=movie&query=%s&start=%s&display=%s";

//    public Request getNaverMovie(Context context, String keyword, int start, int display,
//                                 final OnResultListener<NaverMovies> listener) throws UnsupportedEncodingException {
//        String url = String.format(BASE_URL_FORMAT, URLEncoder.encode(keyword, "utf-8"), start, display);
//
//        final CallbackObject<NaverMovies> callbackObject = new CallbackObject<NaverMovies>();
//
//        Request request = new Request.Builder().url(url)
//                .header("X-Naver-Client-Id", "FRzO_6MMu6zwQYAaXlZr")
//                .header("X-Naver-Client-Secret", "z0iOB55iQk")
//                .tag(context)
//                .build();
//
//        callbackObject.request = request;
//        callbackObject.listener = listener;
//        mClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                callbackObject.exception = e;
//                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
//                mHandler.sendMessage(msg);
//            }

//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                XMLParser parser = new XMLParser();
//                NaverMovies movies = parser.fromXml(response.body().byteStream(), "channel", NaverMovies.class);
//                callbackObject.result = movies;
//                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
//                mHandler.sendMessage(msg);
//            }
//        });
//
//        return request;
//    }

    public Request testSSL(Context context, final OnResultListener<String> listener) {
        Request request = new Request.Builder().url("https://192.168.210.51:8443/test.html").build();
        final CallbackObject<String> callbackObject = new CallbackObject<String>();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callbackObject.result = response.body().string();
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;

    }
}
