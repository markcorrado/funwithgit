package com.markcorrado.funwithgit;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by markcorrado on 8/12/15.
 */
public class GitRestClient extends AsyncHttpClient {

    private static  String mBaseApiUrl;
    private static GitRestClient mClient = null;
    private static Context mContext;

    public static GitRestClient getInstance(String baseApiUrl) {
        if(mClient == null) {
            mClient = new GitRestClient();
        }
        mBaseApiUrl = baseApiUrl;
        return mClient;
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        super.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        super.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return mBaseApiUrl + relativeUrl;
    }
}