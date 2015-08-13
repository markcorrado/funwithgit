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

    public static GitRestClient getInstance(String baseApiUrl) {
        if(mClient == null) {
            mClient = new GitRestClient();
        }
        mBaseApiUrl = baseApiUrl;
        return mClient;
    }

    public void get(String url, AsyncHttpResponseHandler responseHandler) {
        super.get(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return mBaseApiUrl + relativeUrl;
    }
}