package com.markcorrado.funwithgit;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of CommitFiles.
 * <p>
 */
public class CommitFileFragment extends ListFragment {

    public static final String TAG = "commit_files_fragment";
    public static final String SHA_TAG = "sha";

    GitRestClient mRestClient;
    private String mSha;

    public static CommitFileFragment newInstance(String sha) {
        CommitFileFragment fragment = new CommitFileFragment();
        Bundle args = new Bundle();
        args.putString(SHA_TAG, sha);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommitFileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSha = getArguments().getString(SHA_TAG);
        }
        getActivity().setTitle("Files");

        getFiles(mSha);
    }

    private void getFiles(String sha) {
        mRestClient = GitRestClient.getInstance();
        mRestClient.setUserAgent("FunWithGit");
        mRestClient.getFiles(sha, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<CommitFile> commitFileArrayList = new ArrayList<>();
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    if (!response.isNull("files")) {
                        JSONArray filesJsonArray = response.getJSONArray("files");
                        commitFileArrayList = gson.fromJson(filesJsonArray.toString(), new TypeToken<List<CommitFile>>() {
                        }.getType());
                    }
                } catch (JSONException e) {
                    Log.e("TAG", "JSON parsing error: " + e);
                }
                setupAdapter(commitFileArrayList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "Error loading git commits!", Toast.LENGTH_LONG).show();
                System.out.println(errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(throwable.getLocalizedMessage());
            }
        });
    }

    private void setupAdapter(ArrayList<CommitFile> commitFileArrayList) {
        ArrayAdapter<CommitFile> adapter = new ArrayAdapter<CommitFile>(getActivity(), android.R.layout.simple_list_item_1, commitFileArrayList);
        setListAdapter(adapter);
    }
}
