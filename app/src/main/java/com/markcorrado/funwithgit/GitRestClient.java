package com.markcorrado.funwithgit;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by markcorrado on 8/12/15.
 */
public class GitRestClient extends AsyncHttpClient {

    private static final String BASE_URL = "https://api.github.com/repos/markcorrado/funwithgit/";
    private static final String COMMITS = "commits";
    private static GitRestClient mClient = null;

    public static GitRestClient getInstance() {
        if(mClient == null) {
            mClient = new GitRestClient();
        }
        return mClient;
    }

    public void get(String url, AsyncHttpResponseHandler responseHandler) {
        super.get(getAbsoluteUrl(url), responseHandler);
    }

    public void getCommits(AsyncHttpResponseHandler responseHandler) {
        mClient.get(COMMITS, responseHandler);
    }

    public void getFiles(String sha, AsyncHttpResponseHandler responseHandler) {
        mClient.get(COMMITS + "/" + sha, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}