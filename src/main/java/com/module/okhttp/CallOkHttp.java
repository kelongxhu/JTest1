package com.module.okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ke.long
 * @since 2018/10/12 17:04
 */
@Slf4j
public class CallOkHttp {
    public static void main(String[] args) {
        CallOkHttp okHttp = new CallOkHttp();
      //  okHttp.testAsyncCall();
      //  okHttp.testSync();
        //
        //okHttp.postForm();
        //okHttp.postBody();
        okHttp.interceptorTest();
    }

    private void testAsyncCall() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.info("onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.info("onResponse: {}" , response.body().string());
            }
        });
    }


    private void testSync(){
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    log.info("run:{} " , response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    void postBody(){
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";

        //RequestBody requestBody = new RequestBody() {
        //    @Nullable
        //    @Override
        //    public MediaType contentType() {
        //        return MediaType.parse("text/x-markdown; charset=utf-8");
        //    }
        //
        //    @Override
        //    public void writeTo(BufferedSink sink) throws IOException {
        //        sink.writeUtf8("I am Jdqm.");
        //    }
        //};


        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.info("onFailure:{} " , e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.info("response {}", response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    log.info("{}", headers.name(i) + ":" + headers.value(i));
                }
                log.info("onResponse: {}" + response.body().string());
            }
        });
    }

    /**
     * 提交表单
     */
    void postForm(){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.info("onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.info(response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    log.info( "{}",headers.name(i) + ":" + headers.value(i));
                }
                log.info("onResponse: " + response.body().string());
            }
        });
    }




    public void interceptorTest(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .header("User-Agent", "OkHttp Example")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.info("onFailure: {}" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    log.info("onResponse: " + response.body().string());
                    body.close();
                }
            }
        });
    }




    public class LoggingInterceptor implements Interceptor {
        private static final String TAG = "LoggingInterceptor:{}";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long startTime = System.nanoTime();
            log.info(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response =  chain.proceed(request);

            long endTime = System.nanoTime();
            log.info(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (endTime - startTime) / 1e6d, response.headers()));

            return response;
        }
    }


    static void method3(){
        System.out.println("List<String>转化示例开始----------");
        List<String> list = new ArrayList<String>();
        list.add("fastjson1");
        list.add("fastjson2");
        list.add("fastjson3");
        String jsonString = JSON.toJSONString(list);
        System.out.println("json字符串:"+jsonString);

        //解析json字符串
        List<String> list2 = JSON.parseObject(jsonString,new TypeReference<List<String>>(){});
        System.out.println(list2.get(0)+"::"+list2.get(1)+"::"+list2.get(2));
        System.out.println("List<String>转化示例结束----------");

    }

}
