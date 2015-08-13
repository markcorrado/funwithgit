package com.markcorrado.funwithgit;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("One");
        stringArrayList.add("Two");
        stringArrayList.add("Three");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringArrayList);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        GitRestClient restClient = GitRestClient.getInstance(getString(R.string.server));
        restClient.setUserAgent("FunWithGit");
        restClient.get("markcorrado/funwithgit/commits", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseArray) {
                super.onSuccess(statusCode, headers, responseArray);
                ArrayList<Commit>commitArrayList = new ArrayList<Commit>();
                if (responseArray.length() == 0) {
                    Toast.makeText(getActivity(), "No Equipment", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < responseArray.length(); i++) {
                        try {
                            JSONObject fullJson = responseArray.getJSONObject(i);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
//                            Commit commit = gson.fromJson(commitJson.toString(), Commit.class);
                            if (!fullJson.isNull("commit")) {
                                JSONObject commitJsonObject = fullJson.getJSONObject("commit");
                                Commit commit = gson.fromJson(commitJsonObject.toString(), Commit.class);
                                commitArrayList.add(commit);
                            }
                        } catch (JSONException e) {
                            Log.e("TAG", "JSON parsing error: " + e);
                        }
                    }
                }
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
