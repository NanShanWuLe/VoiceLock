package com.graduationproject.voicelock.ToolUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public String GETHELLO = "http://106.54.167.67:8080/login/hello";
    public String POSTFILE = "http://106.54.167.67:8080/login/upload";


    public String GetHello(String url) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String PostWavFile(String url, String path, String filename) throws IOException{
        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", filename, RequestBody.create(MediaType.parse("multipart/form-data"), new File(path)))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(fileBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
