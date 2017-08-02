package com.example.ubuntu.okhttptest;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_get;
    private Button bt_post;
    private Button bt_cancel;
    final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_get = (Button) findViewById(R.id.bt_get);
        bt_post = (Button) findViewById(R.id.bt_post);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);

        bt_get.setOnClickListener(this);
        bt_post.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_get:
                getRequest();
                break;
            case R.id.bt_post:
                postRequest();
                break;
            case R.id.bt_cancel:
                cancelRequest(1);
                break;
        }
    }

    public void getRequest(){
        final Request request = new Request.Builder()
                .get()
                .tag(1)
                .url("http://baidu.com")
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.d("request","get data as: "+response.body().string());
                    }else{
                        throw new IOException("Unexcepted code"+response);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }



    public  void postRequest(){
        //RequestBody formBody = new FormBody().add("","").build();

        FormBody formBody = new FormBody.Builder()
                .add("try","shishi")
                .build();

        final Request request = new Request.Builder()
                .url("http://baidu.com")
                .tag(1)
                .post(formBody)
                .build();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.d("request","post data as: "+response.body().string());
                    }else{
                        throw new IOException("Unexcepted code"+response);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public  void cancelRequest( int tag){

        for(Call call : client.dispatcher().queuedCalls()) {
            if(call.request().tag().equals(tag)) {
                call.cancel();
                Log.d("request "+tag, "request task "+tag + "is canceled");
            }
        }
        for(Call call : client.dispatcher().runningCalls()) {
            if(call.request().tag().equals(tag)){
                call.cancel();
                Log.d("request "+tag, "request task "+tag + "is canceled");
            }
        }
    }



    //async post file
    public  void asyncpostRequest(){
        //RequestBody formBody = new FormBody().add("","").build();

        FormBody formBody = new FormBody.Builder()
                .add("try","shishi")
                .build();

        final Request request = new Request.Builder()
                .url("http://baidu.com")
                .tag(1)
                .post(formBody)
                .build();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.d("request","post data as: "+response.body().string());
                    }else{
                        throw new IOException("Unexcepted code"+response);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



}


